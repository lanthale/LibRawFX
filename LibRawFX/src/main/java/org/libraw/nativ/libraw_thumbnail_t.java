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
 *     enum LibRaw_thumbnail_formats tformat;
 *     ushort twidth;
 *     ushort theight;
 *     unsigned int tlength;
 *     int tcolors;
 *     char *thumb;
 * }
 * }
 */
public class libraw_thumbnail_t {

    libraw_thumbnail_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_INT.withName("tformat"),
        libraw_h.C_SHORT.withName("twidth"),
        libraw_h.C_SHORT.withName("theight"),
        libraw_h.C_INT.withName("tlength"),
        libraw_h.C_INT.withName("tcolors"),
        libraw_h.C_POINTER.withName("thumb")
    ).withName("$anon$804:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt tformat$LAYOUT = (OfInt)$LAYOUT.select(groupElement("tformat"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * enum LibRaw_thumbnail_formats tformat
     * }
     */
    public static final OfInt tformat$layout() {
        return tformat$LAYOUT;
    }

    private static final long tformat$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * enum LibRaw_thumbnail_formats tformat
     * }
     */
    public static final long tformat$offset() {
        return tformat$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * enum LibRaw_thumbnail_formats tformat
     * }
     */
    public static int tformat(MemorySegment struct) {
        return struct.get(tformat$LAYOUT, tformat$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * enum LibRaw_thumbnail_formats tformat
     * }
     */
    public static void tformat(MemorySegment struct, int fieldValue) {
        struct.set(tformat$LAYOUT, tformat$OFFSET, fieldValue);
    }

    private static final OfShort twidth$LAYOUT = (OfShort)$LAYOUT.select(groupElement("twidth"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort twidth
     * }
     */
    public static final OfShort twidth$layout() {
        return twidth$LAYOUT;
    }

    private static final long twidth$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort twidth
     * }
     */
    public static final long twidth$offset() {
        return twidth$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort twidth
     * }
     */
    public static short twidth(MemorySegment struct) {
        return struct.get(twidth$LAYOUT, twidth$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort twidth
     * }
     */
    public static void twidth(MemorySegment struct, short fieldValue) {
        struct.set(twidth$LAYOUT, twidth$OFFSET, fieldValue);
    }

    private static final OfShort theight$LAYOUT = (OfShort)$LAYOUT.select(groupElement("theight"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort theight
     * }
     */
    public static final OfShort theight$layout() {
        return theight$LAYOUT;
    }

    private static final long theight$OFFSET = 6;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort theight
     * }
     */
    public static final long theight$offset() {
        return theight$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort theight
     * }
     */
    public static short theight(MemorySegment struct) {
        return struct.get(theight$LAYOUT, theight$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort theight
     * }
     */
    public static void theight(MemorySegment struct, short fieldValue) {
        struct.set(theight$LAYOUT, theight$OFFSET, fieldValue);
    }

    private static final OfInt tlength$LAYOUT = (OfInt)$LAYOUT.select(groupElement("tlength"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int tlength
     * }
     */
    public static final OfInt tlength$layout() {
        return tlength$LAYOUT;
    }

    private static final long tlength$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int tlength
     * }
     */
    public static final long tlength$offset() {
        return tlength$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int tlength
     * }
     */
    public static int tlength(MemorySegment struct) {
        return struct.get(tlength$LAYOUT, tlength$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int tlength
     * }
     */
    public static void tlength(MemorySegment struct, int fieldValue) {
        struct.set(tlength$LAYOUT, tlength$OFFSET, fieldValue);
    }

    private static final OfInt tcolors$LAYOUT = (OfInt)$LAYOUT.select(groupElement("tcolors"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int tcolors
     * }
     */
    public static final OfInt tcolors$layout() {
        return tcolors$LAYOUT;
    }

    private static final long tcolors$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int tcolors
     * }
     */
    public static final long tcolors$offset() {
        return tcolors$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int tcolors
     * }
     */
    public static int tcolors(MemorySegment struct) {
        return struct.get(tcolors$LAYOUT, tcolors$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int tcolors
     * }
     */
    public static void tcolors(MemorySegment struct, int fieldValue) {
        struct.set(tcolors$LAYOUT, tcolors$OFFSET, fieldValue);
    }

    private static final AddressLayout thumb$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("thumb"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *thumb
     * }
     */
    public static final AddressLayout thumb$layout() {
        return thumb$LAYOUT;
    }

    private static final long thumb$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *thumb
     * }
     */
    public static final long thumb$offset() {
        return thumb$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *thumb
     * }
     */
    public static MemorySegment thumb(MemorySegment struct) {
        return struct.get(thumb$LAYOUT, thumb$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *thumb
     * }
     */
    public static void thumb(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(thumb$LAYOUT, thumb$OFFSET, fieldValue);
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
