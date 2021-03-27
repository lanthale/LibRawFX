/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
 *
 * @author selfemp
 */
public class LibrawImage {

    private final String imageFileURL;
    private InputStream imageInputStream;
    private short imageWidth;
    private short imageHeight;
    private short imageBits;
    private short imageColors;
    private int stride;
    private boolean halfsize;
    private MemoryAddress iprc;
    private MemoryAddress mem_image_adr;
    private File loadLibraryFromJar;

    public LibrawImage(String imageFile) {
        this.imageFileURL = imageFile;
        halfsize = false;
    }

    LibrawImage(InputStream input) {
        imageFileURL = null;
        this.imageInputStream = input;
    }

    public synchronized byte[] readPixelDataFromStream(byte[] sourceFileAsByteArray) throws IOException {
        if (loadLibraryFromJar == null) {
            String OS = System.getProperty("os.name").toUpperCase();
            if (OS.contains("WIN")) {
                loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/win-x86_64/libraw.dll");
            } else if (OS.contains("MAC")) {
                loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/osx/libraw.20.dylib");
            } else if (OS.contains("NUX")) {
                loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/linux-x86_64/libraw.so.20");
            }
            System.out.println("system path: "+loadLibraryFromJar.getPath());
            System.out.println("OS "+OS);
            //loadLibraryFromJar.deleteOnExit();
        }
        LibraryLookup[] LIBRARIES = RuntimeHelper.libraries(new String[]{loadLibraryFromJar.getAbsolutePath()});

        if (imageInputStream == null) {
            throw new IllegalArgumentException("input == null!");
        }
        try ( var scope = NativeScope.unboundedScope()) {
            iprc = libraw_h.libraw_init(0);
            MemorySegment datasegment = libraw_h.libraw_data_t.ofAddressRestricted(iprc);
            MemorySegment params$slice = libraw_h.libraw_data_t.params$slice(datasegment);
            if (halfsize == true) {
                libraw_h.libraw_output_params_t.half_size$set(params$slice, 1);
            }
            libraw_h.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
            libraw_h.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
            libraw_h.libraw_output_params_t.output_tiff$set(params$slice, 0);
            //libraw_output_params_t.output_bps$set(params$slice, 8);
            //libraw_output_params_t.output_color$set(params$slice, 0);        

            MemorySegment inputStreamBytes = MemorySegment.ofArray(sourceFileAsByteArray);
            MemorySegment allocateNative = scope.allocateArray(C_CHAR, sourceFileAsByteArray);
            int k = libraw_h.libraw_open_buffer(iprc, allocateNative, inputStreamBytes.byteSize());
            if (k > 0) {
                throw new IOException("Cannot open file stream!");
            }

            libraw_h.libraw_unpack(iprc);
            libraw_h.libraw_dcraw_process(iprc);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();

            MemorySegment errorCode = scope.allocate(C_INT.byteSize());
            mem_image_adr = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode.address());
            MemorySegment imageMemSegment = libraw_h.libraw_processed_image_t.ofAddressRestricted(mem_image_adr);
            MemorySegment data$slice = libraw_h.libraw_processed_image_t.data$slice(imageMemSegment);
            imageWidth = libraw_h.libraw_processed_image_t.width$get(imageMemSegment);
            imageHeight = libraw_h.libraw_processed_image_t.height$get(imageMemSegment);
            imageBits = libraw_h.libraw_processed_image_t.bits$get(imageMemSegment);
            imageColors = libraw_h.libraw_processed_image_t.colors$get(imageMemSegment);

            stride = imageWidth * imageColors * (imageBits / 8);
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
            byte[] data = bo.toByteArray();
            return data;
        }
    }

    public synchronized int[] readPixelData() throws IOException {
        if (loadLibraryFromJar == null) {
            String OS = System.getProperty("os.name").toUpperCase();
            if (OS.contains("WIN")) {
                loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/win-x86_64/libraw.dll");
            } else if (OS.contains("MAC")) {
                loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/osx/libraw.20.dylib");
            } else if (OS.contains("NUX")) {
                loadLibraryFromJar = NativeUtils.loadLibraryFromJar("/lib/linux-x86_64/libraw.so");
            }
            loadLibraryFromJar.deleteOnExit();
        }
        LibraryLookup[] LIBRARIES = RuntimeHelper.libraries(new String[]{loadLibraryFromJar.getAbsolutePath()});

        if (imageFileURL == null) {
            throw new IllegalArgumentException("imageFileURL == null!");
        }

        iprc = libraw_h.libraw_init(0);
        MemorySegment datasegment = libraw_h.libraw_data_t.ofAddressRestricted(iprc);
        MemorySegment params$slice = libraw_h.libraw_data_t.params$slice(datasegment);
        if (halfsize == true) {
            libraw_h.libraw_output_params_t.half_size$set(params$slice, 1);
        }
        libraw_h.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
        libraw_h.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
        libraw_h.libraw_output_params_t.output_tiff$set(params$slice, 0);
        //libraw_output_params_t.output_bps$set(params$slice, 8);
        //libraw_output_params_t.output_color$set(params$slice, 0);

        int libraw_open_file = libraw_h.libraw_open_file(iprc, CLinker.toCString(imageFileURL).address());

        libraw_h.libraw_unpack(iprc);
        libraw_h.libraw_dcraw_process(iprc);

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try (
                 MemorySegment errorCode = MemorySegment.allocateNative(C_INT.byteSize())) {
            mem_image_adr = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode.address());
            MemorySegment imageMemSegment = libraw_h.libraw_processed_image_t.ofAddressRestricted(mem_image_adr);
            MemorySegment data$slice = libraw_h.libraw_processed_image_t.data$slice(imageMemSegment);
            imageWidth = libraw_h.libraw_processed_image_t.width$get(imageMemSegment);
            imageHeight = libraw_h.libraw_processed_image_t.height$get(imageMemSegment);
            imageBits = libraw_h.libraw_processed_image_t.bits$get(imageMemSegment);
            imageColors = libraw_h.libraw_processed_image_t.colors$get(imageMemSegment);
            //var num = imageWidth % 4;
            var stride = imageWidth * imageColors * (imageBits / 8);
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
            libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
        }
        byte[] rawBytes = bo.toByteArray();
        int[] raw = convertToINT(rawBytes);
        libraw_h.libraw_close(iprc);
        return raw;
    }

    private int[] convertToINT(byte[] rawBytes) {
        int[] raw = new int[rawBytes.length * 4 / 3];
        for (int j = 0; j < rawBytes.length / 3; j++) {
            raw[j] = 0xFF000000
                    | ((rawBytes[3 * j + 0] & 0xFF) << 16)
                    | ((rawBytes[3 * j + 1] & 0xFF) << 8)
                    | ((rawBytes[3 * j + 2] & 0xFF));
        }
        return raw;
    }

    public String getImageFileURL() {
        return imageFileURL;
    }

    public short getImageWidth() {
        return imageWidth;
    }

    public short getImageHeight() {
        return imageHeight;
    }

    public short getImageBits() {
        return imageBits;
    }

    public short getImageColors() {
        return imageColors;
    }

    public int getStride() {
        return stride;
    }

    void setHalfSize(boolean halfsize) {
        this.halfsize = halfsize;
    }

    void dispose() {
        //libraw_h.libraw_dcraw_clear_mem(mem_image_adr);
        //libraw_h.libraw_close(iprc);
    }

}
