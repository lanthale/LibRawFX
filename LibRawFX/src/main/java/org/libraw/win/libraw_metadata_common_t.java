// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public class libraw_metadata_common_t {

    static final MemoryLayout $struct$LAYOUT = MemoryLayout.structLayout(
        C_FLOAT.withName("FlashEC"),
        C_FLOAT.withName("FlashGN"),
        C_FLOAT.withName("CameraTemperature"),
        C_FLOAT.withName("SensorTemperature"),
        C_FLOAT.withName("SensorTemperature2"),
        C_FLOAT.withName("LensTemperature"),
        C_FLOAT.withName("AmbientTemperature"),
        C_FLOAT.withName("BatteryTemperature"),
        C_FLOAT.withName("exifAmbientTemperature"),
        C_FLOAT.withName("exifHumidity"),
        C_FLOAT.withName("exifPressure"),
        C_FLOAT.withName("exifWaterDepth"),
        C_FLOAT.withName("exifAcceleration"),
        C_FLOAT.withName("exifCameraElevationAngle"),
        C_FLOAT.withName("real_ISO"),
        C_FLOAT.withName("exifExposureIndex"),
        C_SHORT.withName("ColorSpace"),
        MemoryLayout.sequenceLayout(128, C_CHAR).withName("firmware"),
        MemoryLayout.paddingLayout(16)
    );
    public static MemoryLayout $LAYOUT() {
        return libraw_metadata_common_t.$struct$LAYOUT;
    }
    static final VarHandle FlashEC$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("FlashEC"));
    public static VarHandle FlashEC$VH() {
        return libraw_metadata_common_t.FlashEC$VH;
    }
    public static float FlashEC$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.FlashEC$VH.get(seg);
    }
    public static void FlashEC$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.FlashEC$VH.set(seg, x);
    }
    public static float FlashEC$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.FlashEC$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void FlashEC$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.FlashEC$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle FlashGN$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("FlashGN"));
    public static VarHandle FlashGN$VH() {
        return libraw_metadata_common_t.FlashGN$VH;
    }
    public static float FlashGN$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.FlashGN$VH.get(seg);
    }
    public static void FlashGN$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.FlashGN$VH.set(seg, x);
    }
    public static float FlashGN$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.FlashGN$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void FlashGN$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.FlashGN$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle CameraTemperature$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("CameraTemperature"));
    public static VarHandle CameraTemperature$VH() {
        return libraw_metadata_common_t.CameraTemperature$VH;
    }
    public static float CameraTemperature$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.CameraTemperature$VH.get(seg);
    }
    public static void CameraTemperature$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.CameraTemperature$VH.set(seg, x);
    }
    public static float CameraTemperature$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.CameraTemperature$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void CameraTemperature$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.CameraTemperature$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle SensorTemperature$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("SensorTemperature"));
    public static VarHandle SensorTemperature$VH() {
        return libraw_metadata_common_t.SensorTemperature$VH;
    }
    public static float SensorTemperature$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.SensorTemperature$VH.get(seg);
    }
    public static void SensorTemperature$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.SensorTemperature$VH.set(seg, x);
    }
    public static float SensorTemperature$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.SensorTemperature$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void SensorTemperature$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.SensorTemperature$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle SensorTemperature2$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("SensorTemperature2"));
    public static VarHandle SensorTemperature2$VH() {
        return libraw_metadata_common_t.SensorTemperature2$VH;
    }
    public static float SensorTemperature2$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.SensorTemperature2$VH.get(seg);
    }
    public static void SensorTemperature2$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.SensorTemperature2$VH.set(seg, x);
    }
    public static float SensorTemperature2$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.SensorTemperature2$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void SensorTemperature2$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.SensorTemperature2$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle LensTemperature$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("LensTemperature"));
    public static VarHandle LensTemperature$VH() {
        return libraw_metadata_common_t.LensTemperature$VH;
    }
    public static float LensTemperature$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.LensTemperature$VH.get(seg);
    }
    public static void LensTemperature$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.LensTemperature$VH.set(seg, x);
    }
    public static float LensTemperature$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.LensTemperature$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void LensTemperature$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.LensTemperature$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle AmbientTemperature$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("AmbientTemperature"));
    public static VarHandle AmbientTemperature$VH() {
        return libraw_metadata_common_t.AmbientTemperature$VH;
    }
    public static float AmbientTemperature$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.AmbientTemperature$VH.get(seg);
    }
    public static void AmbientTemperature$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.AmbientTemperature$VH.set(seg, x);
    }
    public static float AmbientTemperature$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.AmbientTemperature$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void AmbientTemperature$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.AmbientTemperature$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle BatteryTemperature$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("BatteryTemperature"));
    public static VarHandle BatteryTemperature$VH() {
        return libraw_metadata_common_t.BatteryTemperature$VH;
    }
    public static float BatteryTemperature$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.BatteryTemperature$VH.get(seg);
    }
    public static void BatteryTemperature$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.BatteryTemperature$VH.set(seg, x);
    }
    public static float BatteryTemperature$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.BatteryTemperature$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void BatteryTemperature$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.BatteryTemperature$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifAmbientTemperature$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifAmbientTemperature"));
    public static VarHandle exifAmbientTemperature$VH() {
        return libraw_metadata_common_t.exifAmbientTemperature$VH;
    }
    public static float exifAmbientTemperature$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifAmbientTemperature$VH.get(seg);
    }
    public static void exifAmbientTemperature$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifAmbientTemperature$VH.set(seg, x);
    }
    public static float exifAmbientTemperature$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifAmbientTemperature$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifAmbientTemperature$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifAmbientTemperature$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifHumidity$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifHumidity"));
    public static VarHandle exifHumidity$VH() {
        return libraw_metadata_common_t.exifHumidity$VH;
    }
    public static float exifHumidity$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifHumidity$VH.get(seg);
    }
    public static void exifHumidity$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifHumidity$VH.set(seg, x);
    }
    public static float exifHumidity$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifHumidity$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifHumidity$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifHumidity$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifPressure$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifPressure"));
    public static VarHandle exifPressure$VH() {
        return libraw_metadata_common_t.exifPressure$VH;
    }
    public static float exifPressure$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifPressure$VH.get(seg);
    }
    public static void exifPressure$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifPressure$VH.set(seg, x);
    }
    public static float exifPressure$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifPressure$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifPressure$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifPressure$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifWaterDepth$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifWaterDepth"));
    public static VarHandle exifWaterDepth$VH() {
        return libraw_metadata_common_t.exifWaterDepth$VH;
    }
    public static float exifWaterDepth$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifWaterDepth$VH.get(seg);
    }
    public static void exifWaterDepth$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifWaterDepth$VH.set(seg, x);
    }
    public static float exifWaterDepth$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifWaterDepth$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifWaterDepth$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifWaterDepth$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifAcceleration$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifAcceleration"));
    public static VarHandle exifAcceleration$VH() {
        return libraw_metadata_common_t.exifAcceleration$VH;
    }
    public static float exifAcceleration$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifAcceleration$VH.get(seg);
    }
    public static void exifAcceleration$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifAcceleration$VH.set(seg, x);
    }
    public static float exifAcceleration$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifAcceleration$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifAcceleration$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifAcceleration$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifCameraElevationAngle$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifCameraElevationAngle"));
    public static VarHandle exifCameraElevationAngle$VH() {
        return libraw_metadata_common_t.exifCameraElevationAngle$VH;
    }
    public static float exifCameraElevationAngle$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifCameraElevationAngle$VH.get(seg);
    }
    public static void exifCameraElevationAngle$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifCameraElevationAngle$VH.set(seg, x);
    }
    public static float exifCameraElevationAngle$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifCameraElevationAngle$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifCameraElevationAngle$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifCameraElevationAngle$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle real_ISO$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("real_ISO"));
    public static VarHandle real_ISO$VH() {
        return libraw_metadata_common_t.real_ISO$VH;
    }
    public static float real_ISO$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.real_ISO$VH.get(seg);
    }
    public static void real_ISO$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.real_ISO$VH.set(seg, x);
    }
    public static float real_ISO$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.real_ISO$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void real_ISO$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.real_ISO$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle exifExposureIndex$VH = $struct$LAYOUT.varHandle(float.class, MemoryLayout.PathElement.groupElement("exifExposureIndex"));
    public static VarHandle exifExposureIndex$VH() {
        return libraw_metadata_common_t.exifExposureIndex$VH;
    }
    public static float exifExposureIndex$get(MemorySegment seg) {
        return (float)libraw_metadata_common_t.exifExposureIndex$VH.get(seg);
    }
    public static void exifExposureIndex$set( MemorySegment seg, float x) {
        libraw_metadata_common_t.exifExposureIndex$VH.set(seg, x);
    }
    public static float exifExposureIndex$get(MemorySegment seg, long index) {
        return (float)libraw_metadata_common_t.exifExposureIndex$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void exifExposureIndex$set(MemorySegment seg, long index, float x) {
        libraw_metadata_common_t.exifExposureIndex$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ColorSpace$VH = $struct$LAYOUT.varHandle(short.class, MemoryLayout.PathElement.groupElement("ColorSpace"));
    public static VarHandle ColorSpace$VH() {
        return libraw_metadata_common_t.ColorSpace$VH;
    }
    public static short ColorSpace$get(MemorySegment seg) {
        return (short)libraw_metadata_common_t.ColorSpace$VH.get(seg);
    }
    public static void ColorSpace$set( MemorySegment seg, short x) {
        libraw_metadata_common_t.ColorSpace$VH.set(seg, x);
    }
    public static short ColorSpace$get(MemorySegment seg, long index) {
        return (short)libraw_metadata_common_t.ColorSpace$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ColorSpace$set(MemorySegment seg, long index, short x) {
        libraw_metadata_common_t.ColorSpace$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment firmware$slice(MemorySegment seg) {
        return seg.asSlice(66, 128);
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

