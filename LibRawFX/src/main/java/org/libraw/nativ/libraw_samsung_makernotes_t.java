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
 *     unsigned int ImageSizeFull[4];
 *     unsigned int ImageSizeCrop[4];
 *     int ColorSpace[2];
 *     unsigned int key[11];
 *     double DigitalGain;
 *     int DeviceType;
 *     char LensFirmware[32];
 * }
 * }
 */
public class libraw_samsung_makernotes_t {

    libraw_samsung_makernotes_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(4, libraw_h.C_INT).withName("ImageSizeFull"),
        MemoryLayout.sequenceLayout(4, libraw_h.C_INT).withName("ImageSizeCrop"),
        MemoryLayout.sequenceLayout(2, libraw_h.C_INT).withName("ColorSpace"),
        MemoryLayout.sequenceLayout(11, libraw_h.C_INT).withName("key"),
        MemoryLayout.paddingLayout(4),
        libraw_h.C_DOUBLE.withName("DigitalGain"),
        libraw_h.C_INT.withName("DeviceType"),
        MemoryLayout.sequenceLayout(32, libraw_h.C_CHAR).withName("LensFirmware"),
        MemoryLayout.paddingLayout(4)
    ).withName("$anon$605:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final SequenceLayout ImageSizeFull$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("ImageSizeFull"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static final SequenceLayout ImageSizeFull$layout() {
        return ImageSizeFull$LAYOUT;
    }

    private static final long ImageSizeFull$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static final long ImageSizeFull$offset() {
        return ImageSizeFull$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static MemorySegment ImageSizeFull(MemorySegment struct) {
        return struct.asSlice(ImageSizeFull$OFFSET, ImageSizeFull$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static void ImageSizeFull(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, ImageSizeFull$OFFSET, ImageSizeFull$LAYOUT.byteSize());
    }

    private static long[] ImageSizeFull$DIMS = { 4 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static long[] ImageSizeFull$dimensions() {
        return ImageSizeFull$DIMS;
    }
    private static final VarHandle ImageSizeFull$ELEM_HANDLE = ImageSizeFull$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static int ImageSizeFull(MemorySegment struct, long index0) {
        return (int)ImageSizeFull$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeFull[4]
     * }
     */
    public static void ImageSizeFull(MemorySegment struct, long index0, int fieldValue) {
        ImageSizeFull$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final SequenceLayout ImageSizeCrop$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("ImageSizeCrop"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static final SequenceLayout ImageSizeCrop$layout() {
        return ImageSizeCrop$LAYOUT;
    }

    private static final long ImageSizeCrop$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static final long ImageSizeCrop$offset() {
        return ImageSizeCrop$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static MemorySegment ImageSizeCrop(MemorySegment struct) {
        return struct.asSlice(ImageSizeCrop$OFFSET, ImageSizeCrop$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static void ImageSizeCrop(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, ImageSizeCrop$OFFSET, ImageSizeCrop$LAYOUT.byteSize());
    }

    private static long[] ImageSizeCrop$DIMS = { 4 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static long[] ImageSizeCrop$dimensions() {
        return ImageSizeCrop$DIMS;
    }
    private static final VarHandle ImageSizeCrop$ELEM_HANDLE = ImageSizeCrop$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static int ImageSizeCrop(MemorySegment struct, long index0) {
        return (int)ImageSizeCrop$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * unsigned int ImageSizeCrop[4]
     * }
     */
    public static void ImageSizeCrop(MemorySegment struct, long index0, int fieldValue) {
        ImageSizeCrop$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final SequenceLayout ColorSpace$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("ColorSpace"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static final SequenceLayout ColorSpace$layout() {
        return ColorSpace$LAYOUT;
    }

    private static final long ColorSpace$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static final long ColorSpace$offset() {
        return ColorSpace$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static MemorySegment ColorSpace(MemorySegment struct) {
        return struct.asSlice(ColorSpace$OFFSET, ColorSpace$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static void ColorSpace(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, ColorSpace$OFFSET, ColorSpace$LAYOUT.byteSize());
    }

    private static long[] ColorSpace$DIMS = { 2 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static long[] ColorSpace$dimensions() {
        return ColorSpace$DIMS;
    }
    private static final VarHandle ColorSpace$ELEM_HANDLE = ColorSpace$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static int ColorSpace(MemorySegment struct, long index0) {
        return (int)ColorSpace$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * int ColorSpace[2]
     * }
     */
    public static void ColorSpace(MemorySegment struct, long index0, int fieldValue) {
        ColorSpace$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final SequenceLayout key$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("key"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static final SequenceLayout key$layout() {
        return key$LAYOUT;
    }

    private static final long key$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static final long key$offset() {
        return key$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static MemorySegment key(MemorySegment struct) {
        return struct.asSlice(key$OFFSET, key$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static void key(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, key$OFFSET, key$LAYOUT.byteSize());
    }

    private static long[] key$DIMS = { 11 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static long[] key$dimensions() {
        return key$DIMS;
    }
    private static final VarHandle key$ELEM_HANDLE = key$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static int key(MemorySegment struct, long index0) {
        return (int)key$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * unsigned int key[11]
     * }
     */
    public static void key(MemorySegment struct, long index0, int fieldValue) {
        key$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
    }

    private static final OfDouble DigitalGain$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("DigitalGain"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double DigitalGain
     * }
     */
    public static final OfDouble DigitalGain$layout() {
        return DigitalGain$LAYOUT;
    }

    private static final long DigitalGain$OFFSET = 88;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double DigitalGain
     * }
     */
    public static final long DigitalGain$offset() {
        return DigitalGain$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double DigitalGain
     * }
     */
    public static double DigitalGain(MemorySegment struct) {
        return struct.get(DigitalGain$LAYOUT, DigitalGain$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double DigitalGain
     * }
     */
    public static void DigitalGain(MemorySegment struct, double fieldValue) {
        struct.set(DigitalGain$LAYOUT, DigitalGain$OFFSET, fieldValue);
    }

    private static final OfInt DeviceType$LAYOUT = (OfInt)$LAYOUT.select(groupElement("DeviceType"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int DeviceType
     * }
     */
    public static final OfInt DeviceType$layout() {
        return DeviceType$LAYOUT;
    }

    private static final long DeviceType$OFFSET = 96;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int DeviceType
     * }
     */
    public static final long DeviceType$offset() {
        return DeviceType$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int DeviceType
     * }
     */
    public static int DeviceType(MemorySegment struct) {
        return struct.get(DeviceType$LAYOUT, DeviceType$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int DeviceType
     * }
     */
    public static void DeviceType(MemorySegment struct, int fieldValue) {
        struct.set(DeviceType$LAYOUT, DeviceType$OFFSET, fieldValue);
    }

    private static final SequenceLayout LensFirmware$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("LensFirmware"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static final SequenceLayout LensFirmware$layout() {
        return LensFirmware$LAYOUT;
    }

    private static final long LensFirmware$OFFSET = 100;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static final long LensFirmware$offset() {
        return LensFirmware$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static MemorySegment LensFirmware(MemorySegment struct) {
        return struct.asSlice(LensFirmware$OFFSET, LensFirmware$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static void LensFirmware(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, LensFirmware$OFFSET, LensFirmware$LAYOUT.byteSize());
    }

    private static long[] LensFirmware$DIMS = { 32 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static long[] LensFirmware$dimensions() {
        return LensFirmware$DIMS;
    }
    private static final VarHandle LensFirmware$ELEM_HANDLE = LensFirmware$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static byte LensFirmware(MemorySegment struct, long index0) {
        return (byte)LensFirmware$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * char LensFirmware[32]
     * }
     */
    public static void LensFirmware(MemorySegment struct, long index0, byte fieldValue) {
        LensFirmware$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
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
