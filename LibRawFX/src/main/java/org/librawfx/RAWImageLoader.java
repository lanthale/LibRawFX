package org.librawfx;

import org.librawjava.LibrawImage;
import org.librawdecodersettings.RawDecoderSettings;
import org.librawfx.dimension.DimensionProvider;
import com.sun.javafx.iio.ImageFrame;
import com.sun.javafx.iio.ImageMetadata;
import com.sun.javafx.iio.ImageStorage;
import com.sun.javafx.iio.common.ImageLoaderImpl;
import com.sun.javafx.iio.common.ImageTools;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Screen;
import javax.imageio.ImageIO;

/**
 * Lädt RAW-Bilder über LibRaw. Dateien, die kein RAW sind (z. B. normale TIFFs),
 * werden über ImageIO geladen. Der RawFormatDetector wählt nur den ersten Versuch,
 * der jeweils andere Decoder dient als Fallback.
 *
 * @author Clemens Lanthaler
 */
public class RAWImageLoader extends ImageLoaderImpl {

    private static final Logger LOG = Logger.getLogger(RAWImageLoader.class.getName());

    private static final ConcurrentHashMap<String, RawDecoderSettings> settings = new ConcurrentHashMap<>();

    private final InputStream input;
    private final DimensionProvider dimensionProvider;
    private final Lock accessLock = new Lock();
    private final LibrawImage libraw;
    private boolean isDisposed = false;

