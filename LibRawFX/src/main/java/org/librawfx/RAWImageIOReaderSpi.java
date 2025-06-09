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

    static final String vendorName = "LibrawFX";
    static final String version = "1.0";
    static final String readerClassName
            = "org.librawfx.RAWImageIOReader";
    static final String[] names = {"myformat"};
    static final String[] suffixes = {"cr2", "crw", "cr3", "nef", "nrw", "raf", "x3f", "dng", "raw", "rwl", "mef", "mfw", "orf", "ori", "rw2", "pef", "srw", "arw"};
    static final String[] MIMETypes = {
        "image/x-raw"};
    static final String[] writerSpiNames = {
        "org.librawfx.RAWImageWriterSpi"};

    // Metadata formats, more information below
    static final boolean supportsStandardStreamMetadataFormat = false;
    static final String nativeStreamMetadataFormatName = null;
    static final String nativeStreamMetadataFormatClassName = null;
    static final String[] extraStreamMetadataFormatNames = null;
    static final String[] extraStreamMetadataFormatClassNames = null;
    static final boolean supportsStandardImageMetadataFormat = false;
    static final String nativeImageMetadataFormatName
            = "org.librawfx.RAWMetadata_1.0";
    static final String nativeImageMetadataFormatClassName
            = "org.librawfx.RAWMetadata";
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
