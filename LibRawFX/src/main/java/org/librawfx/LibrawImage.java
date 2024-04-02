/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.foreign.*;

/**
 * Loads the native libraw lib and process the image in the native space. This
 * is the entry point of the whole libraw lib to be used
 *
 * @author Clemens Lanthaler
 */
public class LibrawImage {

    private final String imageFileURL;
    private short imageWidth;
    private short imageHeight;
    private short imageBits;
    private short imageColors;
    private int stride;
    private static String[] loadLibraryFromJar;
    private static String operatingSystem;
    private RAWImageLoader loader;
    private String cameraModel;
    private LocalDateTime shootingDateTime;
    private RawDecoderSettings rawSettings;
    private static SymbolLookup loaderLookup;

    public LibrawImage(String imageFile, RawDecoderSettings settings) {
        this.imageFileURL = imageFile;
        this.rawSettings = settings;
    }

    public LibrawImage(RAWImageLoader loader, RawDecoderSettings settings) {
        imageFileURL = null;
        this.loader = loader;
        this.rawSettings = settings;
    }

    /**
     * Unpacks all native libs and places them in a temp directory and include
     * them in the directory
     *
     * @param tempDir directory where the native libs are placed
     * @throws IOException if it is not possible to load/unpack the native libs
     * into a temp directory
     */
    public static void loadLibs(String tempDir) throws IOException {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native libs...");
        operatingSystem = System.getProperty("os.name").toUpperCase();
        String arch = System.getProperty("os.arch").toUpperCase();
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "OS was: " + operatingSystem);
        if (operatingSystem.contains("WIN")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar(tempDir, "/lib/win-x86_64/libraw.dll", "/lib/win-x86_64/libjpeg.dll", "/lib/win-x86_64/zlib.dll");
        } else if (operatingSystem.contains("MAC") && !arch.contains("AARCH64")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar(tempDir, "/lib/osx-x86_64/liblcms2.2.dylib", "/lib/osx-x86_64/libjasper.4.dylib", "/lib/osx-x86_64/libjpeg.9.dylib", "/lib/osx-x86_64/libz.1.dylib", "/lib/osx-x86_64/libraw.22.dylib");
        } else if (operatingSystem.contains("MAC") && arch.contains("AARCH64")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar(tempDir, "/lib/osx-arm64/liblcms2.2.dylib", "/lib/osx-arm64/libjasper.7.dylib", "/lib/osx-arm64/libjpeg.8.dylib", "/lib/osx-arm64/libraw_r.23.dylib");
        } else if (operatingSystem.contains("NUX")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar(tempDir, "/lib/linux-x86_64/libstdc++.so.6", "/lib/linux-x86_64/libm.so.6", "/lib/linux-x86_64/liblcms2.so.2", "/lib/linux-x86_64/libjpeg.so.8", "/lib/linux-x86_64/libraw.so.23");
        }

        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "loadLibraryFromJar: " + Arrays.toString(loadLibraryFromJar) + " , tempdir: " + tempDir);
        for (String part : loadLibraryFromJar) {
            //System.out.println("libsarray "+part);
            new File(part).deleteOnExit();
        }
        for (String strTemp : loadLibraryFromJar) {
            System.load(strTemp);
            loaderLookup = SymbolLookup.loaderLookup();
        }
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native libs...finished");
    }

    /**
     * Loading the native image from the given stream and give it as byte array
     * back
     *
     * @param sourceFileAsByteArray the stream as array of bytes (raw bytes)
     * @return an array of rgb bytes of the image which can be feed to the image
     * class
     * @throws IOException if the given byte array cannot be read
     */
    public byte[] readPixelDataFromStream(byte[] sourceFileAsByteArray) throws IOException {
        if (sourceFileAsByteArray == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.SEVERE, null, "sourceFileAsByteArray == null!");
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        if (loadLibraryFromJar == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.SEVERE, null, "Please call loadLibs as static method first!");
            throw new IllegalArgumentException("Please call loadLibs as static method first!");
        }
        try ( var scope = Arena.ofShared()) {
            if (operatingSystem.contains("WIN")) {
                MemorySegment iprc = org.libraw.win.libraw_h.libraw_init(0);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Memory dddress native lib was: " + iprc.get(ValueLayout.JAVA_LONG, 0));
                //MemorySegment datasegment = org.libraw.win.libraw_data_t.idata(iprc);
                MemorySegment params$slice = org.libraw.win.libraw_data_t.params(iprc);

                if (this.rawSettings == null) {
                    this.rawSettings = new RawDecoderSettings();
                }
                new RawDecoderToNativeTranslator(rawSettings, operatingSystem).translate(params$slice);
                /*org.libraw.win.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.output_tiff$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.half_size$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.user_qual$set(params$slice, 0);*/

                MemorySegment inputStreamBytes = MemorySegment.ofArray(sourceFileAsByteArray);                
                MemorySegment allocateNative = scope.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray);//)Array(org.libraw.win.libraw_h.C_CHAR, sourceFileAsByteArray);
                int k = org.libraw.win.libraw_h.libraw_open_buffer(iprc, allocateNative, inputStreamBytes.byteSize());
                if (k > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.SEVERE, null, "Cannot open stream, return value was: " + k);
                    throw new IOException("Cannot open file stream!");
                }

                org.libraw.win.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.win.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.win.libraw_iparams_t.allocate(iParams);//.ofAddress(iParams, scope);
                MemorySegment modelSlice = org.libraw.win.libraw_iparams_t.model(iParams);
                cameraModel = new String(modelSlice.toArray(ValueLayout.JAVA_BYTE), StandardCharsets.US_ASCII);
                MemorySegment image_other_data = org.libraw.win.libraw_h.libraw_get_imgother(iprc);
                //MemorySegment imageOtherRestricted = org.libraw.win.libraw_imgother_t.ofAddress(image_other_data, scope);
                long timestamp = org.libraw.win.libraw_imgother_t.timestamp(image_other_data);
                shootingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());

                org.libraw.win.libraw_h.libraw_dcraw_process(iprc);
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                
                MemorySegment errorCode = scope.allocate(org.libraw.win.libraw_h.C_INT.byteSize());
                MemorySegment mem_image_adr = org.libraw.win.libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode);
                //MemorySegment imageMemSegment = org.libraw.win.libraw_processed_image_t..ofAddress(mem_image_adr, scope);
                MemorySegment data$slice = org.libraw.win.libraw_processed_image_t.data(mem_image_adr);
                imageWidth = org.libraw.win.libraw_processed_image_t.width(mem_image_adr);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native width: " + imageWidth);
                imageHeight = org.libraw.win.libraw_processed_image_t.height(mem_image_adr);
                imageBits = org.libraw.win.libraw_processed_image_t.bits(mem_image_adr);
                imageColors = org.libraw.win.libraw_processed_image_t.colors(mem_image_adr);

                stride = imageWidth * imageColors * (imageBits / 8);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Start reading image from native memory...");
                byte[] line = new byte[stride];
                for (var i = 0; i < imageHeight; i++) {
                    loader.updateImageProgress(i, imageHeight);
                    long offSetAdr=data$slice.address()+stride * i;
                    MemorySegment asSegmentRestricted = MemorySegment.ofAddress(offSetAdr);
                    line = asSegmentRestricted.reinterpret(stride).toArray(ValueLayout.JAVA_BYTE);
                    try {
                        bo.write(line);
                    } catch (IOException ex) {
                        Logger.getLogger(LibrawImage.class.getName()).log(Level.SEVERE, "Cannot retrieve image from native memory", ex);
                        return null;
                    }
                }
                bo.flush();
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Start reading image from native memory...finished");
                byte[] data = bo.toByteArray();
                bo.close();
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Freeing of native memory...");
                org.libraw.win.libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
                mem_image_adr = null;
                org.libraw.win.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Freeing of native memory...finished");
                return data;
            } else {
                MemorySegment iprc = org.libraw.win.libraw_h.libraw_init(0);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Memory dddress native lib was: " + iprc.get(ValueLayout.JAVA_LONG, 0));
                //MemorySegment datasegment = org.libraw.win.libraw_data_t.idata(iprc);
                MemorySegment params$slice = org.libraw.win.libraw_data_t.params(iprc);

                if (this.rawSettings == null) {
                    this.rawSettings = new RawDecoderSettings();
                }
                new RawDecoderToNativeTranslator(rawSettings, operatingSystem).translate(params$slice);
                /*org.libraw.win.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.output_tiff$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.half_size$set(params$slice, 0);
                org.libraw.win.libraw_output_params_t.user_qual$set(params$slice, 0);*/

                MemorySegment inputStreamBytes = MemorySegment.ofArray(sourceFileAsByteArray);                
                MemorySegment allocateNative = scope.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray);//)Array(org.libraw.win.libraw_h.C_CHAR, sourceFileAsByteArray);
                int k = org.libraw.win.libraw_h.libraw_open_buffer(iprc, allocateNative, inputStreamBytes.byteSize());
                if (k > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.SEVERE, null, "Cannot open stream, return value was: " + k);
                    throw new IOException("Cannot open file stream!");
                }

                org.libraw.win.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.win.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.win.libraw_iparams_t.allocate(iParams);//.ofAddress(iParams, scope);
                MemorySegment modelSlice = org.libraw.win.libraw_iparams_t.model(iParams);
                cameraModel = new String(modelSlice.toArray(ValueLayout.JAVA_BYTE), StandardCharsets.US_ASCII);
                MemorySegment image_other_data = org.libraw.win.libraw_h.libraw_get_imgother(iprc);
                //MemorySegment imageOtherRestricted = org.libraw.win.libraw_imgother_t.ofAddress(image_other_data, scope);
                long timestamp = org.libraw.win.libraw_imgother_t.timestamp(image_other_data);
                shootingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());

                org.libraw.win.libraw_h.libraw_dcraw_process(iprc);
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                
                MemorySegment errorCode = scope.allocate(org.libraw.win.libraw_h.C_INT.byteSize());
                MemorySegment mem_image_adr = org.libraw.win.libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode);
                //MemorySegment imageMemSegment = org.libraw.win.libraw_processed_image_t..ofAddress(mem_image_adr, scope);
                MemorySegment data$slice = org.libraw.win.libraw_processed_image_t.data(mem_image_adr);
                imageWidth = org.libraw.win.libraw_processed_image_t.width(mem_image_adr);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native width: " + imageWidth);
                imageHeight = org.libraw.win.libraw_processed_image_t.height(mem_image_adr);
                imageBits = org.libraw.win.libraw_processed_image_t.bits(mem_image_adr);
                imageColors = org.libraw.win.libraw_processed_image_t.colors(mem_image_adr);

                stride = imageWidth * imageColors * (imageBits / 8);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Start reading image from native memory...");
                byte[] line = new byte[stride];
                for (var i = 0; i < imageHeight; i++) {
                    loader.updateImageProgress(i, imageHeight);
                    long offSetAdr=data$slice.address()+stride * i;
                    MemorySegment asSegmentRestricted = MemorySegment.ofAddress(offSetAdr);
                    line = asSegmentRestricted.reinterpret(stride).toArray(ValueLayout.JAVA_BYTE);
                    try {
                        bo.write(line);
                    } catch (IOException ex) {
                        Logger.getLogger(LibrawImage.class.getName()).log(Level.SEVERE, "Cannot retrieve image from native memory", ex);
                        return null;
                    }
                }
                bo.flush();
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Start reading image from native memory...finished");
                byte[] data = bo.toByteArray();
                bo.close();
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Freeing of native memory...");
                org.libraw.win.libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
                mem_image_adr = null;
                org.libraw.win.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Freeing of native memory...finished");
                return data;
            }
        }
    }

    /**
     * Reads from give URL as string the file. Please use before constructor
     * LibrawImage(String imageFile)
     *
     * @return int array of RGB values which can be further processed
     * @throws IOException if the given file cannot be read
     */
    public synchronized int[] readPixelData() throws IOException {
        if (imageFileURL == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "imageFileURL == null! " + imageFileURL);
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        if (loadLibraryFromJar == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Please call loadLibs as static method first!");
            throw new IllegalArgumentException("Please call loadLibs as static method first!");
        }

        try ( var scope = Arena.ofShared()) {
            if (operatingSystem.contains("WIN")) {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
                MemorySegment iprc = org.libraw.win.libraw_h.libraw_init(0);
                
                //MemorySegment datasegment = org.libraw.win.libraw_data_t.ofAddress(iprc, scope);
                MemorySegment params$slice = org.libraw.win.libraw_data_t.params(iprc);

                if (this.rawSettings == null) {
                    this.rawSettings = new RawDecoderSettings();
                }
                new RawDecoderToNativeTranslator(rawSettings, operatingSystem).translate(params$slice);

                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
                //int libraw_open_file = org.libraw.win.libraw_h.libraw_open_file(iprc, SegmentAllocator.nativeAllocator(scope).allocateUtf8String(imageFileURL));
                int libraw_open_file = org.libraw.win.libraw_h.libraw_open_file(iprc,scope.allocateFrom(imageFileURL));
                if (libraw_open_file > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                    throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
                }

                org.libraw.win.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.win.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.win.libraw_iparams_t.ofAddress(iParams, scope);
                MemorySegment modelSlice = org.libraw.win.libraw_iparams_t.model(iParams);
                cameraModel = new String(modelSlice.toArray(ValueLayout.JAVA_BYTE), StandardCharsets.UTF_8);
                MemorySegment image_other_data = org.libraw.win.libraw_h.libraw_get_imgother(iprc);
                //MemorySegment imageOtherRestricted = org.libraw.win.libraw_imgother_t.ofAddress(image_other_data, scope);
                long timestamp = org.libraw.win.libraw_imgother_t.timestamp(image_other_data);
                shootingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());

                org.libraw.win.libraw_h.libraw_dcraw_process(iprc);

                ByteArrayOutputStream bo = new ByteArrayOutputStream();

                //MemorySegment errorCode = SegmentAllocator.nativeAllocator(scope).allocate(org.libraw.win.libraw_h.C_INT.byteSize());
                MemorySegment errorCode = scope.allocate(org.libraw.win.libraw_h.C_INT.byteSize());
                MemorySegment mem_image_adr = org.libraw.win.libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode);
                //MemorySegment imageMemSegment = org.libraw.win.libraw_processed_image_t.ofAddress(mem_image_adr, scope);
                MemorySegment data$slice = org.libraw.win.libraw_processed_image_t.data(mem_image_adr);
                imageWidth = org.libraw.win.libraw_processed_image_t.width(mem_image_adr);
                imageHeight = org.libraw.win.libraw_processed_image_t.height(mem_image_adr);
                imageBits = org.libraw.win.libraw_processed_image_t.bits(mem_image_adr);
                imageColors = org.libraw.win.libraw_processed_image_t.colors(mem_image_adr);
                //var num = imageWidth % 4;
                stride = imageWidth * imageColors * (imageBits / 8);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Reading image from native memory...");
                byte[] line = new byte[stride];
                for (var i = 0; i < imageHeight; i++) {
                    long offSetAdr=data$slice.address()+stride * i;
                    //MemorySegment asSegmentRestricted = MemorySegment.ofAddress(offSetAdr, stride, scope);                                        
                    MemorySegment asSegmentRestricted = MemorySegment.ofAddress(offSetAdr);
                    line = asSegmentRestricted.reinterpret(stride).toArray(ValueLayout.JAVA_BYTE);
                    try {
                        bo.write(line);
                    } catch (IOException ex) {
                        return null;
                    }
                }
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Reading image from native memory...finished");
                byte[] rawBytes = bo.toByteArray();
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Convert rawtoINTBytes");
                int[] raw = convertToINT(rawBytes);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...");
                org.libraw.win.libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
                mem_image_adr = null;
                org.libraw.win.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
                return raw;
            } else {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
                MemorySegment iprc = org.libraw.linuxosx.libraw_h.libraw_init(0);
                //MemorySegment datasegment = org.libraw.linuxosx.libraw_data_t.ofAddress(iprc, scope);
                MemorySegment params$slice = org.libraw.linuxosx.libraw_data_t.params(iprc);

                if (this.rawSettings == null) {
                    this.rawSettings = new RawDecoderSettings();
                }
                new RawDecoderToNativeTranslator(rawSettings, operatingSystem).translate(params$slice);

                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
                //int libraw_open_file = org.libraw.linuxosx.libraw_h.libraw_open_file(iprc, SegmentAllocator.nativeAllocator(scope).allocateUtf8String(imageFileURL).asReadOnly());                
                int libraw_open_file = org.libraw.linuxosx.libraw_h.libraw_open_file(iprc, scope.allocateFrom(imageFileURL));
                if (libraw_open_file > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                    throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
                }

                org.libraw.linuxosx.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.linuxosx.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.linuxosx.libraw_iparams_t.ofAddress(iParams, scope);
                MemorySegment modelSlice = org.libraw.linuxosx.libraw_iparams_t.model(iParams);
                cameraModel = new String(modelSlice.toArray(ValueLayout.JAVA_BYTE), StandardCharsets.UTF_8);
                MemorySegment image_other_data = org.libraw.linuxosx.libraw_h.libraw_get_imgother(iprc);
                //MemorySegment imageOtherRestricted = org.libraw.linuxosx.libraw_imgother_t.ofAddress(image_other_data, scope);
                long timestamp = org.libraw.linuxosx.libraw_imgother_t.timestamp(image_other_data);
                shootingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());

                org.libraw.linuxosx.libraw_h.libraw_dcraw_process(iprc);

                ByteArrayOutputStream bo = new ByteArrayOutputStream();

                //MemorySegment errorCode = SegmentAllocator.nativeAllocator(scope).allocate(org.libraw.linuxosx.libraw_h.C_INT.byteSize());
                MemorySegment errorCode = scope.allocate(org.libraw.linuxosx.libraw_h.C_INT.byteSize());
                MemorySegment mem_image_adr = org.libraw.linuxosx.libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode);
                //MemorySegment imageMemSegment = org.libraw.linuxosx.libraw_processed_image_t.ofAddress(mem_image_adr, scope);
                MemorySegment data$slice = org.libraw.linuxosx.libraw_processed_image_t.data(mem_image_adr);
                imageWidth = org.libraw.linuxosx.libraw_processed_image_t.width(mem_image_adr);
                imageHeight = org.libraw.linuxosx.libraw_processed_image_t.height(mem_image_adr);
                imageBits = org.libraw.linuxosx.libraw_processed_image_t.bits(mem_image_adr);
                imageColors = org.libraw.linuxosx.libraw_processed_image_t.colors(mem_image_adr);
                //var num = imageWidth % 4;
                stride = imageWidth * imageColors * (imageBits / 8);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Reading image from native memory...");
                byte[] line = new byte[stride];
                for (var i = 0; i < imageHeight; i++) {
                    long offSetAdr=data$slice.address()+stride * i;
                    //MemorySegment asSegmentRestricted = MemorySegment.ofAddress(offSetAdr, stride, scope); 
                    MemorySegment asSegmentRestricted = MemorySegment.ofAddress(offSetAdr);
                    line = asSegmentRestricted.reinterpret(stride).toArray(ValueLayout.JAVA_BYTE);
                    try {
                        bo.write(line);
                    } catch (IOException ex) {
                        return null;
                    }
                }
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Reading image from native memory...finished");
                byte[] rawBytes = bo.toByteArray();
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Convert rawtoINTBytes");
                int[] raw = convertToINT(rawBytes);
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...");
                org.libraw.linuxosx.libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
                mem_image_adr = null;
                org.libraw.linuxosx.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
                return raw;
            }
        }
    }

    /**
     * Convert RGB byte array to the corresponding INT values for javafx
     *
     * @param rawBytes byte array of RGB bytes
     * @return an int array of the RGB bytes
     */
    private synchronized int[] convertToINT(byte[] rawBytes) {
        int[] raw = new int[rawBytes.length * 4 / 3];
        for (int j = 0; j < rawBytes.length / 3; j++) {
            raw[j] = 0xFF000000
                    | ((rawBytes[3 * j + 0] & 0xFF) << 16)
                    | ((rawBytes[3 * j + 1] & 0xFF) << 8)
                    | ((rawBytes[3 * j + 2] & 0xFF));
        }
        return raw;
    }

    /**
     * Retrievs for the given file in the constructor the metadata Important:
     * The timestamps are in EpocheInSeconds and not milliseconds
     *
     * @return Hashmap of all meta data
     * @throws IOException
     */
    public HashMap<String, String> getMetaData() throws IOException {
        if (imageFileURL == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "imageFileURL == null! " + imageFileURL);
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        if (loadLibraryFromJar == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Please call loadLibs as static method first!");
            throw new IllegalArgumentException("Please call loadLibs as static method first!");
        }
        HashMap<String, String> retMap = new HashMap<>();
        try ( var scope = Arena.ofShared()) {
            if (operatingSystem.contains("WIN")) {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
                MemorySegment iprc = org.libraw.win.libraw_h.libraw_init(0);
                //MemorySegment datasegment = org.libraw.win.libraw_data_t.ofAddress(iprc, scope);

                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
                //int libraw_open_file = org.libraw.win.libraw_h.libraw_open_file(iprc, SegmentAllocator.nativeAllocator(scope).allocateUtf8String(imageFileURL));
                int libraw_open_file = org.libraw.win.libraw_h.libraw_open_file(iprc, scope.allocateFrom(imageFileURL));
                if (libraw_open_file > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                    throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
                }

                org.libraw.win.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.win.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.win.libraw_iparams_t.ofAddress(iParams, scope);
                MemorySegment make$slice = org.libraw.win.libraw_iparams_t.make(iParams);
                retMap.put("CameraMaker", make$slice.getString(0));
                MemorySegment model$slice = org.libraw.win.libraw_iparams_t.model(iParams);
                retMap.put("CameraModel", model$slice.getString(0));
                MemorySegment xmpdata$get = org.libraw.win.libraw_iparams_t.xmpdata(iParams);
                int xmplen$get = org.libraw.win.libraw_iparams_t.xmplen(iParams);
                if (xmplen$get != 0) {
                    //MemorySegment asSegmentRestricted = MemorySegment.allocateNative(xmplen$get, scope);
                    MemorySegment asSegmentRestricted = MemorySegment.ofAddress(xmplen$get);
                    retMap.put("XMP", asSegmentRestricted.getString(0));
                }

                MemorySegment image_other_data = org.libraw.win.libraw_h.libraw_get_imgother(iprc);
                //MemorySegment imageOtherRestricted = org.libraw.win.libraw_imgother_t.ofAddress(image_other_data, scope);

                MemorySegment gpsInfoSegement = org.libraw.win.libraw_imgother_t.parsed_gps(image_other_data);
                float altitude$get = org.libraw.win.libraw_gps_info_t.altitude(gpsInfoSegement);
                retMap.put("GPS Altitude", "" + altitude$get);
                byte latref$get = org.libraw.win.libraw_gps_info_t.latref(gpsInfoSegement);
                byte longref = org.libraw.win.libraw_gps_info_t.longref(gpsInfoSegement);
                retMap.put("GPS Position", String.valueOf(latref$get) + ";" + String.valueOf(longref));
                MemorySegment gpstime = org.libraw.win.libraw_gps_info_t.gpstimestamp(gpsInfoSegement);
                retMap.put("GPS Time", gpstime.getString(0));

                float aperture = org.libraw.win.libraw_imgother_t.aperture(image_other_data);
                float focal_len = org.libraw.win.libraw_imgother_t.focal_len(image_other_data);
                float isoSpeed = org.libraw.win.libraw_imgother_t.iso_speed(image_other_data);
                float shutter = org.libraw.win.libraw_imgother_t.shutter(image_other_data);
                long timestamp = org.libraw.win.libraw_imgother_t.timestamp(image_other_data);
                retMap.put("CurFocal", "" + focal_len);
                retMap.put("ISO speed", "" + isoSpeed);
                retMap.put("shutter", "" + shutter);
                retMap.put("Timestamp (EpocheSec)", "" + timestamp);

                MemorySegment lens$slice = org.libraw.win.libraw_data_t.lens(iprc);
                float EXIF_MaxAp$get = org.libraw.win.libraw_lensinfo_t.EXIF_MaxAp(lens$slice);
                retMap.put("MaxAperture @CurFocal", "f/" + EXIF_MaxAp$get);
                short FocalLengthIn35mmFormat$get = org.libraw.win.libraw_lensinfo_t.FocalLengthIn35mmFormat(lens$slice);
                retMap.put("FocalLengthIn35mmFormat", "" + FocalLengthIn35mmFormat$get + " mm");
                MemorySegment InternalLensSerial$slice = org.libraw.win.libraw_lensinfo_t.InternalLensSerial(lens$slice);
                retMap.put("InternalLensSerial$slice", InternalLensSerial$slice.getString(0));
                MemorySegment Lens$slice = org.libraw.win.libraw_lensinfo_t.Lens(lens$slice);
                retMap.put("Lens", Lens$slice.getString(0));
                MemorySegment LensMake$slice = org.libraw.win.libraw_lensinfo_t.LensMake(lens$slice);
                retMap.put("LensMake", LensMake$slice.getString(0));
                MemorySegment LensSerial$slice = org.libraw.win.libraw_lensinfo_t.LensSerial(lens$slice);
                retMap.put("LensSerial$slice", LensSerial$slice.getString(0));
                float MaxAp4MaxFocal$get = org.libraw.win.libraw_lensinfo_t.MaxAp4MaxFocal(lens$slice);
                retMap.put("MaxAp @MaxFocal", "f/" + MaxAp4MaxFocal$get);
                float MaxAp4MinFocal$get = org.libraw.win.libraw_lensinfo_t.MaxAp4MinFocal(lens$slice);
                retMap.put("MaxAp @MinFocal", "f/" + MaxAp4MinFocal$get);
                float MaxFocal$get = org.libraw.win.libraw_lensinfo_t.MaxFocal(lens$slice);
                retMap.put("MaxFocal", "" + MaxFocal$get + " mm");
                float MinFocal$get = org.libraw.win.libraw_lensinfo_t.MinFocal(lens$slice);
                retMap.put("MinFocal", "" + MinFocal$get + " mm");
                MemorySegment makernotes$slice = org.libraw.win.libraw_lensinfo_t.makernotes(lens$slice);
                float CurFocal$get = org.libraw.win.libraw_makernotes_lens_t.CurFocal(makernotes$slice);
                retMap.put("CurFocal", "" + CurFocal$get);
                float CurAp$get = org.libraw.win.libraw_makernotes_lens_t.CurAp(makernotes$slice);
                retMap.put("CurAperture", "" + CurAp$get);

                MemorySegment shootingInfo$slice = org.libraw.win.libraw_data_t.shootinginfo(iprc);
                short afPoint = org.libraw.win.libraw_shootinginfo_t.AFPoint(shootingInfo$slice);
                retMap.put("AFPoint", "" + afPoint);
                short driveMode = org.libraw.win.libraw_shootinginfo_t.DriveMode(shootingInfo$slice);
                retMap.put("DriveMode", "" + driveMode);
                short exposureMode = org.libraw.win.libraw_shootinginfo_t.ExposureMode(shootingInfo$slice);
                retMap.put("ExposureMode", "" + exposureMode);
                short exposureProgram = org.libraw.win.libraw_shootinginfo_t.ExposureProgram(shootingInfo$slice);
                retMap.put("ExposureProgram", "" + exposureProgram);
                short focusMode = org.libraw.win.libraw_shootinginfo_t.FocusMode(shootingInfo$slice);
                retMap.put("FocusMode", "" + focusMode);
                short imageStabiMode = org.libraw.win.libraw_shootinginfo_t.ImageStabilization(shootingInfo$slice);
                retMap.put("ImageStabiMode", "" + imageStabiMode);
                short medteringMode = org.libraw.win.libraw_shootinginfo_t.MeteringMode(shootingInfo$slice);
                retMap.put("MedteringMode", "" + medteringMode);

                imageHeight = (short) org.libraw.win.libraw_h.libraw_get_iheight(iprc);
                imageWidth = (short) org.libraw.win.libraw_h.libraw_get_iwidth(iprc);
                imageColors = (short) org.libraw.win.libraw_h.libraw_get_color_maximum(iprc);

                org.libraw.win.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
                retMap.put("ImageWidth", "" + imageWidth);
                retMap.put("ImageHeight", "" + imageHeight);
                retMap.put("ImageColors", "" + imageColors);
                retMap.put("Aperture", "f/" + aperture);
                retMap.put("Focal length", "" + focal_len + " mm");
                retMap.put("ISO speed", "" + isoSpeed);
                retMap.put("Shutter", "" + (1 / shutter));

                return retMap;
            } else {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
                MemorySegment iprc = org.libraw.linuxosx.libraw_h.libraw_init(0);
                //MemorySegment datasegment = org.libraw.linuxosx.libraw_data_t.ofAddress(iprc, scope);

                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
                //int libraw_open_file = org.libraw.linuxosx.libraw_h.libraw_open_file(iprc, SegmentAllocator.nativeAllocator(scope).allocateUtf8String(imageFileURL));
                int libraw_open_file = org.libraw.linuxosx.libraw_h.libraw_open_file(iprc,scope.allocateFrom(imageFileURL));
                if (libraw_open_file > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                    throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
                }

                org.libraw.linuxosx.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.linuxosx.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.linuxosx.libraw_iparams_t.ofAddress(iParams, scope);
                MemorySegment make$slice = org.libraw.linuxosx.libraw_iparams_t.make(iParams);
                retMap.put("CameraMaker", make$slice.getString(0));
                MemorySegment model$slice = org.libraw.linuxosx.libraw_iparams_t.model(iParams);
                retMap.put("CameraModel", model$slice.getString(0));
                MemorySegment xmpdata$get = org.libraw.linuxosx.libraw_iparams_t.xmpdata(iParams);
                int xmplen$get = org.libraw.linuxosx.libraw_iparams_t.xmplen(iParams);
                if (xmplen$get != 0) {
                    //MemorySegment asSegmentRestricted = MemorySegment.allocateNative(xmplen$get, scope);
                    MemorySegment asSegmentRestricted = MemorySegment.ofAddress(xmplen$get);
                    retMap.put("XMP", asSegmentRestricted.getString(0));
                }

                MemorySegment image_other_data = org.libraw.linuxosx.libraw_h.libraw_get_imgother(iprc);
                //MemorySegment imageOtherRestricted = org.libraw.linuxosx.libraw_imgother_t.ofAddress(image_other_data, scope);

                MemorySegment gpsInfoSegement = org.libraw.linuxosx.libraw_imgother_t.parsed_gps(image_other_data);
                float altitude$get = org.libraw.linuxosx.libraw_gps_info_t.altitude(gpsInfoSegement);
                retMap.put("GPS Altitude", "" + altitude$get);
                byte latref$get = org.libraw.linuxosx.libraw_gps_info_t.latref(gpsInfoSegement);
                byte longref = org.libraw.linuxosx.libraw_gps_info_t.longref(gpsInfoSegement);
                retMap.put("GPS Position", String.valueOf(latref$get) + ";" + String.valueOf(longref));
                MemorySegment gpstime = org.libraw.linuxosx.libraw_gps_info_t.gpstimestamp(gpsInfoSegement);
                retMap.put("GPS Time", gpstime.getString(0));

                float aperture = org.libraw.linuxosx.libraw_imgother_t.aperture(image_other_data);
                float focal_len = org.libraw.linuxosx.libraw_imgother_t.focal_len(image_other_data);
                float isoSpeed = org.libraw.linuxosx.libraw_imgother_t.iso_speed(image_other_data);
                float shutter = org.libraw.linuxosx.libraw_imgother_t.shutter(image_other_data);
                long timestamp = org.libraw.linuxosx.libraw_imgother_t.timestamp(image_other_data);
                retMap.put("CurFocal", "" + focal_len);
                retMap.put("ISO speed", "" + isoSpeed);
                retMap.put("shutter", "" + shutter);
                retMap.put("Timestamp (EpocheSec)", "" + timestamp);

                MemorySegment lens$slice = org.libraw.linuxosx.libraw_data_t.lens(iprc);
                float EXIF_MaxAp$get = org.libraw.linuxosx.libraw_lensinfo_t.EXIF_MaxAp(lens$slice);
                retMap.put("MaxAperture @CurFocal", "f/" + EXIF_MaxAp$get);
                short FocalLengthIn35mmFormat$get = org.libraw.linuxosx.libraw_lensinfo_t.FocalLengthIn35mmFormat(lens$slice);
                retMap.put("FocalLengthIn35mmFormat", "" + FocalLengthIn35mmFormat$get + " mm");
                MemorySegment InternalLensSerial$slice = org.libraw.linuxosx.libraw_lensinfo_t.InternalLensSerial(lens$slice);
                retMap.put("InternalLensSerial$slice", InternalLensSerial$slice.toString());
                MemorySegment Lens$slice = org.libraw.linuxosx.libraw_lensinfo_t.Lens(lens$slice);
                retMap.put("Lens", Lens$slice.toString());
                MemorySegment LensMake$slice = org.libraw.linuxosx.libraw_lensinfo_t.LensMake(lens$slice);
                retMap.put("LensMake", LensMake$slice.getString(0));
                MemorySegment LensSerial$slice = org.libraw.linuxosx.libraw_lensinfo_t.LensSerial(lens$slice);
                retMap.put("LensSerial$slice", LensSerial$slice.getString(0));
                float MaxAp4MaxFocal$get = org.libraw.linuxosx.libraw_lensinfo_t.MaxAp4MaxFocal(lens$slice);
                retMap.put("MaxAp @MaxFocal", "f/" + MaxAp4MaxFocal$get);
                float MaxAp4MinFocal$get = org.libraw.linuxosx.libraw_lensinfo_t.MaxAp4MinFocal(lens$slice);
                retMap.put("MaxAp @MinFocal", "f/" + MaxAp4MinFocal$get);
                float MaxFocal$get = org.libraw.linuxosx.libraw_lensinfo_t.MaxFocal(lens$slice);
                retMap.put("MaxFocal", "" + MaxFocal$get + " mm");
                float MinFocal$get = org.libraw.linuxosx.libraw_lensinfo_t.MinFocal(lens$slice);
                retMap.put("MinFocal", "" + MinFocal$get + " mm");
                MemorySegment makernotes$slice = org.libraw.linuxosx.libraw_lensinfo_t.makernotes(lens$slice);
                float CurFocal$get = org.libraw.linuxosx.libraw_makernotes_lens_t.CurFocal(makernotes$slice);
                retMap.put("CurFocal", "" + CurFocal$get);
                float CurAp$get = org.libraw.linuxosx.libraw_makernotes_lens_t.CurAp(makernotes$slice);
                retMap.put("CurAperture", "" + CurAp$get);

                MemorySegment shootingInfo$slice = org.libraw.linuxosx.libraw_data_t.shootinginfo(iprc);
                short afPoint = org.libraw.linuxosx.libraw_shootinginfo_t.AFPoint(shootingInfo$slice);
                retMap.put("AFPoint", "" + afPoint);
                short driveMode = org.libraw.linuxosx.libraw_shootinginfo_t.DriveMode(shootingInfo$slice);
                retMap.put("DriveMode", "" + driveMode);
                short exposureMode = org.libraw.linuxosx.libraw_shootinginfo_t.ExposureMode(shootingInfo$slice);
                retMap.put("ExposureMode", "" + exposureMode);
                short exposureProgram = org.libraw.linuxosx.libraw_shootinginfo_t.ExposureProgram(shootingInfo$slice);
                retMap.put("ExposureProgram", "" + exposureProgram);
                short focusMode = org.libraw.linuxosx.libraw_shootinginfo_t.FocusMode(shootingInfo$slice);
                retMap.put("FocusMode", "" + focusMode);
                short imageStabiMode = org.libraw.linuxosx.libraw_shootinginfo_t.ImageStabilization(shootingInfo$slice);
                retMap.put("ImageStabiMode", "" + imageStabiMode);
                short medteringMode = org.libraw.linuxosx.libraw_shootinginfo_t.MeteringMode(shootingInfo$slice);
                retMap.put("MedteringMode", "" + medteringMode);

                imageHeight = (short) org.libraw.linuxosx.libraw_h.libraw_get_iheight(iprc);
                imageWidth = (short) org.libraw.linuxosx.libraw_h.libraw_get_iwidth(iprc);
                imageColors = (short) org.libraw.linuxosx.libraw_h.libraw_get_color_maximum(iprc);

                org.libraw.linuxosx.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
                retMap.put("ImageWidth", "" + imageWidth);
                retMap.put("ImageHeight", "" + imageHeight);
                retMap.put("ImageColors", "" + imageColors);
                retMap.put("Aperture", "f/" + aperture);
                retMap.put("Focal length", "" + focal_len + " mm");
                retMap.put("ISO speed", "" + isoSpeed);
                retMap.put("Shutter", "" + (1 / shutter));

                return retMap;
            }
        }
    }
    
    
    public String getCameraMaker() throws IOException {
        String retString;
        if (imageFileURL == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "imageFileURL == null! " + imageFileURL);
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        if (loadLibraryFromJar == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Please call loadLibs as static method first!");
            throw new IllegalArgumentException("Please call loadLibs as static method first!");
        }        
        try ( var scope = Arena.ofShared()) {
            if (operatingSystem.contains("WIN")) {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
                MemorySegment iprc = org.libraw.win.libraw_h.libraw_init(0);                

                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
                //int libraw_open_file = org.libraw.win.libraw_h.libraw_open_file(iprc, SegmentAllocator.nativeAllocator(scope).allocateUtf8String(imageFileURL));
                int libraw_open_file = org.libraw.win.libraw_h.libraw_open_file(iprc, scope.allocateFrom(imageFileURL));
                if (libraw_open_file > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                    throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
                }

                org.libraw.win.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.win.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.win.libraw_iparams_t.ofAddress(iParams, scope);
                MemorySegment make$slice = org.libraw.win.libraw_iparams_t.make(iParams);                
                retString=make$slice.getString(0);
                MemorySegment model$slice = org.libraw.win.libraw_iparams_t.model(iParams);                
                retString=retString+model$slice.getString(0);                

                org.libraw.win.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
                
                return retString;
            } else {                
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
                MemorySegment iprc = org.libraw.linuxosx.libraw_h.libraw_init(0);                

                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
                //int libraw_open_file = org.libraw.linuxosx.libraw_h.libraw_open_file(iprc, SegmentAllocator.nativeAllocator(scope).allocateUtf8String(imageFileURL));
                int libraw_open_file = org.libraw.linuxosx.libraw_h.libraw_open_file(iprc, scope.allocateFrom(imageFileURL));
                if (libraw_open_file > 0) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                    throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
                }

                org.libraw.linuxosx.libraw_h.libraw_unpack(iprc);

                MemorySegment iParams = org.libraw.linuxosx.libraw_h.libraw_get_iparams(iprc);
                //MemorySegment iParamsRestricted = org.libraw.linuxosx.libraw_iparams_t.ofAddress(iParams, scope);
                MemorySegment make$slice = org.libraw.linuxosx.libraw_iparams_t.make(iParams);                
                retString=make$slice.getString(0);
                MemorySegment model$slice = org.libraw.linuxosx.libraw_iparams_t.model(iParams);                
                retString=retString+model$slice.getString(0);                

                org.libraw.linuxosx.libraw_h.libraw_close(iprc);
                iprc = null;
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
                
                return retString;
            }
        }
    }

    /**
     * URL of the given file
     *
     * @return the URL to be read by readPixelData
     */
    public String getImageFileURL() {
        return imageFileURL;
    }

    /**
     * Gets the width of the native image
     *
     * @return width as short (int) of the native image
     */
    public short getImageWidth() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image width " + imageWidth);
        return imageWidth;
    }

    /**
     * Gets the height of the given image
     *
     * @return height as short (int) of the given image
     */
    public short getImageHeight() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image height " + imageHeight);
        return imageHeight;
    }

    /**
     * Image bit depth (e.g. 16bit, 8 bit, ...)
     *
     * @return the image bits
     */
    public short getImageBits() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image bits " + imageBits);
        return imageBits;
    }

    /**
     * Image colors
     *
     * @return if RGB or ARGB (3 or 4 color model)
     */
    public short getImageColors() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image colors " + imageColors);
        return imageColors;
    }

    /**
     * One line of the native image
     *
     * @return line of the image
     */
    public int getStride() {
        return stride;
    }

    /**
     * same as getStride but without the including the width of the image
     *
     * @return numBands
     */
    public int getNumBands() {
        return imageColors * (imageBits / 8);
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public LocalDateTime getShootingDateTime() {
        return shootingDateTime;
    }

    /**
     * getting the actual setting for the raw decoder engine
     * @return the settings object with all settings applied
     */
    public RawDecoderSettings getRawSettings() {
        return rawSettings;
    }

    /**
     * Optional settings for the raw decoding engine. If not set than basis engine properties are applied. 
     * This must be called before any loading of images
     * @param rawSettings the settings object to be set.
     */
    public void setRawSettings(RawDecoderSettings rawSettings) {
        this.rawSettings = rawSettings;
    }

    /**
     * String representation of the image
     *
     * @return debug string which values are stored in the class
     */
    @Override
    public String toString() {
        return "LibrawImage{" + "imageFileURL=" + imageFileURL + ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight + ", imageBits=" + imageBits + ", imageColors=" + imageColors + ", stride=" + stride + ", loader=" + loader + ", cameraModel=" + cameraModel + ", shootingDateTime=" + shootingDateTime + ", rawSettings=" + rawSettings + '}';
    }    
    

}
