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
 *     float romm_cam[9];
 * }
 * }
 */
public class libraw_P1_color_t {

    libraw_P1_color_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(9, libraw_h.C_FLOAT).withName("romm_cam")
    ).withName("$anon$255:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final SequenceLayout romm_cam$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("romm_cam"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static final SequenceLayout romm_cam$layout() {
        return romm_cam$LAYOUT;
    }

    private static final long romm_cam$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static final long romm_cam$offset() {
        return romm_cam$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static MemorySegment romm_cam(MemorySegment struct) {
        return struct.asSlice(romm_cam$OFFSET, romm_cam$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static void romm_cam(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, romm_cam$OFFSET, romm_cam$LAYOUT.byteSize());
    }

    private static long[] romm_cam$DIMS = { 9 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static long[] romm_cam$dimensions() {
        return romm_cam$DIMS;
    }
    private static final VarHandle romm_cam$ELEM_HANDLE = romm_cam$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static float romm_cam(MemorySegment struct, long index0) {
        return (float)romm_cam$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * float romm_cam[9]
     * }
     */
    public static void romm_cam(MemorySegment struct, long index0, float fieldValue) {
        romm_cam$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
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
