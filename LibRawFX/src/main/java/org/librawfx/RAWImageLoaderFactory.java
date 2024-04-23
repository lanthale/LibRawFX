/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import org.librawfx.dimension.DimensionProvider;
import org.librawfx.dimension.DefaultDimensionProvider;
import com.sun.javafx.iio.ImageFormatDescription;
import com.sun.javafx.iio.ImageLoader;
import com.sun.javafx.iio.ImageLoaderFactory;
import com.sun.javafx.iio.ImageStorage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to install the RAW renderer to the javafx image systems
 * @author selfemp
 */
public class RAWImageLoaderFactory implements ImageLoaderFactory {

    private static final RAWImageLoaderFactory instance = new RAWImageLoaderFactory();
    
    private static String tempDir;

    private static DimensionProvider dimensionProvider;

    /**
     * Main method to install the image handler to support any loading of images with the Image class
     * @param tempdir the directory to specify where the native libs are extracted. Import on OSX because there is a bug on Apple side preventing using the temporary directory
     */
    public static final void install(String tempdir) {  
        RAWImageLoaderFactory.tempDir=tempdir;
        install(new DefaultDimensionProvider());        
    }
    
    /**
     * Main method to install the image handler to support any loading of images with the Image class
     */
    public static final void install() {  
        RAWImageLoaderFactory.tempDir=null;
        install(new DefaultDimensionProvider());          
    }

    public static final void install(DimensionProvider dimensionProvider) {
        try {
            LibrawImage.loadLibs(tempDir);
        } catch (IOException ex) {
            Logger.getLogger(RAWImageLoaderFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        RAWImageLoaderFactory.dimensionProvider = dimensionProvider;
        RAWImageLoader.initSettings();        
        ImageStorage.getInstance().addImageLoaderFactory(instance);        
    }    

    public static final ImageLoaderFactory getInstance() {
        return instance;
    }
    
    public static final HashMap<String, RawDecoderSettings> getDecoderSettings(){        
        return RAWImageLoader.getSettings();
    }

    @Override
    public ImageFormatDescription getFormatDescription() {
        return RAWDescriptor.getInstance();
    }

    @Override
    public ImageLoader createImageLoader(InputStream input) throws IOException {
        return new RAWImageLoader(input, dimensionProvider);
    }        

}
