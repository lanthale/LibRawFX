/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

/**
 *
 * @author cleme
 */
public class RAWImageIOProviderInfo extends RAWImageIOReaderWriterProviderInfo{
     public RAWImageIOProviderInfo() {
        super(
                RAWImageIOProviderInfo.class,
                new String[] {"cr2", "crw", "cr3", "nef", "nrw", "raf", "x3f", "dng", "raw", "rwl", "mef", "mfw", "orf", "ori", "rw2", "pef", "srw", "arw"},
                new String[] {"cr2", "crw", "cr3", "nef", "nrw", "raf", "x3f", "dng", "raw", "rwl", "mef", "mfw", "orf", "ori", "rw2", "pef", "srw", "arw"},
                new String[] {
                        "image/cr2",
                        "image/crw",
                        "image/cr3",
                        "image/nef",
                        "image/nrw",
                        "image/raf",
                        "image/x3f",
                        "image/dng",
                        "image/raw",
                        "image/rwl",
                        "image/mef",
                        "image/mfw",
                        "image/orf",
                        "image/ori",
                        "image/rw2",
                        "image/pef",
                        "image/srw",
                        "image/arw"
                },
                "org.librawimageio.RAWImageIOReader",
                new String[] {"org.librawimageio.RAWImageReaderSpiBase"},
                "com.twelvemonkeys.imageio.plugins.bmp.BMPImageWriter",
                new String[] {"com.twelvemonkeys.imageio.plugins.bmp.BMPImageWriterSpi"},
                false, null, null,
                null, null,
                true, RAWIOFormatMetadata.nativeMetadataFormatName, "org.librawimageio.RAWIOMetadataFormat",
                null, null
        );
    }
}
