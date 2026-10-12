/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Screen;
import javax.imageio.ImageIO;

/**
 * Class to load the raw image requested by the input stream
 *
 * @author Clemens Lanthaler
 */
public class RAWImageLoader extends ImageLoaderImpl {

    private final InputStream input;
    private float maxPixelScale = 0;
    private final DimensionProvider dimensionProvider;
    private final Lock accessLock = new Lock();
    private boolean isDisposed = false;
    private LibrawImage libraw;
    private static ConcurrentHashMap<String, RawDecoderSettings> settings = new ConcurrentHashMap<>();

    protected RAWImageLoader(InputStream input, DimensionProvider dimensionProvider) {
        super(RAWDescriptor.getInstance());
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }
        this.input = input;
        this.dimensionProvider = dimensionProvider;
        initSettings();
        libraw = new LibrawImage(this, settings);
    }

    public static void initSettings() {
        if (settings.isEmpty()) {
            settings.put("Default", new RawDecoderSettings());
        }
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
        super.emitWarning(string); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ImageFrame load(int imageIndex, double width, double height, boolean preserveAspectRatio, boolean smooth, float screenPixelScale, float imagePixelScale) throws IOException {
        if (0 != imageIndex) {
            return null;
        }
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "locking native call " + accessLock.isLocked());

        byte[] fileBytes = readAll(input);
        byte[] head = Arrays.copyOf(fileBytes, Math.min(fileBytes.length, RawFormatDetector.HEADER_SIZE));
        if (!RawFormatDetector.isRaw(head)) {
            // Kein RAW (z. B. normales TIFF): über ImageIO laden
            return loadViaImageIO(fileBytes, width, height, preserveAspectRatio, smooth, imagePixelScale);
        }
        accessLock.lock();

        ByteBuffer imageData = null;
        short rawImageWidth = -1;
        short rawImageHeight = -1;
        int rawImageStride = 0;
        try {
            updateImageProgress(0);
            long start = System.currentTimeMillis();
            imageData = getImageData(libraw, fileBytes);
            double diff = (System.currentTimeMillis() - start) / 1000;
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINE, null, "Raw loading took: " + diff + "s");
            updateImageProgress(lastPercentDone + 1);
            rawImageWidth = libraw.getImageWidth();
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "rawImageWidth " + rawImageWidth);
            rawImageHeight = libraw.getImageHeight();
            rawImageStride = libraw.getStride();
            int[] widthHeight = ImageTools.computeDimensions(rawImageWidth, rawImageHeight, (int) width, (int) height, preserveAspectRatio);
            width = widthHeight[0];
            height = widthHeight[1];
            updateImageProgress(lastPercentDone + 1);
        } catch (IOException e) {
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } catch (Throwable et) {
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.SEVERE, null, et);
            throw new IOException(et);
        } finally {
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Unlock native access...");
            accessLock.unlock();
            dispose();
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Unlock native access...finished");
        }
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "locked 2: " + accessLock.isLocked());

        if (imageData == null) {
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.SEVERE, null, "Error decompressing RAW Image stream!");
            throw new IOException("Error decompressing RAW Image stream!");
        }

        ImageMetadata md = new ImageMetadata(null, true,
                null, null, null, null, null,
                (int) width, (int) height, null, null, null);
        updateImageMetadata(md);
        updateImageProgress(lastPercentDone + 1);

        if (rawImageWidth != width || rawImageHeight != height) {
            imageData = ImageTools.scaleImage(imageData, rawImageWidth, rawImageHeight, libraw.getNumBands(), (int) width, (int) height, smooth);
        }
        updateImageProgress(lastPercentDone + 1);
        rawImageStride = (int) width * libraw.getNumBands();
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Creating image frame...");
        ImageFrame createImageFrame = new ImageFrame(ImageStorage.ImageType.RGB, imageData, (int) width,
                (int) height, rawImageStride, imagePixelScale, md);
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Creating image frame...finished");
        updateImageProgress(100f);
        return createImageFrame;
    }

    /*public float getPixelScale() {
        if (maxPixelScale == 0) {
            maxPixelScale = calculateMaxRenderScale();
        }
        return maxPixelScale;
    }*/
    public float calculateMaxRenderScale() {
        float maxRenderScale = 0;
        ScreenHelper.ScreenAccessor accessor = ScreenHelper.getScreenAccessor();
        for (Screen screen : Screen.getScreens()) {
            maxRenderScale = Math.max(maxRenderScale, accessor.getRenderScale(screen));
        }
        return maxRenderScale;
    }

    /**
     * method to handle the interaction with the native lib
     *
     * @param libraw the instance of the LibrawImage used to update the loading
     * process
     * @return ByteBuffer of the read image
     * @throws IOException if the input stream cannot be read
     */
    private synchronized ByteBuffer getImageData(LibrawImage libraw, byte[] targetArray) throws IOException {
        long reading = System.currentTimeMillis();
        byte[] raw = libraw.readPixelDataFromStream(targetArray);
        double diff = (System.currentTimeMillis() - reading) / 1000.0;
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINE, "Raw convert took: " + diff + "s");
        return ByteBuffer.wrap(raw);
    }

    /**
     * Method called by the class LibrawImage to update the progress
     *
     * @param outLinesDecoded Lines decoded
     * @param outHeight overall height of the native image
     */
    public void updateImageProgress(int outLinesDecoded, int outHeight) {
        float res = 100.0F * outLinesDecoded / outHeight;
        updateImageProgress(res);
    }

    private static class Lock {

        private boolean locked;

        public Lock() {
            locked = false;
        }

        public synchronized boolean isLocked() {
            return locked;
        }

        public synchronized void lock() {
            if (locked) {
                throw new IllegalStateException("Recursive loading is not allowed.");
            }
            locked = true;
        }

        public synchronized void unlock() {
            if (!locked) {
                throw new IllegalStateException("Invalid loader state.");
            }
            locked = false;
        }
    }

    public static final ConcurrentHashMap<String, RawDecoderSettings> getSettings() {
        if (settings == null) {
            initSettings();
        }
        return settings;
    }

    private static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] b = new byte[8192];
        int n;
        while ((n = in.read(b)) != -1) {
            buffer.write(b, 0, n);
        }
        return buffer.toByteArray();
    }

    private ImageFrame loadViaImageIO(byte[] data, double width, double height,
            boolean preserveAspectRatio, boolean smooth, float imagePixelScale) throws IOException {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(data));
        if (src == null) {
            throw new IOException("Unsupported image format");
        }
        int[] wh = ImageTools.computeDimensions(src.getWidth(), src.getHeight(),
                (int) width, (int) height, preserveAspectRatio);
        int w = wh[0], h = wh[1];

        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, smooth
                    ? RenderingHints.VALUE_INTERPOLATION_BILINEAR
                    : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g.drawImage(src, 0, 0, w, h, null);
        } finally {
            g.dispose();
        }

        int[] px = ((DataBufferInt) out.getRaster().getDataBuffer()).getData();
        ImageMetadata md = new ImageMetadata(null, true, null, null, null, null, null,
                w, h, null, null, null);
        updateImageMetadata(md);
        return new ImageFrame(ImageStorage.ImageType.INT_ARGB, IntBuffer.wrap(px),
                w, h, w, imagePixelScale, md);   // Stride in Ints, nicht in Bytes
    }

}
