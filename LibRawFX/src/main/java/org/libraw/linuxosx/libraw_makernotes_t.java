// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public class libraw_makernotes_t {

    static final MemoryLayout $struct$LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.structLayout(
            C_INT.withName("ColorDataVer"),
            C_INT.withName("ColorDataSubVer"),
            C_INT.withName("SpecularWhiteLevel"),
            C_INT.withName("NormalWhiteLevel"),
            MemoryLayout.sequenceLayout(4, C_INT).withName("ChannelBlackLevel"),
            C_INT.withName("AverageBlackLevel"),
            MemoryLayout.sequenceLayout(4, C_INT).withName("multishot"),
            C_SHORT.withName("MeteringMode"),
            C_SHORT.withName("SpotMeteringMode"),
            C_CHAR.withName("FlashMeteringMode"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("FlashExposureLock"),
            C_SHORT.withName("ExposureMode"),
            C_SHORT.withName("AESetting"),
            C_CHAR.withName("HighlightTonePriority"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("ImageStabilization"),
            C_SHORT.withName("FocusMode"),
            C_SHORT.withName("AFPoint"),
            C_SHORT.withName("FocusContinuous"),
            C_SHORT.withName("AFPointsInFocus30D"),
            MemoryLayout.sequenceLayout(8, C_CHAR).withName("AFPointsInFocus1D"),
            C_SHORT.withName("AFPointsInFocus5D"),
            C_SHORT.withName("AFAreaMode"),
            C_SHORT.withName("NumAFPoints"),
            C_SHORT.withName("ValidAFPoints"),
            C_SHORT.withName("AFImageWidth"),
            C_SHORT.withName("AFImageHeight"),
            MemoryLayout.sequenceLayout(61, C_SHORT).withName("AFAreaWidths"),
            MemoryLayout.sequenceLayout(61, C_SHORT).withName("AFAreaHeights"),
            MemoryLayout.sequenceLayout(61, C_SHORT).withName("AFAreaXPositions"),
            MemoryLayout.sequenceLayout(61, C_SHORT).withName("AFAreaYPositions"),
            MemoryLayout.sequenceLayout(4, C_SHORT).withName("AFPointsInFocus"),
            MemoryLayout.sequenceLayout(4, C_SHORT).withName("AFPointsSelected"),
            C_SHORT.withName("PrimaryAFPoint"),
            C_SHORT.withName("FlashMode"),
            C_SHORT.withName("FlashActivity"),
            C_SHORT.withName("FlashBits"),
            C_SHORT.withName("ManualFlashOutput"),
            C_SHORT.withName("FlashOutput"),
            C_SHORT.withName("FlashGuideNumber"),
            C_SHORT.withName("ContinuousDrive"),
            C_SHORT.withName("SensorWidth"),
            C_SHORT.withName("SensorHeight"),
            C_SHORT.withName("SensorLeftBorder"),
            C_SHORT.withName("SensorTopBorder"),
            C_SHORT.withName("SensorRightBorder"),
            C_SHORT.withName("SensorBottomBorder"),
            C_SHORT.withName("BlackMaskLeftBorder"),
            C_SHORT.withName("BlackMaskTopBorder"),
            C_SHORT.withName("BlackMaskRightBorder"),
            C_SHORT.withName("BlackMaskBottomBorder"),
            C_INT.withName("AFMicroAdjMode"),
            C_FLOAT.withName("AFMicroAdjValue"),
            C_SHORT.withName("MakernotesFlip"),
            C_SHORT.withName("RecordMode"),
            C_SHORT.withName("SRAWQuality"),
            MemoryLayout.paddingLayout(16),
            C_INT.withName("wbi"),
            C_FLOAT.withName("firmware"),
            C_SHORT.withName("RF_lensID"),
            MemoryLayout.paddingLayout(16)
        ).withName("canon"),
        MemoryLayout.structLayout(
            C_DOUBLE.withName("ExposureBracketValue"),
            C_SHORT.withName("ActiveDLighting"),
            C_SHORT.withName("ShootingMode"),
            MemoryLayout.sequenceLayout(7, C_CHAR).withName("ImageStabilization"),
            C_CHAR.withName("VibrationReduction"),
            C_CHAR.withName("VRMode"),
            MemoryLayout.sequenceLayout(7, C_CHAR).withName("FocusMode"),
            C_CHAR.withName("AFPoint"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("AFPointsInFocus"),
            C_CHAR.withName("ContrastDetectAF"),
            C_CHAR.withName("AFAreaMode"),
            C_CHAR.withName("PhaseDetectAF"),
            C_CHAR.withName("PrimaryAFPoint"),
            MemoryLayout.sequenceLayout(29, C_CHAR).withName("AFPointsUsed"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("AFImageWidth"),
            C_SHORT.withName("AFImageHeight"),
            C_SHORT.withName("AFAreaXPposition"),
            C_SHORT.withName("AFAreaYPosition"),
            C_SHORT.withName("AFAreaWidth"),
            C_SHORT.withName("AFAreaHeight"),
            C_CHAR.withName("ContrastDetectAFInFocus"),
            MemoryLayout.sequenceLayout(13, C_CHAR).withName("FlashSetting"),
            MemoryLayout.sequenceLayout(20, C_CHAR).withName("FlashType"),
            MemoryLayout.sequenceLayout(4, C_CHAR).withName("FlashExposureCompensation"),
            MemoryLayout.sequenceLayout(4, C_CHAR).withName("ExternalFlashExposureComp"),
            MemoryLayout.sequenceLayout(4, C_CHAR).withName("FlashExposureBracketValue"),
            C_CHAR.withName("FlashMode"),
            C_CHAR.withName("FlashExposureCompensation2"),
            C_CHAR.withName("FlashExposureCompensation3"),
            C_CHAR.withName("FlashExposureCompensation4"),
            C_CHAR.withName("FlashSource"),
            MemoryLayout.sequenceLayout(2, C_CHAR).withName("FlashFirmware"),
            C_CHAR.withName("ExternalFlashFlags"),
            C_CHAR.withName("FlashControlCommanderMode"),
            C_CHAR.withName("FlashOutputAndCompensation"),
            C_CHAR.withName("FlashFocalLength"),
            C_CHAR.withName("FlashGNDistance"),
            MemoryLayout.sequenceLayout(4, C_CHAR).withName("FlashGroupControlMode"),
            MemoryLayout.sequenceLayout(4, C_CHAR).withName("FlashGroupOutputAndCompensation"),
            C_CHAR.withName("FlashColorFilter"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("NEFCompression"),
            C_INT.withName("ExposureMode"),
            C_INT.withName("ExposureProgram"),
            C_INT.withName("nMEshots"),
            C_INT.withName("MEgainOn"),
            MemoryLayout.paddingLayout(32),
            MemoryLayout.sequenceLayout(4, C_DOUBLE).withName("ME_WB"),
            C_CHAR.withName("AFFineTune"),
            C_CHAR.withName("AFFineTuneIndex"),
            C_CHAR.withName("AFFineTuneAdj"),
            MemoryLayout.paddingLayout(8),
            C_INT.withName("LensDataVersion"),
            C_INT.withName("FlashInfoVersion"),
            C_INT.withName("ColorBalanceVersion"),
            C_CHAR.withName("key"),
            MemoryLayout.paddingLayout(8),
            MemoryLayout.sequenceLayout(4, C_SHORT).withName("NEFBitDepth"),
            C_SHORT.withName("HighSpeedCropFormat"),
            MemoryLayout.structLayout(
                C_SHORT.withName("cleft"),
                C_SHORT.withName("ctop"),
                C_SHORT.withName("cwidth"),
                C_SHORT.withName("cheight")
            ).withName("SensorHighSpeedCrop"),
            C_SHORT.withName("SensorWidth"),
            C_SHORT.withName("SensorHeight")
        ).withName("nikon"),
        MemoryLayout.structLayout(
            C_INT.withName("BaseISO"),
            MemoryLayout.paddingLayout(32),
            C_DOUBLE.withName("Gain"),
            MemoryLayout.sequenceLayout(8, C_CHAR).withName("Sensor"),
            MemoryLayout.sequenceLayout(64, C_CHAR).withName("SensorUnit"),
            MemoryLayout.sequenceLayout(64, C_CHAR).withName("HostBody"),
            C_INT.withName("SensorCode"),
            C_INT.withName("SensorSubCode"),
            C_INT.withName("CoatingCode"),
            C_INT.withName("uncropped"),
            MemoryLayout.sequenceLayout(32, C_CHAR).withName("CaptureSequenceInitiator"),
            MemoryLayout.sequenceLayout(64, C_CHAR).withName("SensorUnitConnector"),
            C_INT.withName("format"),
            MemoryLayout.sequenceLayout(2, C_INT).withName("nIFD_CM"),
            MemoryLayout.sequenceLayout(2, C_INT).withName("RecommendedCrop"),
            MemoryLayout.paddingLayout(32),
            MemoryLayout.sequenceLayout(4, MemoryLayout.sequenceLayout(3, C_DOUBLE)).withName("mnColorMatrix")
        ).withName("hasselblad"),
        MemoryLayout.structLayout(
            C_FLOAT.withName("ExpoMidPointShift"),
            C_SHORT.withName("DynamicRange"),
            C_SHORT.withName("FilmMode"),
            C_SHORT.withName("DynamicRangeSetting"),
            C_SHORT.withName("DevelopmentDynamicRange"),
            C_SHORT.withName("AutoDynamicRange"),
            C_SHORT.withName("DRangePriority"),
            C_SHORT.withName("DRangePriorityAuto"),
            C_SHORT.withName("DRangePriorityFixed"),
            C_FLOAT.withName("BrightnessCompensation"),
            C_SHORT.withName("FocusMode"),
            C_SHORT.withName("AFMode"),
            MemoryLayout.sequenceLayout(2, C_SHORT).withName("FocusPixel"),
            MemoryLayout.sequenceLayout(3, C_SHORT).withName("ImageStabilization"),
            C_SHORT.withName("FlashMode"),
            C_SHORT.withName("WB_Preset"),
            C_SHORT.withName("ShutterType"),
            C_SHORT.withName("ExrMode"),
            C_SHORT.withName("Macro"),
            C_INT.withName("Rating"),
            C_SHORT.withName("CropMode"),
            C_SHORT.withName("FrameRate"),
            C_SHORT.withName("FrameWidth"),
            C_SHORT.withName("FrameHeight"),
            MemoryLayout.sequenceLayout(13, C_CHAR).withName("SerialSignature"),
            MemoryLayout.sequenceLayout(5, C_CHAR).withName("RAFVersion"),
            C_SHORT.withName("RAFDataVersion"),
            C_INT.withName("isTSNERDTS"),
            C_SHORT.withName("DriveMode"),
            MemoryLayout.paddingLayout(16)
        ).withName("fuji"),
        MemoryLayout.structLayout(
            MemoryLayout.sequenceLayout(2, C_INT).withName("SensorCalibration"),
            MemoryLayout.sequenceLayout(2, C_SHORT).withName("FocusMode"),
            C_SHORT.withName("AutoFocus"),
            C_SHORT.withName("AFPoint"),
            MemoryLayout.sequenceLayout(64, C_INT).withName("AFAreas"),
            MemoryLayout.sequenceLayout(5, C_DOUBLE).withName("AFPointSelected"),
            C_SHORT.withName("AFResult"),
            MemoryLayout.sequenceLayout(5, C_SHORT).withName("DriveMode"),
            C_SHORT.withName("ColorSpace"),
            C_CHAR.withName("AFFineTune"),
            MemoryLayout.paddingLayout(8),
            MemoryLayout.sequenceLayout(3, C_SHORT).withName("AFFineTuneAdj"),
            MemoryLayout.sequenceLayout(6, C_CHAR).withName("CameraType2"),
            MemoryLayout.paddingLayout(32)
        ).withName("olympus"),
        MemoryLayout.structLayout(
            C_SHORT.withName("CameraType"),
            C_CHAR.withName("Sony0x9400_version"),
            C_CHAR.withName("Sony0x9400_ReleaseMode2"),
            C_INT.withName("Sony0x9400_SequenceImageNumber"),
            C_CHAR.withName("Sony0x9400_SequenceLength1"),
            MemoryLayout.paddingLayout(24),
            C_INT.withName("Sony0x9400_SequenceFileNumber"),
            C_CHAR.withName("Sony0x9400_SequenceLength2"),
            C_CHAR.withName("AFAreaModeSetting"),
            MemoryLayout.sequenceLayout(2, C_SHORT).withName("FlexibleSpotPosition"),
            C_CHAR.withName("AFPointSelected"),
            MemoryLayout.sequenceLayout(10, C_CHAR).withName("AFPointsUsed"),
            C_CHAR.withName("AFTracking"),
            C_CHAR.withName("AFType"),
            MemoryLayout.paddingLayout(8),
            MemoryLayout.sequenceLayout(4, C_SHORT).withName("FocusLocation"),
            C_CHAR.withName("AFMicroAdjValue"),
            C_CHAR.withName("AFMicroAdjOn"),
            C_CHAR.withName("AFMicroAdjRegisteredLenses"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("VariableLowPassFilter"),
            MemoryLayout.paddingLayout(16),
            C_INT.withName("LongExposureNoiseReduction"),
            C_SHORT.withName("HighISONoiseReduction"),
            MemoryLayout.sequenceLayout(2, C_SHORT).withName("HDR"),
            C_SHORT.withName("group2010"),
            C_SHORT.withName("real_iso_offset"),
            C_SHORT.withName("MeteringMode_offset"),
            C_SHORT.withName("ExposureProgram_offset"),
            C_SHORT.withName("ReleaseMode2_offset"),
            C_INT.withName("MinoltaCamID"),
            C_FLOAT.withName("firmware"),
            C_SHORT.withName("ImageCount3_offset"),
            MemoryLayout.paddingLayout(16),
            C_INT.withName("ImageCount3"),
            C_INT.withName("ElectronicFrontCurtainShutter"),
            C_SHORT.withName("MeteringMode2"),
            MemoryLayout.sequenceLayout(20, C_CHAR).withName("SonyDateTime"),
            MemoryLayout.paddingLayout(16),
            C_INT.withName("ShotNumberSincePowerUp"),
            C_SHORT.withName("PixelShiftGroupPrefix"),
            MemoryLayout.paddingLayout(16),
            C_INT.withName("PixelShiftGroupID"),
            C_CHAR.withName("nShotsInPixelShiftGroup"),
            C_CHAR.withName("numInPixelShiftGroup"),
            C_SHORT.withName("prd_ImageHeight"),
            C_SHORT.withName("prd_ImageWidth"),
            C_SHORT.withName("prd_RawBitDepth"),
            C_SHORT.withName("prd_StorageMethod"),
            C_SHORT.withName("prd_BayerPattern"),
            C_SHORT.withName("SonyRawFileType"),
            C_SHORT.withName("RAWFileType"),
            C_INT.withName("Quality"),
            C_SHORT.withName("FileFormat"),
            MemoryLayout.paddingLayout(16)
        ).withName("sony"),
        MemoryLayout.structLayout(
            C_SHORT.withName("BlackLevelTop"),
            C_SHORT.withName("BlackLevelBottom"),
            C_SHORT.withName("offset_left"),
            C_SHORT.withName("offset_top"),
            C_SHORT.withName("clipBlack"),
            C_SHORT.withName("clipWhite"),
            MemoryLayout.sequenceLayout(3, MemoryLayout.sequenceLayout(3, C_FLOAT)).withName("romm_camDaylight"),
            MemoryLayout.sequenceLayout(3, MemoryLayout.sequenceLayout(3, C_FLOAT)).withName("romm_camTungsten"),
            MemoryLayout.sequenceLayout(3, MemoryLayout.sequenceLayout(3, C_FLOAT)).withName("romm_camFluorescent"),
            MemoryLayout.sequenceLayout(3, MemoryLayout.sequenceLayout(3, C_FLOAT)).withName("romm_camFlash"),
            MemoryLayout.sequenceLayout(3, MemoryLayout.sequenceLayout(3, C_FLOAT)).withName("romm_camCustom"),
            MemoryLayout.sequenceLayout(3, MemoryLayout.sequenceLayout(3, C_FLOAT)).withName("romm_camAuto"),
            C_SHORT.withName("val018percent"),
            C_SHORT.withName("val100percent"),
            C_SHORT.withName("val170percent"),
            C_SHORT.withName("MakerNoteKodak8a"),
            C_FLOAT.withName("ISOCalibrationGain"),
            C_FLOAT.withName("AnalogISO")
        ).withName("kodak"),
        MemoryLayout.structLayout(
            C_SHORT.withName("Compression"),
            C_SHORT.withName("BlackLevelDim"),
            MemoryLayout.sequenceLayout(8, C_FLOAT).withName("BlackLevel"),
            C_INT.withName("Multishot"),
            C_FLOAT.withName("gamma"),
            MemoryLayout.sequenceLayout(3, C_INT).withName("HighISOMultiplier")
        ).withName("panasonic"),
        MemoryLayout.structLayout(
            C_SHORT.withName("FocusMode"),
            C_SHORT.withName("AFPointSelected"),
            C_INT.withName("AFPointsInFocus"),
            C_SHORT.withName("FocusPosition"),
            MemoryLayout.sequenceLayout(4, C_CHAR).withName("DriveMode"),
            C_SHORT.withName("AFAdjustment"),
            C_CHAR.withName("MultiExposure"),
            MemoryLayout.paddingLayout(8),
            C_SHORT.withName("Quality")
        ).withName("pentax"),
        MemoryLayout.structLayout(
            MemoryLayout.sequenceLayout(64, C_CHAR).withName("Software"),
            MemoryLayout.sequenceLayout(64, C_CHAR).withName("SystemType"),
            MemoryLayout.sequenceLayout(256, C_CHAR).withName("FirmwareString"),
            MemoryLayout.sequenceLayout(64, C_CHAR).withName("SystemModel")
        ).withName("phaseone"),
        MemoryLayout.structLayout(
            MemoryLayout.sequenceLayout(4, C_INT).withName("ImageSizeFull"),
            MemoryLayout.sequenceLayout(4, C_INT).withName("ImageSizeCrop"),
            MemoryLayout.sequenceLayout(2, C_INT).withName("ColorSpace"),
            MemoryLayout.sequenceLayout(11, C_INT).withName("key"),
            MemoryLayout.paddingLayout(32),
            C_DOUBLE.withName("DigitalGain"),
            C_INT.withName("DeviceType"),
            MemoryLayout.sequenceLayout(32, C_CHAR).withName("LensFirmware"),
            MemoryLayout.paddingLayout(32)
        ).withName("samsung"),
        MemoryLayout.structLayout(
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
        ).withName("common"),
        MemoryLayout.paddingLayout(32)
    );
    public static MemoryLayout $LAYOUT() {
        return libraw_makernotes_t.$struct$LAYOUT;
    }
    public static MemorySegment canon$slice(MemorySegment seg) {
        return seg.asSlice(0, 664);
    }
    public static MemorySegment nikon$slice(MemorySegment seg) {
        return seg.asSlice(664, 240);
    }
    public static MemorySegment hasselblad$slice(MemorySegment seg) {
        return seg.asSlice(904, 384);
    }
    public static MemorySegment fuji$slice(MemorySegment seg) {
        return seg.asSlice(1288, 88);
    }
    public static MemorySegment olympus$slice(MemorySegment seg) {
        return seg.asSlice(1376, 344);
    }
    public static MemorySegment sony$slice(MemorySegment seg) {
        return seg.asSlice(1720, 152);
    }
    public static MemorySegment kodak$slice(MemorySegment seg) {
        return seg.asSlice(1872, 244);
    }
    public static MemorySegment panasonic$slice(MemorySegment seg) {
        return seg.asSlice(2116, 56);
    }
    public static MemorySegment pentax$slice(MemorySegment seg) {
        return seg.asSlice(2172, 20);
    }
    public static MemorySegment phaseone$slice(MemorySegment seg) {
        return seg.asSlice(2192, 448);
    }
    public static MemorySegment samsung$slice(MemorySegment seg) {
        return seg.asSlice(2640, 136);
    }
    public static MemorySegment common$slice(MemorySegment seg) {
        return seg.asSlice(2776, 196);
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

