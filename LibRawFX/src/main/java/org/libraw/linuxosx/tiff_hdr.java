// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public class tiff_hdr {

    static final MemoryLayout $struct$LAYOUT = MemoryLayout.structLayout(
        C_SHORT.withName("t_order"),
        C_SHORT.withName("magic"),
        C_INT.withName("ifd"),
        C_SHORT.withName("pad"),
        C_SHORT.withName("ntag"),
        MemoryLayout.sequenceLayout(23, MemoryLayout.structLayout(
            C_SHORT.withName("tag"),
            C_SHORT.withName("type"),
            C_INT.withName("count"),
            MemoryLayout.unionLayout(
                MemoryLayout.sequenceLayout(4, C_CHAR).withName("c"),
                MemoryLayout.sequenceLayout(2, C_SHORT).withName("s"),
                C_INT.withName("i")
            ).withName("val")
        ).withName("libraw_tiff_tag")).withName("tag"),
        C_INT.withName("nextifd"),
        C_SHORT.withName("pad2"),
        C_SHORT.withName("nexif"),
        MemoryLayout.sequenceLayout(4, MemoryLayout.structLayout(
            C_SHORT.withName("tag"),
            C_SHORT.withName("type"),
            C_INT.withName("count"),
            MemoryLayout.unionLayout(
                MemoryLayout.sequenceLayout(4, C_CHAR).withName("c"),
                MemoryLayout.sequenceLayout(2, C_SHORT).withName("s"),
                C_INT.withName("i")
            ).withName("val")
        ).withName("libraw_tiff_tag")).withName("exif"),
        C_SHORT.withName("pad3"),
        C_SHORT.withName("ngps"),
        MemoryLayout.sequenceLayout(10, MemoryLayout.structLayout(
            C_SHORT.withName("tag"),
            C_SHORT.withName("type"),
            C_INT.withName("count"),
            MemoryLayout.unionLayout(
                MemoryLayout.sequenceLayout(4, C_CHAR).withName("c"),
                MemoryLayout.sequenceLayout(2, C_SHORT).withName("s"),
                C_INT.withName("i")
            ).withName("val")
        ).withName("libraw_tiff_tag")).withName("gpst"),
        MemoryLayout.sequenceLayout(4, C_SHORT).withName("bps"),
        MemoryLayout.sequenceLayout(10, C_INT).withName("rat"),
        MemoryLayout.sequenceLayout(26, C_INT).withName("gps"),
        MemoryLayout.sequenceLayout(512, C_CHAR).withName("t_desc"),
        MemoryLayout.sequenceLayout(64, C_CHAR).withName("t_make"),
        MemoryLayout.sequenceLayout(64, C_CHAR).withName("t_model"),
        MemoryLayout.sequenceLayout(32, C_CHAR).withName("soft"),
        MemoryLayout.sequenceLayout(20, C_CHAR).withName("date"),
        MemoryLayout.sequenceLayout(64, C_CHAR).withName("t_artist")
    ).withName("tiff_hdr");
    public static MemoryLayout $LAYOUT() {
        return tiff_hdr.$struct$LAYOUT;
    }
    static final VarHandle t_order$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("t_order"));
    public static VarHandle t_order$VH() {
        return tiff_hdr.t_order$VH;
    }
    public static short t_order$get(MemorySegment seg) {
        return (short)tiff_hdr.t_order$VH.get(seg);
    }
    public static void t_order$set( MemorySegment seg, short x) {
        tiff_hdr.t_order$VH.set(seg, x);
    }
    public static short t_order$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.t_order$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void t_order$set(MemorySegment seg, long index, short x) {
        tiff_hdr.t_order$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle magic$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("magic"));
    public static VarHandle magic$VH() {
        return tiff_hdr.magic$VH;
    }
    public static short magic$get(MemorySegment seg) {
        return (short)tiff_hdr.magic$VH.get(seg);
    }
    public static void magic$set( MemorySegment seg, short x) {
        tiff_hdr.magic$VH.set(seg, x);
    }
    public static short magic$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.magic$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void magic$set(MemorySegment seg, long index, short x) {
        tiff_hdr.magic$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ifd$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("ifd"));
    public static VarHandle ifd$VH() {
        return tiff_hdr.ifd$VH;
    }
    public static int ifd$get(MemorySegment seg) {
        return (int)tiff_hdr.ifd$VH.get(seg);
    }
    public static void ifd$set( MemorySegment seg, int x) {
        tiff_hdr.ifd$VH.set(seg, x);
    }
    public static int ifd$get(MemorySegment seg, long index) {
        return (int)tiff_hdr.ifd$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ifd$set(MemorySegment seg, long index, int x) {
        tiff_hdr.ifd$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle pad$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("pad"));
    public static VarHandle pad$VH() {
        return tiff_hdr.pad$VH;
    }
    public static short pad$get(MemorySegment seg) {
        return (short)tiff_hdr.pad$VH.get(seg);
    }
    public static void pad$set( MemorySegment seg, short x) {
        tiff_hdr.pad$VH.set(seg, x);
    }
    public static short pad$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.pad$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void pad$set(MemorySegment seg, long index, short x) {
        tiff_hdr.pad$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ntag$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("ntag"));
    public static VarHandle ntag$VH() {
        return tiff_hdr.ntag$VH;
    }
    public static short ntag$get(MemorySegment seg) {
        return (short)tiff_hdr.ntag$VH.get(seg);
    }
    public static void ntag$set( MemorySegment seg, short x) {
        tiff_hdr.ntag$VH.set(seg, x);
    }
    public static short ntag$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.ntag$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ntag$set(MemorySegment seg, long index, short x) {
        tiff_hdr.ntag$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment tag$slice(MemorySegment seg) {
        return seg.asSlice(12, 276);
    }
    static final VarHandle nextifd$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("nextifd"));
    public static VarHandle nextifd$VH() {
        return tiff_hdr.nextifd$VH;
    }
    public static int nextifd$get(MemorySegment seg) {
        return (int)tiff_hdr.nextifd$VH.get(seg);
    }
    public static void nextifd$set( MemorySegment seg, int x) {
        tiff_hdr.nextifd$VH.set(seg, x);
    }
    public static int nextifd$get(MemorySegment seg, long index) {
        return (int)tiff_hdr.nextifd$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void nextifd$set(MemorySegment seg, long index, int x) {
        tiff_hdr.nextifd$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle pad2$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("pad2"));
    public static VarHandle pad2$VH() {
        return tiff_hdr.pad2$VH;
    }
    public static short pad2$get(MemorySegment seg) {
        return (short)tiff_hdr.pad2$VH.get(seg);
    }
    public static void pad2$set( MemorySegment seg, short x) {
        tiff_hdr.pad2$VH.set(seg, x);
    }
    public static short pad2$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.pad2$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void pad2$set(MemorySegment seg, long index, short x) {
        tiff_hdr.pad2$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle nexif$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("nexif"));
    public static VarHandle nexif$VH() {
        return tiff_hdr.nexif$VH;
    }
    public static short nexif$get(MemorySegment seg) {
        return (short)tiff_hdr.nexif$VH.get(seg);
    }
    public static void nexif$set( MemorySegment seg, short x) {
        tiff_hdr.nexif$VH.set(seg, x);
    }
    public static short nexif$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.nexif$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void nexif$set(MemorySegment seg, long index, short x) {
        tiff_hdr.nexif$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment exif$slice(MemorySegment seg) {
        return seg.asSlice(296, 48);
    }
    static final VarHandle pad3$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("pad3"));
    public static VarHandle pad3$VH() {
        return tiff_hdr.pad3$VH;
    }
    public static short pad3$get(MemorySegment seg) {
        return (short)tiff_hdr.pad3$VH.get(seg);
    }
    public static void pad3$set( MemorySegment seg, short x) {
        tiff_hdr.pad3$VH.set(seg, x);
    }
    public static short pad3$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.pad3$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void pad3$set(MemorySegment seg, long index, short x) {
        tiff_hdr.pad3$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ngps$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("ngps"));
    public static VarHandle ngps$VH() {
        return tiff_hdr.ngps$VH;
    }
    public static short ngps$get(MemorySegment seg) {
        return (short)tiff_hdr.ngps$VH.get(seg);
    }
    public static void ngps$set( MemorySegment seg, short x) {
        tiff_hdr.ngps$VH.set(seg, x);
    }
    public static short ngps$get(MemorySegment seg, long index) {
        return (short)tiff_hdr.ngps$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ngps$set(MemorySegment seg, long index, short x) {
        tiff_hdr.ngps$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment gpst$slice(MemorySegment seg) {
        return seg.asSlice(348, 120);
    }
    public static MemorySegment bps$slice(MemorySegment seg) {
        return seg.asSlice(468, 8);
    }
    public static MemorySegment rat$slice(MemorySegment seg) {
        return seg.asSlice(476, 40);
    }
    public static MemorySegment gps$slice(MemorySegment seg) {
        return seg.asSlice(516, 104);
    }
    public static MemorySegment t_desc$slice(MemorySegment seg) {
        return seg.asSlice(620, 512);
    }
    public static MemorySegment t_make$slice(MemorySegment seg) {
        return seg.asSlice(1132, 64);
    }
    public static MemorySegment t_model$slice(MemorySegment seg) {
        return seg.asSlice(1196, 64);
    }
    public static MemorySegment soft$slice(MemorySegment seg) {
        return seg.asSlice(1260, 32);
    }
    public static MemorySegment date$slice(MemorySegment seg) {
        return seg.asSlice(1292, 20);
    }
    public static MemorySegment t_artist$slice(MemorySegment seg) {
        return seg.asSlice(1312, 64);
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

