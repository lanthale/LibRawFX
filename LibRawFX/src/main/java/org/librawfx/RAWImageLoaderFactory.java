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

/**
 * Class to install the TIFF renderer to the javafx image systems
 * @author selfemp
 */
public class RAWImageLoaderFactory implements ImageLoaderFactory {

    private static final RAWImageLoaderFactory instance = new RAWImageLoaderFactory();

    private static DimensionProvider dimensionProvider;

    public static final void install() {
        install(new DefaultDimensionProvider());
    }

    public static final void install(DimensionProvider dimensionProvider) {
        RAWImageLoaderFactory.dimensionProvider = dimensionProvider;

        ImageStorage.addImageLoaderFactory(instance);        
    }

    public static final ImageLoaderFactory getInstance() {
        return instance;
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