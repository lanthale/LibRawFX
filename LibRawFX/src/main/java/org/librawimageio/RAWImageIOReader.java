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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import org.librawdecodersettings.RawDecoderSettings;
import org.librawjava.LibrawImage;

/**
 * ImageIO reader for RAW files via LibRaw.
 *
 * Parallelism: no global lock. Every reader owns its own LibrawImage and every
 * decode uses its own native libraw handle (libraw_r), so decodes of different
 * readers run in parallel. A single reader instance must still only be used by
 * one thread at a time (standard ImageReader contract).
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

    private void ensureSize() throws IOException {
        if (!gotSize) {
            ensureDecoded(null);
        }
    }

    // ---- ImageReader API ----------------------------------------------------
    @Override
    public int getNumImages(boolean allowSearch) {
        return 1;
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        ensureSize();
        return width;
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        ensureSize();
        return height;
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
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
    public IIOMetadata getStreamMetadata() {
        return null;
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        if (ignoreMetadata) {
            return null;
        }
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
        checkIndex(imageIndex);
        processImageStarted(0);
        long start = System.nanoTime();

        ensureDecoded(param);
        final byte[] raw = decoded;
        decoded = null;                 // hand over, do not keep 70+ MB per reader
        decodedWith = null;
        final int w = width;
        final int h = height;
        LOG.log(Level.INFO, "ImageIO read: native={0}x{1}, renderSize={2}",
                new Object[]{w, h, param == null ? "no param" : param.getSourceRenderSize()});
        LOG.log(Level.FINE, "Raw convert took: {0}s", (System.nanoTime() - start) / 1e9);

        // Zero-copy: the byte[] from LibRaw becomes the raster directly
        WritableRaster raster = Raster.createInterleavedRaster(
                new DataBufferByte(raw, raw.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);
        ComponentColorModel cm = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_sRGB), false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        BufferedImage full = new BufferedImage(cm, raster, false, null);

        Dimension target = (param != null) ? param.getSourceRenderSize() : null;
        final int dstW = target != null ? Math.max(1, target.width) : w;   // exakt, ohne eigene Seitenverhältnis-Logik
        final int dstH = target != null ? Math.max(1, target.height) : h;

        BufferedImage result = (dstW == w && dstH == h) ? full : scale(full, dstW, dstH);

        LOG.log(Level.INFO, "ImageIO read: native={0}x{1}, target={2}, returned={3}x{4}",
                new Object[]{w, h, target, result.getWidth(), result.getHeight()});
        processImageComplete();
        return result;
    }

    /**
     * Skaliert schrittweise (max. Faktor 2 pro Schritt), Ergebnis hat exakt
     * dstW x dstH.
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
        if (cw != dstW || ch != dstH) {
            cur = drawScaled(cur, dstW, dstH);
        }
        return cur;
    }

    private static BufferedImage drawScaled(BufferedImage src, int w, int h) {
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
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