    protected RAWImageLoader(InputStream input, DimensionProvider dimensionProvider) {
        super(RAWDescriptor.getInstance());
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }
        this.input = input;
        this.dimensionProvider = dimensionProvider;
        initSettings();
        this.libraw = new LibrawImage(this, settings);
    }

    public static void initSettings() {
        settings.computeIfAbsent("Default", k -> new RawDecoderSettings());
    }

    public static ConcurrentHashMap<String, RawDecoderSettings> getSettings() {
        initSettings();
        return settings;
    }

    @Override
    public synchronized void dispose() {
        if (!accessLock.isLocked() && !isDisposed) {
            isDisposed = true;
        }
    }

    @Override
    protected void updateImageMetadata(ImageMetadata im) {
        super.updateImageMetadata(im);
    }

    @Override
    protected void updateImageProgress(float f) {
        super.updateImageProgress(f);
    }

    @Override
    protected void emitWarning(String string) {
        super.emitWarning(string);
    }

    @Override
    public ImageFrame load(int imageIndex, double width, double height, boolean preserveAspectRatio,
            boolean smooth, float screenPixelScale, float imagePixelScale) throws IOException {
        if (imageIndex != 0) {
            return null;
        }

        byte[] fileBytes = readAll(input);
        if (fileBytes.length == 0) {
            throw new IOException("Empty input stream");
        }
        byte[] head = Arrays.copyOf(fileBytes, Math.min(fileBytes.length, RawFormatDetector.HEADER_SIZE));
        boolean looksRaw = RawFormatDetector.isRaw(head);
        LOG.log(Level.FINE, "Detector says raw: {0}", looksRaw);

        IOException firstFailure;
        try {
            return looksRaw
                    ? loadViaLibRaw(fileBytes, width, height, preserveAspectRatio, smooth, imagePixelScale)
                    : loadViaImageIO(fileBytes, width, height, preserveAspectRatio, smooth, imagePixelScale);
        } catch (IOException e) {
            firstFailure = e;
            LOG.log(Level.FINE, "First decoder failed, trying fallback: {0}", e.toString());
        }

        try {
            return looksRaw
                    ? loadViaImageIO(fileBytes, width, height, preserveAspectRatio, smooth, imagePixelScale)
                    : loadViaLibRaw(fileBytes, width, height, preserveAspectRatio, smooth, imagePixelScale);
        } catch (IOException secondFailure) {
            firstFailure.addSuppressed(secondFailure);
            LOG.log(Level.SEVERE, "Neither LibRaw nor ImageIO could decode the image", firstFailure);
            throw firstFailure;
        }
    }

    private ImageFrame loadViaLibRaw(byte[] fileBytes, double width, double height,
            boolean preserveAspectRatio, boolean smooth, float imagePixelScale) throws IOException {
        accessLock.lock();

        ByteBuffer imageData;
        int rawWidth;
        int rawHeight;
        int rawStride;
        int bands;
        int targetW;
        int targetH;
        try {
            updateImageProgress(0);
            long start = System.currentTimeMillis();
            imageData = getImageData(libraw, fileBytes);
            LOG.log(Level.FINE, "Raw loading took: {0}s", (System.currentTimeMillis() - start) / 1000.0);
            updateImageProgress(lastPercentDone + 1);

            rawWidth = libraw.getImageWidth();
            rawHeight = libraw.getImageHeight();
            rawStride = libraw.getStride();
            bands = libraw.getNumBands();

            if (imageData == null || rawWidth <= 0 || rawHeight <= 0 || bands <= 0) {
                throw new IOException("Error decompressing RAW image stream!");
            }

            int[] wh = ImageTools.computeDimensions(rawWidth, rawHeight,
                    (int) width, (int) height, preserveAspectRatio);
            targetW = wh[0];
            targetH = wh[1];
            updateImageProgress(lastPercentDone + 1);
        } catch (IOException e) {
            throw e;
        } catch (Throwable t) {
            // z. B. UnsatisfiedLinkError, OutOfMemoryError aus dem nativen Teil
            throw new IOException("LibRaw failed: " + t, t);
        } finally {
            accessLock.unlock();
            dispose();
        }

        ImageMetadata md = new ImageMetadata(null, true, null, null, null, null, null,
                targetW, targetH, null, null, null);
        updateImageMetadata(md);
        updateImageProgress(lastPercentDone + 1);

        int stride;
        if (rawWidth != targetW || rawHeight != targetH) {
            imageData = ImageTools.scaleImage(imageData, rawWidth, rawHeight, bands, targetW, targetH, smooth);
            stride = targetW * bands;                       // scaleImage liefert dicht gepackte Zeilen
        } else {
            stride = Math.max(rawStride, rawWidth * bands); // Original-Stride, falls größer (Padding)
        }
        updateImageProgress(lastPercentDone + 1);

        ImageFrame frame = new ImageFrame(ImageStorage.ImageType.RGB, imageData, targetW, targetH,
                stride, imagePixelScale, md);
        updateImageProgress(100f);
        return frame;
    }

    private ImageFrame loadViaImageIO(byte[] data, double width, double height,
            boolean preserveAspectRatio, boolean smooth, float imagePixelScale) throws IOException {
        BufferedImage src;
        try {
            src = ImageIO.read(new ByteArrayInputStream(data));
        } catch (RuntimeException | OutOfMemoryError e) {
            throw new IOException("ImageIO failed: " + e, e);   // manche TIFF-Varianten werfen Runtime-Fehler
        }
        if (src == null) {
            throw new IOException("Unsupported image format");
        }

        int[] wh = ImageTools.computeDimensions(src.getWidth(), src.getHeight(),
                (int) width, (int) height, preserveAspectRatio);
        int w = wh[0];
        int h = wh[1];

        // JavaFX erwartet vormultiplizierte Pixel (INT_ARGB_PRE)
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, smooth
                    ? RenderingHints.VALUE_INTERPOLATION_BILINEAR
                    : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(src, 0, 0, w, h, null);
        } finally {
            g.dispose();
        }

        int[] px = ((DataBufferInt) out.getRaster().getDataBuffer()).getData();
        ImageMetadata md = new ImageMetadata(null, true, null, null, null, null, null,
                w, h, null, null, null);
        updateImageMetadata(md);
        updateImageProgress(100f);
        return new ImageFrame(ImageStorage.ImageType.INT_ARGB_PRE, IntBuffer.wrap(px),
                w, h, w, imagePixelScale, md);   // Stride in Ints, nicht in Bytes
    }

    /** Native Anbindung. Das Ergebnis ist ein Java-byte[] und damit unabhängig von dispose(). */
    private synchronized ByteBuffer getImageData(LibrawImage libraw, byte[] fileBytes) throws IOException {
        long start = System.currentTimeMillis();
        byte[] raw = libraw.readPixelDataFromStream(fileBytes);
        LOG.log(Level.FINE, "Raw convert took: {0}s", (System.currentTimeMillis() - start) / 1000.0);
        return raw == null ? null : ByteBuffer.wrap(raw);
    }

    /** Wird von LibrawImage aufgerufen, um den Fortschritt zu melden. */
    public void updateImageProgress(int outLinesDecoded, int outHeight) {
        if (outHeight <= 0) {
            return;
        }
        updateImageProgress(100.0F * outLinesDecoded / outHeight);
    }

    public float calculateMaxRenderScale() {
        float maxRenderScale = 0;
        ScreenHelper.ScreenAccessor accessor = ScreenHelper.getScreenAccessor();
        for (Screen screen : Screen.getScreens()) {
            maxRenderScale = Math.max(maxRenderScale, accessor.getRenderScale(screen));
        }
        return maxRenderScale;
    }

    private static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(1 << 20);
        byte[] b = new byte[64 * 1024];
        int n;
        while ((n = in.read(b)) != -1) {
            buffer.write(b, 0, n);
        }
        return buffer.toByteArray();
    }

    private static final class Lock {

        private boolean locked;

        synchronized boolean isLocked() {
            return locked;
        }

        synchronized void lock() {
            if (locked) {
                throw new IllegalStateException("Recursive loading is not allowed.");
            }
            locked = true;
        }

        synchronized void unlock() {
            if (!locked) {
                throw new IllegalStateException("Invalid loader state.");
            }
            locked = false;
        }
    }
}