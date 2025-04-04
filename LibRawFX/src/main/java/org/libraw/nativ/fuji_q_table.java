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
 * struct fuji_q_table {
 *     int8_t *q_table;
 *     int raw_bits;
 *     int total_values;
 *     int max_grad;
 *     int q_grad_mult;
 *     int q_base;
 * }
 * }
 */
public class fuji_q_table {

    fuji_q_table() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_POINTER.withName("q_table"),
        libraw_h.C_INT.withName("raw_bits"),
        libraw_h.C_INT.withName("total_values"),
        libraw_h.C_INT.withName("max_grad"),
        libraw_h.C_INT.withName("q_grad_mult"),
        libraw_h.C_INT.withName("q_base"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuji_q_table");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final AddressLayout q_table$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("q_table"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int8_t *q_table
     * }
     */
    public static final AddressLayout q_table$layout() {
        return q_table$LAYOUT;
    }

    private static final long q_table$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int8_t *q_table
     * }
     */
    public static final long q_table$offset() {
        return q_table$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int8_t *q_table
     * }
     */
    public static MemorySegment q_table(MemorySegment struct) {
        return struct.get(q_table$LAYOUT, q_table$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int8_t *q_table
     * }
     */
    public static void q_table(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(q_table$LAYOUT, q_table$OFFSET, fieldValue);
    }

    private static final OfInt raw_bits$LAYOUT = (OfInt)$LAYOUT.select(groupElement("raw_bits"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int raw_bits
     * }
     */
    public static final OfInt raw_bits$layout() {
        return raw_bits$LAYOUT;
    }

    private static final long raw_bits$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int raw_bits
     * }
     */
    public static final long raw_bits$offset() {
        return raw_bits$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int raw_bits
     * }
     */
    public static int raw_bits(MemorySegment struct) {
        return struct.get(raw_bits$LAYOUT, raw_bits$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int raw_bits
     * }
     */
    public static void raw_bits(MemorySegment struct, int fieldValue) {
        struct.set(raw_bits$LAYOUT, raw_bits$OFFSET, fieldValue);
    }

    private static final OfInt total_values$LAYOUT = (OfInt)$LAYOUT.select(groupElement("total_values"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int total_values
     * }
     */
    public static final OfInt total_values$layout() {
        return total_values$LAYOUT;
    }

    private static final long total_values$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int total_values
     * }
     */
    public static final long total_values$offset() {
        return total_values$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int total_values
     * }
     */
    public static int total_values(MemorySegment struct) {
        return struct.get(total_values$LAYOUT, total_values$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int total_values
     * }
     */
    public static void total_values(MemorySegment struct, int fieldValue) {
        struct.set(total_values$LAYOUT, total_values$OFFSET, fieldValue);
    }

    private static final OfInt max_grad$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_grad"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int max_grad
     * }
     */
    public static final OfInt max_grad$layout() {
        return max_grad$LAYOUT;
    }

    private static final long max_grad$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int max_grad
     * }
     */
    public static final long max_grad$offset() {
        return max_grad$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int max_grad
     * }
     */
    public static int max_grad(MemorySegment struct) {
        return struct.get(max_grad$LAYOUT, max_grad$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int max_grad
     * }
     */
    public static void max_grad(MemorySegment struct, int fieldValue) {
        struct.set(max_grad$LAYOUT, max_grad$OFFSET, fieldValue);
    }

    private static final OfInt q_grad_mult$LAYOUT = (OfInt)$LAYOUT.select(groupElement("q_grad_mult"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int q_grad_mult
     * }
     */
    public static final OfInt q_grad_mult$layout() {
        return q_grad_mult$LAYOUT;
    }

    private static final long q_grad_mult$OFFSET = 20;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int q_grad_mult
     * }
     */
    public static final long q_grad_mult$offset() {
        return q_grad_mult$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int q_grad_mult
     * }
     */
    public static int q_grad_mult(MemorySegment struct) {
        return struct.get(q_grad_mult$LAYOUT, q_grad_mult$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int q_grad_mult
     * }
     */
    public static void q_grad_mult(MemorySegment struct, int fieldValue) {
        struct.set(q_grad_mult$LAYOUT, q_grad_mult$OFFSET, fieldValue);
    }

    private static final OfInt q_base$LAYOUT = (OfInt)$LAYOUT.select(groupElement("q_base"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int q_base
     * }
     */
    public static final OfInt q_base$layout() {
        return q_base$LAYOUT;
    }

    private static final long q_base$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int q_base
     * }
     */
    public static final long q_base$offset() {
        return q_base$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int q_base
     * }
     */
    public static int q_base(MemorySegment struct) {
        return struct.get(q_base$LAYOUT, q_base$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int q_base
     * }
     */
    public static void q_base(MemorySegment struct, int fieldValue) {
        struct.set(q_base$LAYOUT, q_base$OFFSET, fieldValue);
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

