/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.incubator.foreign.CLinker;
import static jdk.incubator.foreign.CLinker.C_CHAR;
import static jdk.incubator.foreign.CLinker.C_INT;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.NativeScope;
import org.libraw.RuntimeHelper;
import org.libraw.libraw_h;

/**
 * Loads the native libraw lib and process the image in the native space. 
 * This is the entry point of the whole libraw lib to be used
 * @author Clemens Lanthaler
 */
public class LibrawImage {

    private static LibraryLookup[] libraries;

    private final String imageFileURL;
    private short imageWidth;
    private short imageHeight;
    private short imageBits;
    private short imageColors;
    private int stride;
    private static String[] loadLibraryFromJar;
    private RAWImageLoader loader;    

    public LibrawImage(String imageFile) {
        this.imageFileURL = imageFile;
    }

    LibrawImage(RAWImageLoader loader) {
        imageFileURL = null;
        this.loader = loader;
    }

    /**
     * Unpacks all native libs and places them in a temp directory and include them in the directory
     * @throws IOException if it is not possible to load/unpack the native libs into a temp directory
     */
    public static void loadLibs() throws IOException {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native libs...");
        String OS = System.getProperty("os.name").toUpperCase();
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "OS was: " + OS);
        if (OS.contains("WIN")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/win-x86_64/libraw.dll", "/lib/win-x86_64/libjpeg.dll", "/lib/win-x86_64/zlib.dll");
        } else if (OS.contains("MAC")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/osx/libraw_r.20.dylib", "/lib/osx/libjpeg.9.dylib", "/lib/osx/libz.1.dylib");
        } else if (OS.contains("NUX")) {
            loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/linux-x86_64/libraw_r.so.20", "/lib/linux-x86_64/libm.so.6", "/lib/linux-x86_64/libjpeg.so.8", "/lib/linux-x86_64/libjasper.so.1", "/lib/linux-x86_64/libgomp.so.1", "/lib/linux-x86_64/libgcc_s.so.1", "/lib/linux-x86_64/libc.so.6", "/lib/linux-x86_64/libstdc++.so.6");
        }
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "loadLibraryFromJar: " + loadLibraryFromJar);
        for (int i = 0; i < loadLibraryFromJar.length; i++) {
            String part = loadLibraryFromJar[i];
            new File(part).deleteOnExit();
        }
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native libs...finished");
    }

    /**
     * Loading the native image from the given stream and give it as byte array back
     * @param sourceFileAsByteArray the stream as array of bytes (raw bytes)
     * @return an array of rgb bytes of the image which can be feed to the image class
     * @throws IOException if the given byte array cannot be read
     */
    public byte[] readPixelDataFromStream(byte[] sourceFileAsByteArray) throws IOException {
        if (sourceFileAsByteArray == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "sourceFileAsByteArray == null!");
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        if (loadLibraryFromJar == null) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Please call loadLibs as static method first!");
            throw new IllegalArgumentException("Please call loadLibs as static method first!");
        }
        libraries = RuntimeHelper.libraries(loadLibraryFromJar);

        try (var scope = NativeScope.unboundedScope()) {
            MemoryAddress iprc = libraw_h.libraw_init(0);
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Memory dddress native lib was: " + iprc.toRawLongValue());
            MemorySegment datasegment = libraw_h.libraw_data_t.ofAddressRestricted(iprc);
            MemorySegment params$slice = libraw_h.libraw_data_t.params$slice(datasegment);
            libraw_h.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
            libraw_h.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
            libraw_h.libraw_output_params_t.output_tiff$set(params$slice, 0);
            //libraw_output_params_t.output_bps$set(params$slice, 8);
            //libraw_output_params_t.output_color$set(params$slice, 0);        

            MemorySegment inputStreamBytes = MemorySegment.ofArray(sourceFileAsByteArray);
            MemorySegment allocateNative = scope.allocateArray(C_CHAR, sourceFileAsByteArray);
            int k = libraw_h.libraw_open_buffer(iprc, allocateNative, inputStreamBytes.byteSize());
            if (k > 0) {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open stream, return value was: " + k);
                throw new IOException("Cannot open file stream!");
            }

            libraw_h.libraw_unpack(iprc);
            libraw_h.libraw_dcraw_process(iprc);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();

            MemorySegment errorCode = scope.allocate(C_INT.byteSize());
            MemoryAddress mem_image_adr = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode.address());
            MemorySegment imageMemSegment = libraw_h.libraw_processed_image_t.ofAddressRestricted(mem_image_adr);
            MemorySegment data$slice = libraw_h.libraw_processed_image_t.data$slice(imageMemSegment);
            imageWidth = libraw_h.libraw_processed_image_t.width$get(imageMemSegment);
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native width: " + imageWidth);
            imageHeight = libraw_h.libraw_processed_image_t.height$get(imageMemSegment);
            imageBits = libraw_h.libraw_processed_image_t.bits$get(imageMemSegment);
            imageColors = libraw_h.libraw_processed_image_t.colors$get(imageMemSegment);
            stride = imageWidth * imageColors * (imageBits / 8);
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Start reading image from native memory...");
            byte[] line = new byte[stride];
            for (var i = 0; i < imageHeight; i++) {
                loader.updateImageProgress(i, imageHeight);
                MemoryAddress addOffset = data$slice.address().addOffset(stride * i);
                MemorySegment asSegmentRestricted = addOffset.asSegmentRestricted(stride);
                line = asSegmentRestricted.toByteArray();
                try {
                    bo.write(line);
                } catch (IOException ex) {
                    Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, "Cannot retrieve image from native memory", ex);
                    System.out.println("Exception " + ex.getMessage());
                    return null;
                }
            }
            bo.flush();
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Start reading image from native memory...finished");
            byte[] data = bo.toByteArray();
            bo.close();
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Freeing of native memory...");
            libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
            mem_image_adr = null;
            libraw_h.libraw_close(iprc);
            iprc = null;
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Freeing of native memory...finished");
            return data;
        }
    }

    /**
     * Reads from give URL as string the file. Please use before constructor LibrawImage(String imageFile)
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
        try (var scope = NativeScope.unboundedScope()) {
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Init native memory");
            MemoryAddress iprc = libraw_h.libraw_init(0);
            MemorySegment datasegment = libraw_h.libraw_data_t.ofAddressRestricted(iprc);
            MemorySegment params$slice = libraw_h.libraw_data_t.params$slice(datasegment);
            libraw_h.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
            libraw_h.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
            libraw_h.libraw_output_params_t.output_tiff$set(params$slice, 0);
            //libraw_output_params_t.output_bps$set(params$slice, 8);
            //libraw_output_params_t.output_color$set(params$slice, 0);

            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Open file");
            int libraw_open_file = libraw_h.libraw_open_file(iprc, CLinker.toCString(imageFileURL).address());
            if (libraw_open_file > 0) {
                Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Cannot open file stream '" + imageFileURL + "'!" + libraw_open_file);
                throw new IOException("Cannot open file stream '" + imageFileURL + "'! " + libraw_open_file);
            }

            libraw_h.libraw_unpack(iprc);
            libraw_h.libraw_dcraw_process(iprc);

            ByteArrayOutputStream bo = new ByteArrayOutputStream();

            MemorySegment errorCode = scope.allocate(C_INT.byteSize());
            MemoryAddress mem_image_adr = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode.address());
            MemorySegment imageMemSegment = libraw_h.libraw_processed_image_t.ofAddressRestricted(mem_image_adr);
            MemorySegment data$slice = libraw_h.libraw_processed_image_t.data$slice(imageMemSegment);
            imageWidth = libraw_h.libraw_processed_image_t.width$get(imageMemSegment);
            imageHeight = libraw_h.libraw_processed_image_t.height$get(imageMemSegment);
            imageBits = libraw_h.libraw_processed_image_t.bits$get(imageMemSegment);
            imageColors = libraw_h.libraw_processed_image_t.colors$get(imageMemSegment);
            //var num = imageWidth % 4;
            stride = imageWidth * imageColors * (imageBits / 8);
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Reading image from native memory...");
            byte[] line = new byte[stride];
            for (var i = 0; i < imageHeight; i++) {
                MemoryAddress addOffset = data$slice.address().addOffset(stride * i);
                MemorySegment asSegmentRestricted = addOffset.asSegmentRestricted(stride);
                line = asSegmentRestricted.toByteArray();
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
            libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
            mem_image_adr = null;
            libraw_h.libraw_close(iprc);
            iprc = null;
            Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Releasing native memory...finished.");
            return raw;
        }
    }

    /**
     * Convert RGB byte array to the corresponding INT values for javafx
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
     * URL of the given file
     * @return the URL to be read by readPixelData
     */
    public String getImageFileURL() {
        return imageFileURL;
    }

    /**
     * Gets the width of the native image
     * @return width as short (int) of the native image
     */
    public short getImageWidth() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image width " + imageWidth);
        return imageWidth;
    }

    /**
     * Gets the height of the given image
     * @return height as short (int) of the given image
     */
    public short getImageHeight() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image height " + imageHeight);
        return imageHeight;
    }

    /**
     * Image bit depth (e.g. 16bit, 8 bit, ...)
     * @return the image bits
     */
    public short getImageBits() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image bits " + imageBits);
        return imageBits;
    }

    /**
     * Image colors
     * @return if RGB or ARGB (3 or 4 color model)
     */
    public short getImageColors() {
        Logger.getLogger(LibrawImage.class.getName()).log(Level.FINEST, null, "Native image colors " + imageColors);
        return imageColors;
    }

    /**
     * One line of the native image
     * @return line of the image
     */
    public int getStride() {
        return stride;
    }

    /**
     * same as getStride but without the including the width of the image
     * @return numBands
     */
    public int getNumBands() {
        return imageColors * (imageBits / 8);
    }

    /**
     * String representation of the image
     * @return debug string which values are stored in the class
     */
    @Override
    public String toString() {
        return "LibrawImage{" + "imageFileURL=" + imageFileURL + ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight + ", imageBits=" + imageBits + ", imageColors=" + imageColors + ", stride=" + stride + '}';
    }

}
