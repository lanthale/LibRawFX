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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javafx.stage.Screen;

/**
 * Class to load the image requested by the file
 *
 * @author selfemp
 */
public class RAWImageLoader extends ImageLoaderImpl {

    private static final int BYTES_PER_PIXEL = 3; // RGB

    private final InputStream input;
    private float maxPixelScale = 0;
    private final DimensionProvider dimensionProvider;
    private Lock accessLock = new Lock();
    private boolean isDisposed = false;
    private LibrawImage libraw;

    protected RAWImageLoader(InputStream input, DimensionProvider dimensionProvider) {
        super(RAWDescriptor.getInstance());        

        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }
        this.input = input;
        this.dimensionProvider = dimensionProvider;
    }

    @Override
    public void dispose() {
        if (!accessLock.isLocked() && !isDisposed) {
            isDisposed = true;            
            libraw.dispose();
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
        accessLock.lock();
        libraw = new LibrawImage(input);
        Dimension fallbackDimension = (width <= 0 || height <= 0) ? dimensionProvider.getDimension() : null;

        float imageWidth = width > 0 ? width : (float) fallbackDimension.getWidth();
        float imageHeight = height > 0 ? height : (float) fallbackDimension.getHeight();

        ImageMetadata md = new ImageMetadata(null, true,
                null, null, null, null, null,
                width, height, null, null, null);

        updateImageMetadata(md);

        try {
            return createImageFrame(imageWidth, imageHeight, getPixelScale(), libraw);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally {
            accessLock.unlock();
            dispose();
        }
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

    private ImageFrame createImageFrame(float width, float height, float pixelScale, LibrawImage libraw)
            throws IOException {
        ByteBuffer imageData = null;
        if (width > 300) {
            imageData = getImageData(false, libraw);
        } else {
            imageData = getImageData(true, libraw);
        }

        return new FixedPixelDensityImageFrame(ImageStorage.ImageType.RGB, imageData, libraw.getImageWidth(),
                libraw.getImageHeight(), libraw.getStride(), null, pixelScale, null);
    }

    private ByteBuffer getImageData(boolean halfsize, LibrawImage libraw) throws IOException {
        libraw.setHalfSize(halfsize);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] datab = new byte[1024];
        while ((nRead = input.read(datab, 0, datab.length)) != -1) {
            buffer.write(datab, 0, nRead);
        }
        buffer.flush();
        byte[] targetArray = buffer.toByteArray();
        byte[] raw = libraw.readPixelDataFromStream(targetArray);

        return ByteBuffer.wrap(raw);
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
