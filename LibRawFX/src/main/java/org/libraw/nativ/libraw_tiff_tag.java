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
 * struct libraw_tiff_tag {
 *     ushort tag;
 *     ushort type;
 *     int count;
 *     union {
 *         char c[4];
 *         short s[2];
 *         int i;
 *     } val;
 * }
 * }
 */
public class libraw_tiff_tag {

    libraw_tiff_tag() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_SHORT.withName("tag"),
        libraw_h.C_SHORT.withName("type"),
        libraw_h.C_INT.withName("count"),
        libraw_tiff_tag.val.layout().withName("val")
    ).withName("libraw_tiff_tag");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfShort tag$LAYOUT = (OfShort)$LAYOUT.select(groupElement("tag"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort tag
     * }
     */
    public static final OfShort tag$layout() {
        return tag$LAYOUT;
    }

    private static final long tag$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort tag
     * }
     */
    public static final long tag$offset() {
        return tag$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort tag
     * }
     */
    public static short tag(MemorySegment struct) {
        return struct.get(tag$LAYOUT, tag$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort tag
     * }
     */
    public static void tag(MemorySegment struct, short fieldValue) {
        struct.set(tag$LAYOUT, tag$OFFSET, fieldValue);
    }

    private static final OfShort type$LAYOUT = (OfShort)$LAYOUT.select(groupElement("type"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort type
     * }
     */
    public static final OfShort type$layout() {
        return type$LAYOUT;
    }

    private static final long type$OFFSET = 2;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort type
     * }
     */
    public static final long type$offset() {
        return type$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort type
     * }
     */
    public static short type(MemorySegment struct) {
        return struct.get(type$LAYOUT, type$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort type
     * }
     */
    public static void type(MemorySegment struct, short fieldValue) {
        struct.set(type$LAYOUT, type$OFFSET, fieldValue);
    }

    private static final OfInt count$LAYOUT = (OfInt)$LAYOUT.select(groupElement("count"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int count
     * }
     */
    public static final OfInt count$layout() {
        return count$LAYOUT;
    }

    private static final long count$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int count
     * }
     */
    public static final long count$offset() {
        return count$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int count
     * }
     */
    public static int count(MemorySegment struct) {
        return struct.get(count$LAYOUT, count$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int count
     * }
     */
    public static void count(MemorySegment struct, int fieldValue) {
        struct.set(count$LAYOUT, count$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * union {
     *     char c[4];
     *     short s[2];
     *     int i;
     * }
     * }
     */
    public static class val {

        val() {
            // Should not be called directly
        }

        private static final GroupLayout $LAYOUT = MemoryLayout.unionLayout(
            MemoryLayout.sequenceLayout(4, libraw_h.C_CHAR).withName("c"),
            MemoryLayout.sequenceLayout(2, libraw_h.C_SHORT).withName("s"),
            libraw_h.C_INT.withName("i")
        ).withName("$anon$242:3");

        /**
         * The layout of this union
         */
        public static final GroupLayout layout() {
            return $LAYOUT;
        }

        private static final SequenceLayout c$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("c"));

        /**
         * Layout for field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static final SequenceLayout c$layout() {
            return c$LAYOUT;
        }

        private static final long c$OFFSET = 0;

        /**
         * Offset for field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static final long c$offset() {
            return c$OFFSET;
        }

        /**
         * Getter for field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static MemorySegment c(MemorySegment union) {
            return union.asSlice(c$OFFSET, c$LAYOUT.byteSize());
        }

        /**
         * Setter for field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static void c(MemorySegment union, MemorySegment fieldValue) {
            MemorySegment.copy(fieldValue, 0L, union, c$OFFSET, c$LAYOUT.byteSize());
        }

        private static long[] c$DIMS = { 4 };

        /**
         * Dimensions for array field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static long[] c$dimensions() {
            return c$DIMS;
        }
        private static final VarHandle c$ELEM_HANDLE = c$LAYOUT.varHandle(sequenceElement());

        /**
         * Indexed getter for field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static byte c(MemorySegment union, long index0) {
            return (byte)c$ELEM_HANDLE.get(union, 0L, index0);
        }

        /**
         * Indexed setter for field:
         * {@snippet lang=c :
         * char c[4]
         * }
         */
        public static void c(MemorySegment union, long index0, byte fieldValue) {
            c$ELEM_HANDLE.set(union, 0L, index0, fieldValue);
        }

        private static final SequenceLayout s$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("s"));

        /**
         * Layout for field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static final SequenceLayout s$layout() {
            return s$LAYOUT;
        }

        private static final long s$OFFSET = 0;

        /**
         * Offset for field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static final long s$offset() {
            return s$OFFSET;
        }

        /**
         * Getter for field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static MemorySegment s(MemorySegment union) {
            return union.asSlice(s$OFFSET, s$LAYOUT.byteSize());
        }

        /**
         * Setter for field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static void s(MemorySegment union, MemorySegment fieldValue) {
            MemorySegment.copy(fieldValue, 0L, union, s$OFFSET, s$LAYOUT.byteSize());
        }

        private static long[] s$DIMS = { 2 };

        /**
         * Dimensions for array field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static long[] s$dimensions() {
            return s$DIMS;
        }
        private static final VarHandle s$ELEM_HANDLE = s$LAYOUT.varHandle(sequenceElement());

        /**
         * Indexed getter for field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static short s(MemorySegment union, long index0) {
            return (short)s$ELEM_HANDLE.get(union, 0L, index0);
        }

        /**
         * Indexed setter for field:
         * {@snippet lang=c :
         * short s[2]
         * }
         */
        public static void s(MemorySegment union, long index0, short fieldValue) {
            s$ELEM_HANDLE.set(union, 0L, index0, fieldValue);
        }

        private static final OfInt i$LAYOUT = (OfInt)$LAYOUT.select(groupElement("i"));

        /**
         * Layout for field:
         * {@snippet lang=c :
         * int i
         * }
         */
        public static final OfInt i$layout() {
            return i$LAYOUT;
        }

        private static final long i$OFFSET = 0;

        /**
         * Offset for field:
         * {@snippet lang=c :
         * int i
         * }
         */
        public static final long i$offset() {
            return i$OFFSET;
        }

        /**
         * Getter for field:
         * {@snippet lang=c :
         * int i
         * }
         */
        public static int i(MemorySegment union) {
            return union.get(i$LAYOUT, i$OFFSET);
        }

        /**
         * Setter for field:
         * {@snippet lang=c :
         * int i
         * }
         */
        public static void i(MemorySegment union, int fieldValue) {
            union.set(i$LAYOUT, i$OFFSET, fieldValue);
        }

        /**
         * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
         * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
         */
        public static MemorySegment asSlice(MemorySegment array, long index) {
            return array.asSlice(layout().byteSize() * index);
        }

        /**
         * The size (in bytes) of this union
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

    private static final GroupLayout val$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("val"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * union {
     *     char c[4];
     *     short s[2];
     *     int i;
     * } val
     * }
     */
    public static final GroupLayout val$layout() {
        return val$LAYOUT;
    }

    private static final long val$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * union {
     *     char c[4];
     *     short s[2];
     *     int i;
     * } val
     * }
     */
    public static final long val$offset() {
        return val$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * union {
     *     char c[4];
     *     short s[2];
     *     int i;
     * } val
     * }
     */
    public static MemorySegment val(MemorySegment struct) {
        return struct.asSlice(val$OFFSET, val$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * union {
     *     char c[4];
     *     short s[2];
     *     int i;
     * } val
     * }
     */
    public static void val(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, val$OFFSET, val$LAYOUT.byteSize());
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
