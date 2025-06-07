/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawfx;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author selfemp
 */
public class RAWImageIOReaderSpi extends ImageReaderSpi {

    static final String vendorName = "My Company";
    static final String version = "1.0_beta33_build9467";
    static final String readerClassName
            = "com.mycompany.imageio.MyFormatImageReader";
    static final String[] names = {"myformat"};
    static final String[] suffixes = {"myf"};
    static final String[] MIMETypes = {
        "image/x-myformat"};
    static final String[] writerSpiNames = {
        "com.mycompany.imageio.MyFormatImageWriterSpi"};

    // Metadata formats, more information below
    static final boolean supportsStandardStreamMetadataFormat = false;
    static final String nativeStreamMetadataFormatName = null;
    static final String nativeStreamMetadataFormatClassName = null;
    static final String[] extraStreamMetadataFormatNames = null;
    static final String[] extraStreamMetadataFormatClassNames = null;
    static final boolean supportsStandardImageMetadataFormat = false;
    static final String nativeImageMetadataFormatName
            = "com.mycompany.imageio.MyFormatMetadata_1.0";
    static final String nativeImageMetadataFormatClassName
            = "com.mycompany.imageio.MyFormatMetadata";
    static final String[] extraImageMetadataFormatNames = null;
    static final String[] extraImageMetadataFormatClassNames = null;

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream)) {
            return false;
        }

        ImageInputStream stream = (ImageInputStream) source;
        byte[] b = new byte[8];
        try {
            stream.mark();
            stream.readFully(b);
            stream.reset();
        } catch (IOException e) {
            return false;
        }

        // Cast unsigned character constants prior to comparison
        return (b[0] == (byte) 'm' && b[1] == (byte) 'y'
                && b[2] == (byte) 'f' && b[3] == (byte) 'o'
                && b[4] == (byte) 'r' && b[5] == (byte) 'm'
                && b[6] == (byte) 'a' && b[7] == (byte) 't');
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new RAWImageIOReader(this);
    }

    @Override
    public String getDescription(Locale locale) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
