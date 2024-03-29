// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * struct identify_data_t {
 *     unsigned int olympus_exif_cfa;
 *     unsigned long long unique_id;
 *     unsigned long long OlyID;
 *     unsigned int tiff_nifds;
 *     int tiff_flip;
 *     int metadata_blocks;
 * };
 * }
 */
public class identify_data_t {

    public static MemoryLayout $LAYOUT() {
        return constants$94.const$1;
    }
    public static VarHandle olympus_exif_cfa$VH() {
        return constants$94.const$2;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int olympus_exif_cfa;
     * }
     */
    public static int olympus_exif_cfa$get(MemorySegment seg) {
        return (int)constants$94.const$2.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int olympus_exif_cfa;
     * }
     */
    public static void olympus_exif_cfa$set(MemorySegment seg, int x) {
        constants$94.const$2.set(seg, x);
    }
    public static int olympus_exif_cfa$get(MemorySegment seg, long index) {
        return (int)constants$94.const$2.get(seg.asSlice(index*sizeof()));
    }
    public static void olympus_exif_cfa$set(MemorySegment seg, long index, int x) {
        constants$94.const$2.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle unique_id$VH() {
        return constants$94.const$3;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned long long unique_id;
     * }
     */
    public static long unique_id$get(MemorySegment seg) {
        return (long)constants$94.const$3.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned long long unique_id;
     * }
     */
    public static void unique_id$set(MemorySegment seg, long x) {
        constants$94.const$3.set(seg, x);
    }
    public static long unique_id$get(MemorySegment seg, long index) {
        return (long)constants$94.const$3.get(seg.asSlice(index*sizeof()));
    }
    public static void unique_id$set(MemorySegment seg, long index, long x) {
        constants$94.const$3.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle OlyID$VH() {
        return constants$94.const$4;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned long long OlyID;
     * }
     */
    public static long OlyID$get(MemorySegment seg) {
        return (long)constants$94.const$4.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned long long OlyID;
     * }
     */
    public static void OlyID$set(MemorySegment seg, long x) {
        constants$94.const$4.set(seg, x);
    }
    public static long OlyID$get(MemorySegment seg, long index) {
        return (long)constants$94.const$4.get(seg.asSlice(index*sizeof()));
    }
    public static void OlyID$set(MemorySegment seg, long index, long x) {
        constants$94.const$4.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle tiff_nifds$VH() {
        return constants$94.const$5;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int tiff_nifds;
     * }
     */
    public static int tiff_nifds$get(MemorySegment seg) {
        return (int)constants$94.const$5.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int tiff_nifds;
     * }
     */
    public static void tiff_nifds$set(MemorySegment seg, int x) {
        constants$94.const$5.set(seg, x);
    }
    public static int tiff_nifds$get(MemorySegment seg, long index) {
        return (int)constants$94.const$5.get(seg.asSlice(index*sizeof()));
    }
    public static void tiff_nifds$set(MemorySegment seg, long index, int x) {
        constants$94.const$5.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle tiff_flip$VH() {
        return constants$95.const$0;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int tiff_flip;
     * }
     */
    public static int tiff_flip$get(MemorySegment seg) {
        return (int)constants$95.const$0.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int tiff_flip;
     * }
     */
    public static void tiff_flip$set(MemorySegment seg, int x) {
        constants$95.const$0.set(seg, x);
    }
    public static int tiff_flip$get(MemorySegment seg, long index) {
        return (int)constants$95.const$0.get(seg.asSlice(index*sizeof()));
    }
    public static void tiff_flip$set(MemorySegment seg, long index, int x) {
        constants$95.const$0.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle metadata_blocks$VH() {
        return constants$95.const$1;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int metadata_blocks;
     * }
     */
    public static int metadata_blocks$get(MemorySegment seg) {
        return (int)constants$95.const$1.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int metadata_blocks;
     * }
     */
    public static void metadata_blocks$set(MemorySegment seg, int x) {
        constants$95.const$1.set(seg, x);
    }
    public static int metadata_blocks$get(MemorySegment seg, long index) {
        return (int)constants$95.const$1.get(seg.asSlice(index*sizeof()));
    }
    public static void metadata_blocks$set(MemorySegment seg, long index, int x) {
        constants$95.const$1.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(long len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemorySegment addr, Arena scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


