/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;

/**
 *
 * @author cleme
 */
public class RAWImageReaderSpiBase extends ImageReaderSpi {
 protected RAWImageReaderSpiBase(final RAWImageIOReaderWriterProviderInfo info) {
        super(
                info.getVendorName(), info.getVersion(),
                info.formatNames(), info.suffixes(), info.mimeTypes(),
                info.readerClassName(), info.inputTypes(),
                info.writerSpiClassNames(),
                info.supportsStandardStreamMetadataFormat(),
                info.nativeStreamMetadataFormatName(), info.nativeStreamMetadataFormatClassName(),
                info.extraStreamMetadataFormatNames(), info.extraStreamMetadataFormatClassNames(),
                info.supportsStandardImageMetadataFormat(),
                info.nativeImageMetadataFormatName(), info.nativeImageMetadataFormatClassName(),
                info.extraImageMetadataFormatNames(), info.extraImageMetadataFormatClassNames()
        );
    }   

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getDescription(Locale locale) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
