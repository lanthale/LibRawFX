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
 *     ushort cleft;
 *     ushort ctop;
 *     ushort cwidth;
 *     ushort cheight;
 * }
 * }
 */
public class libraw_raw_inset_crop_t {

    libraw_raw_inset_crop_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_SHORT.withName("cleft"),
        libraw_h.C_SHORT.withName("ctop"),
        libraw_h.C_SHORT.withName("cwidth"),
        libraw_h.C_SHORT.withName("cheight")
    ).withName("$anon$200:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfShort cleft$LAYOUT = (OfShort)$LAYOUT.select(groupElement("cleft"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort cleft
     * }
     */
    public static final OfShort cleft$layout() {
        return cleft$LAYOUT;
    }

    private static final long cleft$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort cleft
     * }
     */
    public static final long cleft$offset() {
        return cleft$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort cleft
     * }
     */
    public static short cleft(MemorySegment struct) {
        return struct.get(cleft$LAYOUT, cleft$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort cleft
     * }
     */
    public static void cleft(MemorySegment struct, short fieldValue) {
        struct.set(cleft$LAYOUT, cleft$OFFSET, fieldValue);
    }

    private static final OfShort ctop$LAYOUT = (OfShort)$LAYOUT.select(groupElement("ctop"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort ctop
     * }
     */
    public static final OfShort ctop$layout() {
        return ctop$LAYOUT;
    }

    private static final long ctop$OFFSET = 2;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort ctop
     * }
     */
    public static final long ctop$offset() {
        return ctop$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort ctop
     * }
     */
    public static short ctop(MemorySegment struct) {
        return struct.get(ctop$LAYOUT, ctop$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort ctop
     * }
     */
    public static void ctop(MemorySegment struct, short fieldValue) {
        struct.set(ctop$LAYOUT, ctop$OFFSET, fieldValue);
    }

    private static final OfShort cwidth$LAYOUT = (OfShort)$LAYOUT.select(groupElement("cwidth"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort cwidth
     * }
     */
    public static final OfShort cwidth$layout() {
        return cwidth$LAYOUT;
    }

    private static final long cwidth$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort cwidth
     * }
     */
    public static final long cwidth$offset() {
        return cwidth$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort cwidth
     * }
     */
    public static short cwidth(MemorySegment struct) {
        return struct.get(cwidth$LAYOUT, cwidth$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort cwidth
     * }
     */
    public static void cwidth(MemorySegment struct, short fieldValue) {
        struct.set(cwidth$LAYOUT, cwidth$OFFSET, fieldValue);
    }

    private static final OfShort cheight$LAYOUT = (OfShort)$LAYOUT.select(groupElement("cheight"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort cheight
     * }
     */
    public static final OfShort cheight$layout() {
        return cheight$LAYOUT;
    }

    private static final long cheight$OFFSET = 6;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort cheight
     * }
     */
    public static final long cheight$offset() {
        return cheight$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort cheight
     * }
     */
    public static short cheight(MemorySegment struct) {
        return struct.get(cheight$LAYOUT, cheight$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort cheight
     * }
     */
    public static void cheight(MemorySegment struct, short fieldValue) {
        struct.set(cheight$LAYOUT, cheight$OFFSET, fieldValue);
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
