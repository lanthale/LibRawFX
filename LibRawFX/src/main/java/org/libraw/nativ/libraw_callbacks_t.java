// Generated by jextract

package org.libraw.nativ;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct {
 *     data_callback data_cb;
 *     void *datacb_data;
 *     progress_callback progress_cb;
 *     void *progresscb_data;
 *     exif_parser_callback exif_cb;
 *     void *exifparser_data;
 *     pre_identify_callback pre_identify_cb;
 *     post_identify_callback post_identify_cb;
 *     process_step_callback pre_subtractblack_cb;
 *     process_step_callback pre_scalecolors_cb;
 *     process_step_callback pre_preinterpolate_cb;
 *     process_step_callback pre_interpolate_cb;
 *     process_step_callback interpolate_bayer_cb;
 *     process_step_callback interpolate_xtrans_cb;
 *     process_step_callback post_interpolate_cb;
 *     process_step_callback pre_converttorgb_cb;
 *     process_step_callback post_converttorgb_cb;
 * }
 * }
 */
public class libraw_callbacks_t {

    libraw_callbacks_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_POINTER.withName("data_cb"),
        libraw_h.C_POINTER.withName("datacb_data"),
        libraw_h.C_POINTER.withName("progress_cb"),
        libraw_h.C_POINTER.withName("progresscb_data"),
        libraw_h.C_POINTER.withName("exif_cb"),
        libraw_h.C_POINTER.withName("exifparser_data"),
        libraw_h.C_POINTER.withName("pre_identify_cb"),
        libraw_h.C_POINTER.withName("post_identify_cb"),
        libraw_h.C_POINTER.withName("pre_subtractblack_cb"),
        libraw_h.C_POINTER.withName("pre_scalecolors_cb"),
        libraw_h.C_POINTER.withName("pre_preinterpolate_cb"),
        libraw_h.C_POINTER.withName("pre_interpolate_cb"),
        libraw_h.C_POINTER.withName("interpolate_bayer_cb"),
        libraw_h.C_POINTER.withName("interpolate_xtrans_cb"),
        libraw_h.C_POINTER.withName("post_interpolate_cb"),
        libraw_h.C_POINTER.withName("pre_converttorgb_cb"),
        libraw_h.C_POINTER.withName("post_converttorgb_cb")
    ).withName("$anon$152:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final AddressLayout data_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("data_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * data_callback data_cb
     * }
     */
    public static final AddressLayout data_cb$layout() {
        return data_cb$LAYOUT;
    }

    private static final long data_cb$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * data_callback data_cb
     * }
     */
    public static final long data_cb$offset() {
        return data_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * data_callback data_cb
     * }
     */
    public static MemorySegment data_cb(MemorySegment struct) {
        return struct.get(data_cb$LAYOUT, data_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * data_callback data_cb
     * }
     */
    public static void data_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(data_cb$LAYOUT, data_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout datacb_data$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("datacb_data"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * void *datacb_data
     * }
     */
    public static final AddressLayout datacb_data$layout() {
        return datacb_data$LAYOUT;
    }

    private static final long datacb_data$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * void *datacb_data
     * }
     */
    public static final long datacb_data$offset() {
        return datacb_data$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * void *datacb_data
     * }
     */
    public static MemorySegment datacb_data(MemorySegment struct) {
        return struct.get(datacb_data$LAYOUT, datacb_data$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * void *datacb_data
     * }
     */
    public static void datacb_data(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(datacb_data$LAYOUT, datacb_data$OFFSET, fieldValue);
    }

    private static final AddressLayout progress_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("progress_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * progress_callback progress_cb
     * }
     */
    public static final AddressLayout progress_cb$layout() {
        return progress_cb$LAYOUT;
    }

    private static final long progress_cb$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * progress_callback progress_cb
     * }
     */
    public static final long progress_cb$offset() {
        return progress_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * progress_callback progress_cb
     * }
     */
    public static MemorySegment progress_cb(MemorySegment struct) {
        return struct.get(progress_cb$LAYOUT, progress_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * progress_callback progress_cb
     * }
     */
    public static void progress_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(progress_cb$LAYOUT, progress_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout progresscb_data$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("progresscb_data"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * void *progresscb_data
     * }
     */
    public static final AddressLayout progresscb_data$layout() {
        return progresscb_data$LAYOUT;
    }

    private static final long progresscb_data$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * void *progresscb_data
     * }
     */
    public static final long progresscb_data$offset() {
        return progresscb_data$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * void *progresscb_data
     * }
     */
    public static MemorySegment progresscb_data(MemorySegment struct) {
        return struct.get(progresscb_data$LAYOUT, progresscb_data$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * void *progresscb_data
     * }
     */
    public static void progresscb_data(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(progresscb_data$LAYOUT, progresscb_data$OFFSET, fieldValue);
    }

    private static final AddressLayout exif_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("exif_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * exif_parser_callback exif_cb
     * }
     */
    public static final AddressLayout exif_cb$layout() {
        return exif_cb$LAYOUT;
    }

    private static final long exif_cb$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * exif_parser_callback exif_cb
     * }
     */
    public static final long exif_cb$offset() {
        return exif_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * exif_parser_callback exif_cb
     * }
     */
    public static MemorySegment exif_cb(MemorySegment struct) {
        return struct.get(exif_cb$LAYOUT, exif_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * exif_parser_callback exif_cb
     * }
     */
    public static void exif_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(exif_cb$LAYOUT, exif_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout exifparser_data$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("exifparser_data"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * void *exifparser_data
     * }
     */
    public static final AddressLayout exifparser_data$layout() {
        return exifparser_data$LAYOUT;
    }

    private static final long exifparser_data$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * void *exifparser_data
     * }
     */
    public static final long exifparser_data$offset() {
        return exifparser_data$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * void *exifparser_data
     * }
     */
    public static MemorySegment exifparser_data(MemorySegment struct) {
        return struct.get(exifparser_data$LAYOUT, exifparser_data$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * void *exifparser_data
     * }
     */
    public static void exifparser_data(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(exifparser_data$LAYOUT, exifparser_data$OFFSET, fieldValue);
    }

    private static final AddressLayout pre_identify_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("pre_identify_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * pre_identify_callback pre_identify_cb
     * }
     */
    public static final AddressLayout pre_identify_cb$layout() {
        return pre_identify_cb$LAYOUT;
    }

    private static final long pre_identify_cb$OFFSET = 48;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * pre_identify_callback pre_identify_cb
     * }
     */
    public static final long pre_identify_cb$offset() {
        return pre_identify_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * pre_identify_callback pre_identify_cb
     * }
     */
    public static MemorySegment pre_identify_cb(MemorySegment struct) {
        return struct.get(pre_identify_cb$LAYOUT, pre_identify_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * pre_identify_callback pre_identify_cb
     * }
     */
    public static void pre_identify_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(pre_identify_cb$LAYOUT, pre_identify_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout post_identify_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("post_identify_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * post_identify_callback post_identify_cb
     * }
     */
    public static final AddressLayout post_identify_cb$layout() {
        return post_identify_cb$LAYOUT;
    }

    private static final long post_identify_cb$OFFSET = 56;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * post_identify_callback post_identify_cb
     * }
     */
    public static final long post_identify_cb$offset() {
        return post_identify_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * post_identify_callback post_identify_cb
     * }
     */
    public static MemorySegment post_identify_cb(MemorySegment struct) {
        return struct.get(post_identify_cb$LAYOUT, post_identify_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * post_identify_callback post_identify_cb
     * }
     */
    public static void post_identify_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(post_identify_cb$LAYOUT, post_identify_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout pre_subtractblack_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("pre_subtractblack_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback pre_subtractblack_cb
     * }
     */
    public static final AddressLayout pre_subtractblack_cb$layout() {
        return pre_subtractblack_cb$LAYOUT;
    }

    private static final long pre_subtractblack_cb$OFFSET = 64;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback pre_subtractblack_cb
     * }
     */
    public static final long pre_subtractblack_cb$offset() {
        return pre_subtractblack_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback pre_subtractblack_cb
     * }
     */
    public static MemorySegment pre_subtractblack_cb(MemorySegment struct) {
        return struct.get(pre_subtractblack_cb$LAYOUT, pre_subtractblack_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback pre_subtractblack_cb
     * }
     */
    public static void pre_subtractblack_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(pre_subtractblack_cb$LAYOUT, pre_subtractblack_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout pre_scalecolors_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("pre_scalecolors_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback pre_scalecolors_cb
     * }
     */
    public static final AddressLayout pre_scalecolors_cb$layout() {
        return pre_scalecolors_cb$LAYOUT;
    }

    private static final long pre_scalecolors_cb$OFFSET = 72;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback pre_scalecolors_cb
     * }
     */
    public static final long pre_scalecolors_cb$offset() {
        return pre_scalecolors_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback pre_scalecolors_cb
     * }
     */
    public static MemorySegment pre_scalecolors_cb(MemorySegment struct) {
        return struct.get(pre_scalecolors_cb$LAYOUT, pre_scalecolors_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback pre_scalecolors_cb
     * }
     */
    public static void pre_scalecolors_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(pre_scalecolors_cb$LAYOUT, pre_scalecolors_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout pre_preinterpolate_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("pre_preinterpolate_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback pre_preinterpolate_cb
     * }
     */
    public static final AddressLayout pre_preinterpolate_cb$layout() {
        return pre_preinterpolate_cb$LAYOUT;
    }

    private static final long pre_preinterpolate_cb$OFFSET = 80;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback pre_preinterpolate_cb
     * }
     */
    public static final long pre_preinterpolate_cb$offset() {
        return pre_preinterpolate_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback pre_preinterpolate_cb
     * }
     */
    public static MemorySegment pre_preinterpolate_cb(MemorySegment struct) {
        return struct.get(pre_preinterpolate_cb$LAYOUT, pre_preinterpolate_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback pre_preinterpolate_cb
     * }
     */
    public static void pre_preinterpolate_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(pre_preinterpolate_cb$LAYOUT, pre_preinterpolate_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout pre_interpolate_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("pre_interpolate_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback pre_interpolate_cb
     * }
     */
    public static final AddressLayout pre_interpolate_cb$layout() {
        return pre_interpolate_cb$LAYOUT;
    }

    private static final long pre_interpolate_cb$OFFSET = 88;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback pre_interpolate_cb
     * }
     */
    public static final long pre_interpolate_cb$offset() {
        return pre_interpolate_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback pre_interpolate_cb
     * }
     */
    public static MemorySegment pre_interpolate_cb(MemorySegment struct) {
        return struct.get(pre_interpolate_cb$LAYOUT, pre_interpolate_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback pre_interpolate_cb
     * }
     */
    public static void pre_interpolate_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(pre_interpolate_cb$LAYOUT, pre_interpolate_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout interpolate_bayer_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("interpolate_bayer_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_bayer_cb
     * }
     */
    public static final AddressLayout interpolate_bayer_cb$layout() {
        return interpolate_bayer_cb$LAYOUT;
    }

    private static final long interpolate_bayer_cb$OFFSET = 96;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_bayer_cb
     * }
     */
    public static final long interpolate_bayer_cb$offset() {
        return interpolate_bayer_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_bayer_cb
     * }
     */
    public static MemorySegment interpolate_bayer_cb(MemorySegment struct) {
        return struct.get(interpolate_bayer_cb$LAYOUT, interpolate_bayer_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_bayer_cb
     * }
     */
    public static void interpolate_bayer_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(interpolate_bayer_cb$LAYOUT, interpolate_bayer_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout interpolate_xtrans_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("interpolate_xtrans_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_xtrans_cb
     * }
     */
    public static final AddressLayout interpolate_xtrans_cb$layout() {
        return interpolate_xtrans_cb$LAYOUT;
    }

    private static final long interpolate_xtrans_cb$OFFSET = 104;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_xtrans_cb
     * }
     */
    public static final long interpolate_xtrans_cb$offset() {
        return interpolate_xtrans_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_xtrans_cb
     * }
     */
    public static MemorySegment interpolate_xtrans_cb(MemorySegment struct) {
        return struct.get(interpolate_xtrans_cb$LAYOUT, interpolate_xtrans_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback interpolate_xtrans_cb
     * }
     */
    public static void interpolate_xtrans_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(interpolate_xtrans_cb$LAYOUT, interpolate_xtrans_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout post_interpolate_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("post_interpolate_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback post_interpolate_cb
     * }
     */
    public static final AddressLayout post_interpolate_cb$layout() {
        return post_interpolate_cb$LAYOUT;
    }

    private static final long post_interpolate_cb$OFFSET = 112;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback post_interpolate_cb
     * }
     */
    public static final long post_interpolate_cb$offset() {
        return post_interpolate_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback post_interpolate_cb
     * }
     */
    public static MemorySegment post_interpolate_cb(MemorySegment struct) {
        return struct.get(post_interpolate_cb$LAYOUT, post_interpolate_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback post_interpolate_cb
     * }
     */
    public static void post_interpolate_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(post_interpolate_cb$LAYOUT, post_interpolate_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout pre_converttorgb_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("pre_converttorgb_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback pre_converttorgb_cb
     * }
     */
    public static final AddressLayout pre_converttorgb_cb$layout() {
        return pre_converttorgb_cb$LAYOUT;
    }

    private static final long pre_converttorgb_cb$OFFSET = 120;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback pre_converttorgb_cb
     * }
     */
    public static final long pre_converttorgb_cb$offset() {
        return pre_converttorgb_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback pre_converttorgb_cb
     * }
     */
    public static MemorySegment pre_converttorgb_cb(MemorySegment struct) {
        return struct.get(pre_converttorgb_cb$LAYOUT, pre_converttorgb_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback pre_converttorgb_cb
     * }
     */
    public static void pre_converttorgb_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(pre_converttorgb_cb$LAYOUT, pre_converttorgb_cb$OFFSET, fieldValue);
    }

    private static final AddressLayout post_converttorgb_cb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("post_converttorgb_cb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * process_step_callback post_converttorgb_cb
     * }
     */
    public static final AddressLayout post_converttorgb_cb$layout() {
        return post_converttorgb_cb$LAYOUT;
    }

    private static final long post_converttorgb_cb$OFFSET = 128;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * process_step_callback post_converttorgb_cb
     * }
     */
    public static final long post_converttorgb_cb$offset() {
        return post_converttorgb_cb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * process_step_callback post_converttorgb_cb
     * }
     */
    public static MemorySegment post_converttorgb_cb(MemorySegment struct) {
        return struct.get(post_converttorgb_cb$LAYOUT, post_converttorgb_cb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * process_step_callback post_converttorgb_cb
     * }
     */
    public static void post_converttorgb_cb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(post_converttorgb_cb$LAYOUT, post_converttorgb_cb$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}
