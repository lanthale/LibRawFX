package org.librawimageio;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import org.librawdecodersettings.RawDecoderSettings;
import org.librawjava.LibrawException;
import org.librawjava.LibrawImage;
import org.librawjava.RawFormatDetector;

/**
 * ImageIO reader for RAW files via LibRaw.
 *
 * Parallelism: no global lock. Every reader owns its own LibrawImage and every
 * decode uses its own native libraw handle (libraw_r), so decodes of different
 * readers run in parallel. A single reader instance must still only be used by
 * one thread at a time (standard ImageReader contract).
 *
 * Fallback: ImageIO does not try the next reader when the first one fails. If
 * the SPI claimed a file that LibRaw cannot open (e.g. a normal TIFF), this
 * reader therefore delegates to the next suitable (non-RAW) ImageIO reader, so
 * such files are not lost.
 *
 * Size: if the caller sets sourceRenderSize (JavaFX J2DImageLoader does), the
 * returned image has EXACTLY that size. JavaFX scales itself otherwise and
 * crashes with a ClassCastException.
 */
public class RAWImageIOReader extends ImageReader {

    private static final Logger LOG = Logger.getLogger(RAWImageIOReader.class.getName());
    private static final ConcurrentHashMap<String, RawDecoderSettings> settings = new ConcurrentHashMap<>();

    private final LibrawImage libraw;
    private ImageInputStream stream;

    private byte[] fileBytes;                  // the file is read only ONCE
    private byte[] decoded;                    // decoded RGB, handed over to read()
    private RawDecoderSettings decodedWith;    // settings used for 'decoded'
    private int width;
    private int height;
    private boolean gotSize;
    private RAWIOFormatMetadata metadata;

    private Boolean headerRaw;                 // RawFormatDetector verdict on the first bytes
    private boolean probed;                    // LibRaw confirmed: this is a RAW
    private boolean notRaw;                    // LibRaw said: unsupported -> delegate
    private ImageReader fallback;              // reader used when notRaw

    public RAWImageIOReader() {
        this(new RAWImageIOReaderSpi());
    }

    public RAWImageIOReader(ImageReaderSpi originatingProvider) {
        super(originatingProvider);
        initSettings();
        libraw = new LibrawImage(this, settings);
        libraw.setProgressListener((done, total) -> {
            if (total > 0) {
                processImageProgress(100f * done / total);
            }
        });
    }

    public static void initSettings() {
        settings.computeIfAbsent("Default", k -> new RawDecoderSettings());
    }

    public static ConcurrentHashMap<String, RawDecoderSettings> getSettings() {
        initSettings();
        return settings;
    }

    // ---- input handling -----------------------------------------------------
    @Override
    public void setInput(Object input, boolean seekForwardOnly, boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        stream = (ImageInputStream) input;
        reset0();
    }

    @Override
    public void setInput(Object input, boolean seekForwardOnly) {
        setInput(input, seekForwardOnly, false);
    }

    @Override
    public void setInput(Object input) {
        setInput(input, false, false);
    }

    @Override
    public void dispose() {
        reset0();
        super.dispose();
    }

    private void reset0() {
        fileBytes = null;
        decoded = null;
        decodedWith = null;
        width = 0;
        height = 0;
        gotSize = false;
        metadata = null;
        probed = false;
        notRaw = false;
        headerRaw = null;
        if (fallback != null) {
            fallback.dispose();
            fallback = null;
        }
    }

