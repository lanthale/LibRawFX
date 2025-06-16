/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author cleme
 */
public class RAWImageIOReaderWriterProviderInfo extends ProviderInfo {
    private final String[] formatNames;
    private final String[] suffixes;
    private final String[] mimeTypes;
    private final String readerClassName;
    private final String[] readerSpiClassNames;
    private final Class<?>[] inputTypes = new Class<?>[] {ImageInputStream.class};
    private final String writerClassName;
    private final String[] writerSpiClassNames;
    private final Class<?>[] outputTypes = new Class<?>[] {ImageOutputStream.class};
    private final boolean supportsStandardStreamMetadata;
    private final String nativeStreamMetadataFormatName;
    private final String nativeStreamMetadataFormatClassName;
    private final String[] extraStreamMetadataFormatNames;
    private final String[] extraStreamMetadataFormatClassNames;
    private final boolean supportsStandardImageMetadata;
    private final String nativeImageMetadataFormatName;
    private final String nativeImageMetadataFormatClassName;
    private final String[] extraImageMetadataFormatNames;
    private final String[] extraImageMetadataFormatClassNames;

    /**
     * Creates a provider information instance based on the given class.
     *
     * @param infoClass the class to get provider information from.
     *                  The provider info will be taken from the class' package.
     * @param extraImageMetadataFormatClassNames
     * @throws IllegalArgumentException if {@code pPackage == null}
     */
    protected RAWImageIOReaderWriterProviderInfo(final Class<? extends RAWImageIOReaderWriterProviderInfo> infoClass,
                                       final String[] formatNames,
                                       final String[] suffixes,
                                       final String[] mimeTypes,
                                       final String readerClassName,
                                       final String[] readerSpiClassNames,
                                       final String writerClassName,
                                       final String[] writerSpiClassNames,
                                       final boolean supportsStandardStreamMetadata,
                                       final String nativeStreamMetadataFormatName,
                                       final String nativeStreamMetadataFormatClassName,
                                       final String[] extraStreamMetadataFormatNames,
                                       final String[] extraStreamMetadataFormatClassNames,
                                       final boolean supportsStandardImageMetadata,
                                       final String nativeImageMetadataFormatName,
                                       final String nativeImageMetadataFormatClassName,
                                       final String[] extraImageMetadataFormatNames,
                                       final String[] extraImageMetadataFormatClassNames) {
        super(infoClass.getPackage());

        this.formatNames = formatNames;
        this.suffixes = suffixes;
        this.mimeTypes = mimeTypes;
        this.readerClassName = readerClassName;
        this.readerSpiClassNames = readerSpiClassNames;
        this.writerClassName = writerClassName;
        this.writerSpiClassNames = writerSpiClassNames;
        this.supportsStandardStreamMetadata = supportsStandardStreamMetadata;
        this.nativeStreamMetadataFormatName = nativeStreamMetadataFormatName;
        this.nativeStreamMetadataFormatClassName = nativeStreamMetadataFormatClassName;
        this.extraStreamMetadataFormatNames = extraStreamMetadataFormatNames;
        this.extraStreamMetadataFormatClassNames = extraStreamMetadataFormatClassNames;
        this.supportsStandardImageMetadata = supportsStandardImageMetadata;
        this.nativeImageMetadataFormatName = nativeImageMetadataFormatName;
        this.nativeImageMetadataFormatClassName = nativeImageMetadataFormatClassName;
        this.extraImageMetadataFormatNames = extraImageMetadataFormatNames;
        this.extraImageMetadataFormatClassNames = extraImageMetadataFormatClassNames;
    }

    public String[] formatNames() {
        return formatNames;
    }

    public String[] suffixes() {
        return suffixes;
    }

    public String[] mimeTypes() {
        return mimeTypes;
    }

    public String readerClassName() {
        return readerClassName;
    }

    public String[] readerSpiClassNames() {
        return readerSpiClassNames;
    }

    public Class[] inputTypes() {
        return inputTypes;
    }

    public String writerClassName() {
        return writerClassName;
    }

    public String[] writerSpiClassNames() {
        return writerSpiClassNames;
    }

    public Class[] outputTypes() {
        return outputTypes;
    }

    public boolean supportsStandardStreamMetadataFormat() {
        return supportsStandardStreamMetadata;
    }

    public String nativeStreamMetadataFormatName() {
        return nativeStreamMetadataFormatName;
    }

    public String nativeStreamMetadataFormatClassName() {
        return nativeStreamMetadataFormatClassName;
    }

    public String[] extraStreamMetadataFormatNames() {
        return extraStreamMetadataFormatNames;
    }

    public String[] extraStreamMetadataFormatClassNames() {
        return extraStreamMetadataFormatClassNames;
    }

    public boolean supportsStandardImageMetadataFormat() {
        return supportsStandardImageMetadata;
    }

    public String nativeImageMetadataFormatName() {
        return nativeImageMetadataFormatName;
    }

    public String nativeImageMetadataFormatClassName() {
        return nativeImageMetadataFormatClassName;
    }

    public String[] extraImageMetadataFormatNames() {
        return extraImageMetadataFormatNames;
    }

    public String[] extraImageMetadataFormatClassNames() {
        return extraImageMetadataFormatClassNames;
    }
}
