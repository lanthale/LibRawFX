/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import org.librawfx.dimension.DimensionProvider;
import org.librawfx.dimension.Dimension;
import com.sun.javafx.iio.ImageFrame;
import com.sun.javafx.iio.ImageLoadListener;
import com.sun.javafx.iio.ImageMetadata;
import com.sun.javafx.iio.ImageStorage;
import com.sun.javafx.iio.common.ImageLoaderImpl;
import com.sun.javafx.iio.common.ImageTools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashSet;
import javafx.stage.Screen;

/**
 * Class to load the image requested by the file
 *
 * @author selfemp
 */
public class RAWImageLoader extends ImageLoaderImpl {

    private final InputStream input;
    private float maxPixelScale = 0;
    private final DimensionProvider dimensionProvider;
    private final Lock accessLock = new Lock();
    private boolean isDisposed = false;
    private static LibrawImage libraw;
    /** Set by setInputAttributes native code callback */
    private int inWidth;
    /** Set by setInputAttributes native code callback */
    private int inHeight;

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

        Dimension fallbackDimension = (width <= 0 || height <= 0) ? dimensionProvider.getDimension() : null;

        float imageWidth = width > 0 ? width : (float) fallbackDimension.getWidth();
        float imageHeight = height > 0 ? height : (float) fallbackDimension.getHeight();

        ImageMetadata md = new ImageMetadata(null, true,
                null, null, null, null, null,
                width, height, null, null, null);

        updateImageMetadata(md);
        ByteBuffer imageData = null;
        try {
            updateImageProgress(0);
            imageData = getImageData(libraw);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally {
            accessLock.unlock();
            dispose();
        }
        if (imageData == null) {
            throw new IOException("Error decompressing RAW Image stream!");
        }

        /*System.out.println("scale to request size");
        if (libraw.getImageWidth() != width || libraw.getImageHeight() != height) {
            imageData = ImageTools.scaleImage(imageData,
                    libraw.getImageWidth(), libraw.getImageHeight(), libraw.getNumBands(), width, height, smooth);
        }
        System.out.println("scale to request size-finished");*/
        return createImageFrame(imageData, imageWidth, imageHeight, getPixelScale(), libraw);
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

    private ImageFrame createImageFrame(ByteBuffer imageData, float width, float height, float pixelScale, LibrawImage libraw)
            throws IOException {
        return new FixedPixelDensityImageFrame(ImageStorage.ImageType.RGB, imageData, libraw.getImageWidth(),
                libraw.getImageHeight(), libraw.getStride(), null, pixelScale, null);
    }

    private ByteBuffer getImageData(LibrawImage libraw) throws IOException {        
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
    
    public void updateImageProgress(int outLinesDecoded, int outHeight) {
        float res=100.0F * outLinesDecoded / outHeight;        
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
