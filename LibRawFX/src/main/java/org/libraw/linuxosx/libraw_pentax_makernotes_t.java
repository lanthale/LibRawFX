// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public class libraw_pentax_makernotes_t {

    static final MemoryLayout $struct$LAYOUT = MemoryLayout.structLayout(
        C_SHORT.withName("FocusMode"),
        C_SHORT.withName("AFPointSelected"),
        C_INT.withName("AFPointsInFocus"),
        C_SHORT.withName("FocusPosition"),
        MemoryLayout.sequenceLayout(4, C_CHAR).withName("DriveMode"),
        C_SHORT.withName("AFAdjustment"),
        C_CHAR.withName("MultiExposure"),
        MemoryLayout.paddingLayout(8),
        C_SHORT.withName("Quality")
    );
    public static MemoryLayout $LAYOUT() {
        return libraw_pentax_makernotes_t.$struct$LAYOUT;
    }
    static final VarHandle FocusMode$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("FocusMode"));
    public static VarHandle FocusMode$VH() {
        return libraw_pentax_makernotes_t.FocusMode$VH;
    }
    public static short FocusMode$get(MemorySegment seg) {
        return (short)libraw_pentax_makernotes_t.FocusMode$VH.get(seg);
    }
    public static void FocusMode$set( MemorySegment seg, short x) {
        libraw_pentax_makernotes_t.FocusMode$VH.set(seg, x);
    }
    public static short FocusMode$get(MemorySegment seg, long index) {
        return (short)libraw_pentax_makernotes_t.FocusMode$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void FocusMode$set(MemorySegment seg, long index, short x) {
        libraw_pentax_makernotes_t.FocusMode$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle AFPointSelected$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("AFPointSelected"));
    public static VarHandle AFPointSelected$VH() {
        return libraw_pentax_makernotes_t.AFPointSelected$VH;
    }
    public static short AFPointSelected$get(MemorySegment seg) {
        return (short)libraw_pentax_makernotes_t.AFPointSelected$VH.get(seg);
    }
    public static void AFPointSelected$set( MemorySegment seg, short x) {
        libraw_pentax_makernotes_t.AFPointSelected$VH.set(seg, x);
    }
    public static short AFPointSelected$get(MemorySegment seg, long index) {
        return (short)libraw_pentax_makernotes_t.AFPointSelected$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void AFPointSelected$set(MemorySegment seg, long index, short x) {
        libraw_pentax_makernotes_t.AFPointSelected$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle AFPointsInFocus$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("AFPointsInFocus"));
    public static VarHandle AFPointsInFocus$VH() {
        return libraw_pentax_makernotes_t.AFPointsInFocus$VH;
    }
    public static int AFPointsInFocus$get(MemorySegment seg) {
        return (int)libraw_pentax_makernotes_t.AFPointsInFocus$VH.get(seg);
    }
    public static void AFPointsInFocus$set( MemorySegment seg, int x) {
        libraw_pentax_makernotes_t.AFPointsInFocus$VH.set(seg, x);
    }
    public static int AFPointsInFocus$get(MemorySegment seg, long index) {
        return (int)libraw_pentax_makernotes_t.AFPointsInFocus$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void AFPointsInFocus$set(MemorySegment seg, long index, int x) {
        libraw_pentax_makernotes_t.AFPointsInFocus$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle FocusPosition$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("FocusPosition"));
    public static VarHandle FocusPosition$VH() {
        return libraw_pentax_makernotes_t.FocusPosition$VH;
    }
    public static short FocusPosition$get(MemorySegment seg) {
        return (short)libraw_pentax_makernotes_t.FocusPosition$VH.get(seg);
    }
    public static void FocusPosition$set( MemorySegment seg, short x) {
        libraw_pentax_makernotes_t.FocusPosition$VH.set(seg, x);
    }
    public static short FocusPosition$get(MemorySegment seg, long index) {
        return (short)libraw_pentax_makernotes_t.FocusPosition$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void FocusPosition$set(MemorySegment seg, long index, short x) {
        libraw_pentax_makernotes_t.FocusPosition$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment DriveMode$slice(MemorySegment seg) {
        return seg.asSlice(10, 4);
    }
    static final VarHandle AFAdjustment$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("AFAdjustment"));
    public static VarHandle AFAdjustment$VH() {
        return libraw_pentax_makernotes_t.AFAdjustment$VH;
    }
    public static short AFAdjustment$get(MemorySegment seg) {
        return (short)libraw_pentax_makernotes_t.AFAdjustment$VH.get(seg);
    }
    public static void AFAdjustment$set( MemorySegment seg, short x) {
        libraw_pentax_makernotes_t.AFAdjustment$VH.set(seg, x);
    }
    public static short AFAdjustment$get(MemorySegment seg, long index) {
        return (short)libraw_pentax_makernotes_t.AFAdjustment$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void AFAdjustment$set(MemorySegment seg, long index, short x) {
        libraw_pentax_makernotes_t.AFAdjustment$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle MultiExposure$VH = $struct$LAYOUT.varHandle(byte.class, MemoryLayout.PathElement.groupElement("MultiExposure"));
    public static VarHandle MultiExposure$VH() {
        return libraw_pentax_makernotes_t.MultiExposure$VH;
    }
    public static byte MultiExposure$get(MemorySegment seg) {
        return (byte)libraw_pentax_makernotes_t.MultiExposure$VH.get(seg);
    }
    public static void MultiExposure$set( MemorySegment seg, byte x) {
        libraw_pentax_makernotes_t.MultiExposure$VH.set(seg, x);
    }
    public static byte MultiExposure$get(MemorySegment seg, long index) {
        return (byte)libraw_pentax_makernotes_t.MultiExposure$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void MultiExposure$set(MemorySegment seg, long index, byte x) {
        libraw_pentax_makernotes_t.MultiExposure$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle Quality$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("Quality"));
    public static VarHandle Quality$VH() {
        return libraw_pentax_makernotes_t.Quality$VH;
    }
    public static short Quality$get(MemorySegment seg) {
        return (short)libraw_pentax_makernotes_t.Quality$VH.get(seg);
    }
    public static void Quality$set( MemorySegment seg, short x) {
        libraw_pentax_makernotes_t.Quality$VH.set(seg, x);
    }
    public static short Quality$get(MemorySegment seg, long index) {
        return (short)libraw_pentax_makernotes_t.Quality$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void Quality$set(MemorySegment seg, long index, short x) {
        libraw_pentax_makernotes_t.Quality$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocate(ResourceScope scope) { return allocate(SegmentAllocator.ofScope(scope)); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment allocateArray(int len, ResourceScope scope) {
        return allocateArray(len, SegmentAllocator.ofScope(scope));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, ResourceScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}

