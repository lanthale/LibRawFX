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
                new String[] {"bmp", "BMP"},
                new String[] {"bmp", "rle"},
                new String[] {
                        "image/bmp",
                        "image/x-bmp",
                        "image/vnd.microsoft.bitmap"
                },
                "com.twelvemonkeys.imageio.plugins.bmp.BMPImageReader",
                new String[] {"com.twelvemonkeys.imageio.plugins.bmp.BMPImageReaderSpi"},
                "com.twelvemonkeys.imageio.plugins.bmp.BMPImageWriter",
                new String[] {"com.twelvemonkeys.imageio.plugins.bmp.BMPImageWriterSpi"},
                false, null, null,
                null, null,
                true, RAWIOFormatMetadata.nativeMetadataFormatName, "com.sun.imageio.plugins.bmp.BMPMetadataFormat",
                null, null
        );
    }
}