    private void checkIndex(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException("bad index");
        }
    }

    /**
     * Reads the stream exactly once and caches the bytes.
     */
    private byte[] bytes() throws IOException {
        if (fileBytes != null) {
            return fileBytes;
        }
        if (stream == null) {
            throw new IllegalStateException("No input stream");
        }
        stream.seek(0);
        long len = stream.length();
        byte[] data;
        if (len > 0 && len < Integer.MAX_VALUE - 8) {
            data = new byte[(int) len];
            stream.readFully(data);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1 << 20);
            byte[] buf = new byte[64 * 1024];
            int n;
            while ((n = stream.read(buf)) != -1) {
                bos.write(buf, 0, n);
            }
            data = bos.toByteArray();
        }
        fileBytes = data;
        return data;
    }

    // ---- RAW or not? --------------------------------------------------------
    private void markNotRaw(LibrawException e) {
        notRaw = true;
        fileBytes = null;                       // not needed anymore, the delegate reads the stream itself
        decoded = null;
        LOG.log(Level.FINE, "{0} - Processing further with ImageIO", e.getMessage());
    }

    private void markNotRawByDetector() {
        notRaw = true;                          // no full file read, no LibRaw call
        LOG.log(Level.FINE, "{0}: Not a RAW format (detector) - Processing further with ImageIO",
                "Image data");
    }

    /**
     * Decodes (if needed) and tells whether this file is a RAW. Used by the
     * methods that need the decoded size anyway (getWidth/getHeight/read).
     */
    private boolean decodeIfRaw(ImageReadParam param) throws IOException {
        if (notRaw) {
            return false;
        }
        if (!probed && !headerLooksRaw()) {
            markNotRawByDetector();             // normal TIFF, PSD, WebP ...
            return false;
        }
        try {
            ensureDecoded(param);
            probed = true;
            return true;
        } catch (LibrawException e) {
            if (e.isUnsupportedFormat()) {
                markNotRaw(e);
                return false;
            }
            throw e;
        }
    }

    /**
     * Cheap check without decoding (only opens the file in LibRaw). Used by
     * the methods that do not need pixels (types, metadata, image count).
     */
    private boolean probeRaw() throws IOException {
        if (notRaw) {
            return false;
        }
        if (probed) {
            return true;
        }
        if (!headerLooksRaw()) {
            markNotRawByDetector();
            return false;
        }
        try {
            libraw.getQuickSize(bytes());
            probed = true;
            return true;
        } catch (LibrawException e) {
            if (e.isUnsupportedFormat()) {
                markNotRaw(e);
                return false;
            }
            throw e;
        }
    }

    /**
     * Quick verdict from the first bytes only (no full file read). Files the
     * detector rejects (normal TIFFs) go straight to the delegate.
     */
    private boolean headerLooksRaw() throws IOException {
        if (headerRaw == null) {
            if (stream == null) {
                throw new IllegalStateException("No input stream");
            }
            stream.seek(0);
            byte[] buf = new byte[RawFormatDetector.HEADER_SIZE];
            int n = 0;
            while (n < buf.length) {
                int r = stream.read(buf, n, buf.length - n);
                if (r < 0) {
                    break;
                }
                n += r;
            }
            stream.seek(0);
            headerRaw = RawFormatDetector.isRaw(n == buf.length ? buf : Arrays.copyOf(buf, n));
            LOG.log(Level.FINE, "Detector says raw: {0}", headerRaw);
        }
        return headerRaw;
    }

    /**
     * Next ImageIO reader that is not a RAW reader (normal TIFF, PNG, ...).
     */
    private ImageReader delegate() throws IOException {
        if (fallback != null) {
            return fallback;
        }
        if (stream == null) {
            throw new IllegalStateException("No input stream");
        }
        stream.seek(0);
        Iterator<ImageReader> it = ImageIO.getImageReaders(stream);
        while (it.hasNext()) {
            ImageReader r = it.next();
            if (r instanceof RAWImageIOReader) {
                r.dispose();
                continue;                       // not ourselves again
            }
            stream.seek(0);
            r.setInput(stream, false, ignoreMetadata);
            fallback = r;
            LOG.log(Level.FINE, "ImageIO reader: {0}", r.getClass().getName());
            return r;
        }
        throw new IIOException("Not a RAW file and no suitable ImageIO reader found");
    }

    // ---- decoding (no lock) -------------------------------------------------
    private static RawDecoderSettings customSettings(ImageReadParam param) {
        return (param instanceof RAWImageIOImageReadParam rp) ? rp.getSettings() : null;
    }

    /**
     * Decodes once and caches the result. Exact size (after flip/half_size) is
     * only known after a decode, so getWidth/getHeight use the same cache and
     * read() does not decode a second time.
     */
    private void ensureDecoded(ImageReadParam param) throws IOException {
        RawDecoderSettings custom = customSettings(param);
        if (decoded != null && decodedWith == custom) {
            return;                                   // already decoded with the same settings
        }
        byte[] data = bytes();
        libraw.setSettingsOverride(custom);           // null = standard lookup
        byte[] raw = libraw.readPixelDataFromStream(data);
        int w = libraw.getImageWidth();
        int h = libraw.getImageHeight();
        if (raw == null || w <= 0 || h <= 0 || raw.length != (long) w * h * 3) {
            throw new IIOException("Unexpected pixel data size for " + w + "x" + h);
        }
        decoded = raw;
        decodedWith = custom;
        width = w;
        height = h;
        gotSize = true;
    }

    // ---- ImageReader API ----------------------------------------------------
    @Override
    public int getNumImages(boolean allowSearch) throws IOException {
        return probeRaw() ? 1 : delegate().getNumImages(allowSearch);
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        if (!gotSize && !decodeIfRaw(null)) {
            return delegate().getWidth(imageIndex);
        }
        checkIndex(imageIndex);
        return width;
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        if (!gotSize && !decodeIfRaw(null)) {
            return delegate().getHeight(imageIndex);
        }
        checkIndex(imageIndex);
        return height;
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        if (!probeRaw()) {
            return delegate().getImageTypes(imageIndex);
        }
        checkIndex(imageIndex);
        ImageTypeSpecifier t = ImageTypeSpecifier.createInterleaved(
                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{0, 1, 2}, DataBuffer.TYPE_BYTE, false, false);
        return Collections.singletonList(t).iterator();
    }

    @Override
    public ImageReadParam getDefaultReadParam() {
        return new RAWImageIOImageReadParam();
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        return notRaw && fallback != null ? fallback.getStreamMetadata() : null;
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        if (ignoreMetadata) {
            return null;
        }
        if (!probeRaw()) {
            return delegate().getImageMetadata(imageIndex);
        }
        checkIndex(imageIndex);
        if (metadata == null) {
            readMetadata();
        }
        return metadata;
    }

    private void readMetadata() throws IOException {
        HashMap<String, String> map = libraw.getMetaData(bytes());
        RAWIOFormatMetadata md = new RAWIOFormatMetadata();
        map.forEach((k, v) -> {
            md.keywords.add(k);
            md.values.add(v);
        });
        metadata = md;
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        processImageStarted(imageIndex);
        if (!decodeIfRaw(param)) {
            BufferedImage img = readViaDelegate(imageIndex, param);
            processImageComplete();
            return img;
        }
        checkIndex(imageIndex);
        long start = System.nanoTime();

        final byte[] raw = decoded;
        decoded = null;                 // hand over, do not keep 70+ MB per reader
        decodedWith = null;
        final int w = width;
        final int h = height;
        LOG.log(Level.FINE, "Raw convert took: {0}s", (System.nanoTime() - start) / 1e9);

        // Zero-copy: the byte[] from LibRaw becomes the raster directly
        WritableRaster raster = Raster.createInterleavedRaster(
                new DataBufferByte(raw, raw.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);
        ComponentColorModel cm = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_sRGB), false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        BufferedImage full = new BufferedImage(cm, raster, false, null);

        // Exactly the requested size, no own aspect ratio logic: JavaFX already computed it
        Dimension target = (param != null) ? param.getSourceRenderSize() : null;
        int dstW = target != null ? Math.max(1, target.width) : w;
        int dstH = target != null ? Math.max(1, target.height) : h;

        BufferedImage result = (dstW == w && dstH == h) ? full : scale(full, dstW, dstH);
        LOG.log(Level.FINE, "ImageIO read: native={0}x{1}, target={2}, returned={3}x{4}",
                new Object[]{w, h, target, result.getWidth(), result.getHeight()});
        processImageComplete();
        return result;
    }

    /**
     * Not a RAW: let the next ImageIO reader decode it, then bring the result
     * to the requested size and to a pixel type JavaFX supports.
     */
    private BufferedImage readViaDelegate(int imageIndex, ImageReadParam param) throws IOException {
        BufferedImage img = delegate().read(imageIndex, null);
        Dimension target = (param != null) ? param.getSourceRenderSize() : null;
        if (target == null) {
            return img;                                  // plain ImageIO caller, unchanged result
        }
        int tw = Math.max(1, target.width);
        int th = Math.max(1, target.height);
        if (img.getWidth() == tw && img.getHeight() == th && isFxType(img.getType())) {
            return img;
        }
        return scale(img, tw, th);
    }

    private static boolean isFxType(int type) {
        return type == BufferedImage.TYPE_3BYTE_BGR
                || type == BufferedImage.TYPE_4BYTE_ABGR
                || type == BufferedImage.TYPE_4BYTE_ABGR_PRE
                || type == BufferedImage.TYPE_INT_RGB
                || type == BufferedImage.TYPE_INT_ARGB
                || type == BufferedImage.TYPE_INT_ARGB_PRE
                || type == BufferedImage.TYPE_BYTE_GRAY;
    }

    /**
     * Scales step by step (max. factor 2 per step). The result always has
     * exactly dstW x dstH and a pixel type JavaFX can use.
     */
    private static BufferedImage scale(BufferedImage src, int dstW, int dstH) {
        BufferedImage cur = src;
        int cw = src.getWidth();
        int ch = src.getHeight();
        while (cw / 2 >= dstW || ch / 2 >= dstH) {
            int nw = Math.max(dstW, cw / 2);
            int nh = Math.max(dstH, ch / 2);
            cur = drawScaled(cur, nw, nh);
            cw = nw;
            ch = nh;
        }
        if (cw != dstW || ch != dstH || cur == src) {
            cur = drawScaled(cur, dstW, dstH);
        }
        return cur;
    }

    private static BufferedImage drawScaled(BufferedImage src, int w, int h) {
        int type = src.getColorModel().hasAlpha()
                ? BufferedImage.TYPE_INT_ARGB_PRE
                : BufferedImage.TYPE_3BYTE_BGR;
        BufferedImage out = new BufferedImage(w, h, type);
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(src, 0, 0, w, h, null);
        } finally {
            g.dispose();
        }
        return out;
    }
}
