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
 *     int use_rawspeed;
 *     int use_dngsdk;
 *     unsigned int options;
 *     unsigned int shot_select;
 *     unsigned int specials;
 *     unsigned int max_raw_memory_mb;
 *     int sony_arw2_posterization_thr;
 *     float coolscan_nef_gamma;
 *     char p4shot_order[5];
 *     char **custom_camera_strings;
 * }
 * }
 */
public class libraw_raw_unpack_params_t {

    libraw_raw_unpack_params_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_INT.withName("use_rawspeed"),
        libraw_h.C_INT.withName("use_dngsdk"),
        libraw_h.C_INT.withName("options"),
        libraw_h.C_INT.withName("shot_select"),
        libraw_h.C_INT.withName("specials"),
        libraw_h.C_INT.withName("max_raw_memory_mb"),
        libraw_h.C_INT.withName("sony_arw2_posterization_thr"),
        libraw_h.C_FLOAT.withName("coolscan_nef_gamma"),
        MemoryLayout.sequenceLayout(5, libraw_h.C_CHAR).withName("p4shot_order"),
        MemoryLayout.paddingLayout(3),
        libraw_h.C_POINTER.withName("custom_camera_strings")
    ).withName("$anon$932:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt use_rawspeed$LAYOUT = (OfInt)$LAYOUT.select(groupElement("use_rawspeed"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int use_rawspeed
     * }
     */
    public static final OfInt use_rawspeed$layout() {
        return use_rawspeed$LAYOUT;
    }

    private static final long use_rawspeed$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int use_rawspeed
     * }
     */
    public static final long use_rawspeed$offset() {
        return use_rawspeed$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int use_rawspeed
     * }
     */
    public static int use_rawspeed(MemorySegment struct) {
        return struct.get(use_rawspeed$LAYOUT, use_rawspeed$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int use_rawspeed
     * }
     */
    public static void use_rawspeed(MemorySegment struct, int fieldValue) {
        struct.set(use_rawspeed$LAYOUT, use_rawspeed$OFFSET, fieldValue);
    }

    private static final OfInt use_dngsdk$LAYOUT = (OfInt)$LAYOUT.select(groupElement("use_dngsdk"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int use_dngsdk
     * }
     */
    public static final OfInt use_dngsdk$layout() {
        return use_dngsdk$LAYOUT;
    }

    private static final long use_dngsdk$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int use_dngsdk
     * }
     */
    public static final long use_dngsdk$offset() {
        return use_dngsdk$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int use_dngsdk
     * }
     */
    public static int use_dngsdk(MemorySegment struct) {
        return struct.get(use_dngsdk$LAYOUT, use_dngsdk$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int use_dngsdk
     * }
     */
    public static void use_dngsdk(MemorySegment struct, int fieldValue) {
        struct.set(use_dngsdk$LAYOUT, use_dngsdk$OFFSET, fieldValue);
    }

    private static final OfInt options$LAYOUT = (OfInt)$LAYOUT.select(groupElement("options"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int options
     * }
     */
    public static final OfInt options$layout() {
        return options$LAYOUT;
    }

    private static final long options$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int options
     * }
     */
    public static final long options$offset() {
        return options$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int options
     * }
     */
    public static int options(MemorySegment struct) {
        return struct.get(options$LAYOUT, options$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int options
     * }
     */
    public static void options(MemorySegment struct, int fieldValue) {
        struct.set(options$LAYOUT, options$OFFSET, fieldValue);
    }

    private static final OfInt shot_select$LAYOUT = (OfInt)$LAYOUT.select(groupElement("shot_select"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int shot_select
     * }
     */
    public static final OfInt shot_select$layout() {
        return shot_select$LAYOUT;
    }

    private static final long shot_select$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int shot_select
     * }
     */
    public static final long shot_select$offset() {
        return shot_select$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int shot_select
     * }
     */
    public static int shot_select(MemorySegment struct) {
        return struct.get(shot_select$LAYOUT, shot_select$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int shot_select
     * }
     */
    public static void shot_select(MemorySegment struct, int fieldValue) {
        struct.set(shot_select$LAYOUT, shot_select$OFFSET, fieldValue);
    }

    private static final OfInt specials$LAYOUT = (OfInt)$LAYOUT.select(groupElement("specials"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int specials
     * }
     */
    public static final OfInt specials$layout() {
        return specials$LAYOUT;
    }

    private static final long specials$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int specials
     * }
     */
    public static final long specials$offset() {
        return specials$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int specials
     * }
     */
    public static int specials(MemorySegment struct) {
        return struct.get(specials$LAYOUT, specials$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int specials
     * }
     */
    public static void specials(MemorySegment struct, int fieldValue) {
        struct.set(specials$LAYOUT, specials$OFFSET, fieldValue);
    }

    private static final OfInt max_raw_memory_mb$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_raw_memory_mb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_raw_memory_mb
     * }
     */
    public static final OfInt max_raw_memory_mb$layout() {
        return max_raw_memory_mb$LAYOUT;
    }

    private static final long max_raw_memory_mb$OFFSET = 20;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_raw_memory_mb
     * }
     */
    public static final long max_raw_memory_mb$offset() {
        return max_raw_memory_mb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_raw_memory_mb
     * }
     */
    public static int max_raw_memory_mb(MemorySegment struct) {
        return struct.get(max_raw_memory_mb$LAYOUT, max_raw_memory_mb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_raw_memory_mb
     * }
     */
    public static void max_raw_memory_mb(MemorySegment struct, int fieldValue) {
        struct.set(max_raw_memory_mb$LAYOUT, max_raw_memory_mb$OFFSET, fieldValue);
    }

    private static final OfInt sony_arw2_posterization_thr$LAYOUT = (OfInt)$LAYOUT.select(groupElement("sony_arw2_posterization_thr"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int sony_arw2_posterization_thr
     * }
     */
    public static final OfInt sony_arw2_posterization_thr$layout() {
        return sony_arw2_posterization_thr$LAYOUT;
    }

    private static final long sony_arw2_posterization_thr$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int sony_arw2_posterization_thr
     * }
     */
    public static final long sony_arw2_posterization_thr$offset() {
        return sony_arw2_posterization_thr$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int sony_arw2_posterization_thr
     * }
     */
    public static int sony_arw2_posterization_thr(MemorySegment struct) {
        return struct.get(sony_arw2_posterization_thr$LAYOUT, sony_arw2_posterization_thr$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int sony_arw2_posterization_thr
     * }
     */
    public static void sony_arw2_posterization_thr(MemorySegment struct, int fieldValue) {
        struct.set(sony_arw2_posterization_thr$LAYOUT, sony_arw2_posterization_thr$OFFSET, fieldValue);
    }

    private static final OfFloat coolscan_nef_gamma$LAYOUT = (OfFloat)$LAYOUT.select(groupElement("coolscan_nef_gamma"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * float coolscan_nef_gamma
     * }
     */
    public static final OfFloat coolscan_nef_gamma$layout() {
        return coolscan_nef_gamma$LAYOUT;
    }

    private static final long coolscan_nef_gamma$OFFSET = 28;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * float coolscan_nef_gamma
     * }
     */
    public static final long coolscan_nef_gamma$offset() {
        return coolscan_nef_gamma$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * float coolscan_nef_gamma
     * }
     */
    public static float coolscan_nef_gamma(MemorySegment struct) {
        return struct.get(coolscan_nef_gamma$LAYOUT, coolscan_nef_gamma$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * float coolscan_nef_gamma
     * }
     */
    public static void coolscan_nef_gamma(MemorySegment struct, float fieldValue) {
        struct.set(coolscan_nef_gamma$LAYOUT, coolscan_nef_gamma$OFFSET, fieldValue);
    }

    private static final SequenceLayout p4shot_order$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("p4shot_order"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static final SequenceLayout p4shot_order$layout() {
        return p4shot_order$LAYOUT;
    }

    private static final long p4shot_order$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static final long p4shot_order$offset() {
        return p4shot_order$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static MemorySegment p4shot_order(MemorySegment struct) {
        return struct.asSlice(p4shot_order$OFFSET, p4shot_order$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static void p4shot_order(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, p4shot_order$OFFSET, p4shot_order$LAYOUT.byteSize());
    }

    private static long[] p4shot_order$DIMS = { 5 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static long[] p4shot_order$dimensions() {
        return p4shot_order$DIMS;
    }
    private static final VarHandle p4shot_order$ELEM_HANDLE = p4shot_order$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static byte p4shot_order(MemorySegment struct, long index0) {
        return (byte)p4shot_order$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * char p4shot_order[5]
     * }
     */
    public static void p4shot_order(MemorySegment struct, long index0, byte fieldValue) {
        p4shot_order$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final AddressLayout custom_camera_strings$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("custom_camera_strings"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char **custom_camera_strings
     * }
     */
    public static final AddressLayout custom_camera_strings$layout() {
        return custom_camera_strings$LAYOUT;
    }

    private static final long custom_camera_strings$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char **custom_camera_strings
     * }
     */
    public static final long custom_camera_strings$offset() {
        return custom_camera_strings$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char **custom_camera_strings
     * }
     */
    public static MemorySegment custom_camera_strings(MemorySegment struct) {
        return struct.get(custom_camera_strings$LAYOUT, custom_camera_strings$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char **custom_camera_strings
     * }
     */
    public static void custom_camera_strings(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(custom_camera_strings$LAYOUT, custom_camera_strings$OFFSET, fieldValue);
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
     * Allocate a segment of size @code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size @code elementCount} using {@code allocator}.
     * The returned segment has size @code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size @code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size @code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}

