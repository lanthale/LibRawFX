/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import org.librawfx.dimension.DimensionProvider;
import org.librawfx.dimension.Dimension;
import com.sun.javafx.iio.ImageFrame;
import com.sun.javafx.iio.ImageMetadata;
import com.sun.javafx.iio.ImageStorage;
import com.sun.javafx.iio.common.ImageLoaderImpl;
import com.sun.javafx.iio.common.ImageTools;
import com.sun.javafx.iio.common.PushbroomScaler;
import com.sun.javafx.iio.common.ScalerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Screen;

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

    protected RAWImageLoader(InputStream input, DimensionProvider dimensionProvider) {
        super(RAWDescriptor.getInstance());
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }
        this.input = input;
        this.dimensionProvider = dimensionProvider;
        libraw = new LibrawImage(this);
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
    public ImageFrame load(int imageIndex, int width, int height, boolean preserveAspectRatio, boolean smooth) throws IOException {
        if (0 != imageIndex) {
            return null;
        }
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "locking native call " + accessLock.isLocked());
        accessLock.lock();

        Dimension fallbackDimension = (width <= 0 || height <= 0) ? dimensionProvider.getDimension() : null;

        float imageWidth = width > 0 ? width : (float) fallbackDimension.getWidth();
        float imageHeight = height > 0 ? height : (float) fallbackDimension.getHeight();

        ByteBuffer imageData = null;
        short rawImageWidth = -1;
        short rawImageHeight = -1;
        int rawImageStride = 0;
        try {
            updateImageProgress(0);
            imageData = getImageData(libraw);
            rawImageWidth = libraw.getImageWidth();
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "rawImageWidth " + rawImageWidth);
            rawImageHeight = libraw.getImageHeight();
            rawImageStride = libraw.getStride();
        } catch (IOException e) {
            throw e;
        } catch (Throwable et) {
            throw new IOException(et);
        } finally {
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Unlock native access...");
            accessLock.unlock();
            dispose();
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Unlock native access...finished");
        }
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "locked 2: " + accessLock.isLocked());

        if (imageData == null) {
            Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Error decompressing RAW Image stream!");
            throw new IOException("Error decompressing RAW Image stream!");
        }

        ImageMetadata md = new ImageMetadata(null, true,
                null, null, null, null, null,
                (int) rawImageWidth, (int) rawImageHeight, null, null, null);

        updateImageMetadata(md);

        /*if (rawImageWidth != width || rawImageHeight != height) {
            System.out.println("scale to request size " + imageData.hasArray());
            imageData = scaleImage(imageData, rawImageWidth, rawImageHeight, libraw.getNumBands(), width, height, smooth);
            System.out.println("scale to request size-finished");
        }
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Creating image frame...");
        ImageFrame createImageFrame = new FixedPixelDensityImageFrame(ImageStorage.ImageType.RGB, imageData, width,
                height, rawImageStride, null, getPixelScale(), null);
        Logger.getLogger(RAWImageLoader.class.getName()).log(Level.FINEST, null, "Creating image frame...finished");*/
        ImageFrame createImageFrame = new FixedPixelDensityImageFrame(ImageStorage.ImageType.RGB, imageData, rawImageWidth,
                rawImageHeight, rawImageStride, null, getPixelScale(), null);
        return createImageFrame;
    }

    public ByteBuffer scaleImage(ByteBuffer src,
            int sourceWidth, int sourceHeight, int numBands,
            int destWidth, int destHeight, boolean isSmooth) {
        System.out.println("Create scaler instance");
        PushbroomScaler scaler = ScalerFactory.createScaler(
                sourceWidth, sourceHeight, numBands,
                destWidth, destHeight, isSmooth);
        System.out.println("Create scaler instance-finished");
        int stride = sourceWidth * numBands;
        System.out.println("Stride was " + stride);        
        System.out.println("Stride native was " + libraw.getStride());
        stride = libraw.getStride();
        System.out.println("Stride native image bits " + libraw.getImageBits());
        System.out.println("Stride native image colors " + libraw.getImageColors());
        System.out.println("Start scaling...");
        if (src.hasArray()) {
            System.out.println("hasArray==true");
            byte image[] = src.array();
            for (int y = 0; y != sourceHeight; ++y) {
                scaler.putSourceScanline(image, y * stride);
            }
            System.out.println("hasArray==true-finished");
        } else {
            System.out.println("hasArray==false");
            byte scanline[] = new byte[stride];
            for (int y = 0; y != sourceHeight; ++y) {
                src.get(scanline);
                scaler.putSourceScanline(scanline, 0);
            }
            System.out.println("hasArray==false-finished");
        }
        System.out.println("Start scaling...finished");

        return scaler.getDestination();
    }

    public float getPixelScale() {
        if (maxPixelScale == 0) {
            maxPixelScale = calculateMaxRenderScale();
        }
        return maxPixelScale;
    }

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
    private synchronized ByteBuffer getImageData(LibrawImage libraw) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] datab = new byte[1024];
        while ((nRead = input.read(datab, 0, datab.length)) != -1) {
            buffer.write(datab, 0, nRead);
        }
        buffer.flush();
        byte[] targetArray = buffer.toByteArray();
        byte[] raw = libraw.readPixelDataFromStream(targetArray);
        updateImageProgress(100f);
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
        updateImageProgress(100.0F * outLinesDecoded / outHeight);
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

}
