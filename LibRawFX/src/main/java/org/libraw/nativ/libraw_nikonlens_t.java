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
 *     float EffectiveMaxAp;
 *     uchar LensIDNumber;
 *     uchar LensFStops;
 *     uchar MCUVersion;
 *     uchar LensType;
 * }
 * }
 */
public class libraw_nikonlens_t {

    libraw_nikonlens_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_FLOAT.withName("EffectiveMaxAp"),
        libraw_h.C_CHAR.withName("LensIDNumber"),
        libraw_h.C_CHAR.withName("LensFStops"),
        libraw_h.C_CHAR.withName("MCUVersion"),
        libraw_h.C_CHAR.withName("LensType")
    ).withName("$anon$1007:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfFloat EffectiveMaxAp$LAYOUT = (OfFloat)$LAYOUT.select(groupElement("EffectiveMaxAp"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * float EffectiveMaxAp
     * }
     */
    public static final OfFloat EffectiveMaxAp$layout() {
        return EffectiveMaxAp$LAYOUT;
    }

    private static final long EffectiveMaxAp$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * float EffectiveMaxAp
     * }
     */
    public static final long EffectiveMaxAp$offset() {
        return EffectiveMaxAp$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * float EffectiveMaxAp
     * }
     */
    public static float EffectiveMaxAp(MemorySegment struct) {
        return struct.get(EffectiveMaxAp$LAYOUT, EffectiveMaxAp$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * float EffectiveMaxAp
     * }
     */
    public static void EffectiveMaxAp(MemorySegment struct, float fieldValue) {
        struct.set(EffectiveMaxAp$LAYOUT, EffectiveMaxAp$OFFSET, fieldValue);
    }

    private static final OfByte LensIDNumber$LAYOUT = (OfByte)$LAYOUT.select(groupElement("LensIDNumber"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uchar LensIDNumber
     * }
     */
    public static final OfByte LensIDNumber$layout() {
        return LensIDNumber$LAYOUT;
    }

    private static final long LensIDNumber$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uchar LensIDNumber
     * }
     */
    public static final long LensIDNumber$offset() {
        return LensIDNumber$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uchar LensIDNumber
     * }
     */
    public static byte LensIDNumber(MemorySegment struct) {
        return struct.get(LensIDNumber$LAYOUT, LensIDNumber$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uchar LensIDNumber
     * }
     */
    public static void LensIDNumber(MemorySegment struct, byte fieldValue) {
        struct.set(LensIDNumber$LAYOUT, LensIDNumber$OFFSET, fieldValue);
    }

    private static final OfByte LensFStops$LAYOUT = (OfByte)$LAYOUT.select(groupElement("LensFStops"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uchar LensFStops
     * }
     */
    public static final OfByte LensFStops$layout() {
        return LensFStops$LAYOUT;
    }

    private static final long LensFStops$OFFSET = 5;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uchar LensFStops
     * }
     */
    public static final long LensFStops$offset() {
        return LensFStops$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uchar LensFStops
     * }
     */
    public static byte LensFStops(MemorySegment struct) {
        return struct.get(LensFStops$LAYOUT, LensFStops$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uchar LensFStops
     * }
     */
    public static void LensFStops(MemorySegment struct, byte fieldValue) {
        struct.set(LensFStops$LAYOUT, LensFStops$OFFSET, fieldValue);
    }

    private static final OfByte MCUVersion$LAYOUT = (OfByte)$LAYOUT.select(groupElement("MCUVersion"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uchar MCUVersion
     * }
     */
    public static final OfByte MCUVersion$layout() {
        return MCUVersion$LAYOUT;
    }

    private static final long MCUVersion$OFFSET = 6;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uchar MCUVersion
     * }
     */
    public static final long MCUVersion$offset() {
        return MCUVersion$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uchar MCUVersion
     * }
     */
    public static byte MCUVersion(MemorySegment struct) {
        return struct.get(MCUVersion$LAYOUT, MCUVersion$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uchar MCUVersion
     * }
     */
    public static void MCUVersion(MemorySegment struct, byte fieldValue) {
        struct.set(MCUVersion$LAYOUT, MCUVersion$OFFSET, fieldValue);
    }

    private static final OfByte LensType$LAYOUT = (OfByte)$LAYOUT.select(groupElement("LensType"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uchar LensType
     * }
     */
    public static final OfByte LensType$layout() {
        return LensType$LAYOUT;
    }

    private static final long LensType$OFFSET = 7;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uchar LensType
     * }
     */
    public static final long LensType$offset() {
        return LensType$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uchar LensType
     * }
     */
    public static byte LensType(MemorySegment struct) {
        return struct.get(LensType$LAYOUT, LensType$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uchar LensType
     * }
     */
    public static void LensType(MemorySegment struct, byte fieldValue) {
        struct.set(LensType$LAYOUT, LensType$OFFSET, fieldValue);
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

