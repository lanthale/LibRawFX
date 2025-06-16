/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import com.sun.javafx.iio.ImageFormatDescription;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ServiceRegistry;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.stream.ImageInputStream;
import org.librawjava.LibrawImage;

/**
 *
 * @author selfemp
 */
public class RAWImageIOReaderSpi extends RAWImageReaderSpiBase {
    
    private static final ImageFormatDescription.Signature[] signatures = {
        new ImageFormatDescription.Signature(hexStringToByteArray("49491A0000004845")),//cr2
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00100000004352")),//crw3
        new ImageFormatDescription.Signature(hexStringToByteArray("46554A4946494C4D4343")),//nef
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00081b0300a9aa")),//nef again         
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00080000001C00")),//nrw
        new ImageFormatDescription.Signature(hexStringToByteArray("464F5662000004000100")),//raf
        new ImageFormatDescription.Signature(hexStringToByteArray("464F5662000003003030")),//x3f
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00080000003600")),//dng
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A003E5C02004D4D")),//dng2
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00080000001300")),//sony arw
        new ImageFormatDescription.Signature(hexStringToByteArray("49495500080000002200")) //Leica raw
    };
    

    public RAWImageIOReaderSpi() {
        super(new RAWImageIOProviderInfo());
    }
    
    
    
    @SuppressWarnings("unchecked")
    @Override
    public void onRegistration(final ServiceRegistry registry, final Class<?> category) {
        ImageReaderSpi defaultProvider = lookupProviderByName(registry, "org.librawimageio.RAWImageIOReaderSpi");

        install();
        
        if (defaultProvider != null) {
            // Order before com.sun provider, to aid ImageIO in selecting our reader
            //registry.setOrdering((Class<ImageReaderSpi>) category, this, defaultProvider);
        }
    }

    public static final void install() {
        try {
            LibrawImage.loadLibs(null);
        } catch (IOException ex) {
            Logger.getLogger(RAWImageIOReaderSpi.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }  
    

    @Override
    public String[] getFormatNames() {
        return names;
    }

    

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream)) {
            return false;
        }

        ImageInputStream stream = (ImageInputStream) source;
        byte[] b = new byte[16];
        try {
            stream.mark();
            stream.readFully(b);
            stream.reset();
        } catch (IOException e) {
            return false;
        } finally {
            stream.reset();
        }

        for (ImageFormatDescription.Signature signature : signatures) {
            if (signature.matches(b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new RAWImageIOReader(this);
    }
    

    @Override
    public String getDescription(Locale locale) {
        return "RAW image format support via Libraw";
    }

    public static byte[] hexStringToByteArray(String input) {
        int len = input.length();

        if (len == 0) {
            return new byte[]{};
        }

        byte[] data;
        int startIdx;
        if (len % 2 != 0) {
            data = new byte[(len / 2) + 1];
            data[0] = (byte) Character.digit(input.charAt(0), 16);
            startIdx = 1;
        } else {
            data = new byte[len / 2];
            startIdx = 0;
        }

        for (int i = startIdx; i < len; i += 2) {
            data[(i + 1) / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i + 1), 16));
        }
        return data;
    }
    
    private static <T> T lookupProviderByName(final ServiceRegistry registry, final String providerClassName) {
        try {
            return (T) registry.getServiceProviderByClass(Class.forName(providerClassName));
        } catch (ClassNotFoundException ignore) {
            return null;
        }
    }

}
