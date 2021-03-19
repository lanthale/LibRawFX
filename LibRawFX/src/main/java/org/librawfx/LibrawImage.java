/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import jdk.incubator.foreign.CLinker;
import static jdk.incubator.foreign.CLinker.C_INT;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import org.libraw.RuntimeHelper;
import org.libraw.libraw_h;

/**
 *
 * @author selfemp
 */
public class LibrawImage {

    private final String imageFileURL;
    private short imageWidth;
    private short imageHeight;
    private short imageBits;
    private short imageColors;        

    public LibrawImage(String imageFile) {
        this.imageFileURL = imageFile;        
    }

    public int[] readPixelData() {
        //var libraw = LibraryLookup.ofPath(Path.of("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRaw/bin/libraw.dylib"));        
        LibraryLookup[] LIBRARIES = RuntimeHelper.libraries(new String[]{"/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/Libraw-temp/libraw-osx/lib/.libs/libraw.20.dylib"});
        
        MemoryAddress iprc = libraw_h.libraw_init(0);
        MemorySegment datasegment = libraw_h.libraw_data_t.ofAddressRestricted(iprc);
        MemorySegment params$slice = libraw_h.libraw_data_t.params$slice(datasegment);
        //libraw_output_params_t.half_size$set(params$slice, 1);
        libraw_h.libraw_output_params_t.use_camera_wb$set(params$slice, 0);
        libraw_h.libraw_output_params_t.use_auto_wb$set(params$slice, 0);
        libraw_h.libraw_output_params_t.output_tiff$set(params$slice, 0);
        //libraw_output_params_t.output_bps$set(params$slice, 8);
        //libraw_output_params_t.output_color$set(params$slice, 0);

        int libraw_open_file = libraw_h.libraw_open_file(iprc, CLinker.toCString(imageFileURL).address());
        libraw_h.libraw_unpack(iprc);
        libraw_h.libraw_dcraw_process(iprc);

        MemorySegment errorCode = MemorySegment.allocateNative(C_INT.byteSize());
        MemoryAddress mem_image_adr = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode.address());
        MemorySegment imageMemSegment = libraw_h.libraw_processed_image_t.ofAddressRestricted(mem_image_adr);
        MemorySegment data$slice = libraw_h.libraw_processed_image_t.data$slice(imageMemSegment);
        imageWidth = libraw_h.libraw_processed_image_t.width$get(imageMemSegment);
        imageHeight = libraw_h.libraw_processed_image_t.height$get(imageMemSegment);
        imageBits = libraw_h.libraw_processed_image_t.bits$get(imageMemSegment);
        imageColors = libraw_h.libraw_processed_image_t.colors$get(imageMemSegment);

        //var num = imageWidth % 4;
        var stride = imageWidth * imageColors * (imageBits / 8);
        byte[] line = new byte[stride];
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
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
        libraw_h.libraw_close(iprc);

        byte[] data = bo.toByteArray();

        int[] raw = new int[data.length * 4 / 3];
        for (int i = 0; i < data.length / 3; i++) {
            raw[i] = 0xFF000000
                    | ((data[3 * i + 0] & 0xFF) << 16)
                    | ((data[3 * i + 1] & 0xFF) << 8)
                    | ((data[3 * i + 2] & 0xFF));
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

}
