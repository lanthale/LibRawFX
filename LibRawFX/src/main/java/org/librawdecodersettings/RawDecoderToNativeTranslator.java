package org.librawdecodersettings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.libraw.nativ.libraw_output_params_t;
import org.libraw.nativ.libraw_raw_unpack_params_t;

/**
 * Writes a {@link RawDecoderSettings} object into the native
 * libraw_output_params_t of a libraw handle.
 *
 * Must be called after libraw_unpack() and before libraw_dcraw_process().
 * Every decode uses a fresh libraw handle, so parameters that are not written
 * here keep the LibRaw defaults.
 *
 * Convention for the "optional" settings: an unset value (-1, or the enable
 * flag is false) leaves the LibRaw default untouched.
 */
public class RawDecoderToNativeTranslator {

    private final RawDecoderSettings settings;
    private final String os;

    public RawDecoderToNativeTranslator(RawDecoderSettings settings, String os) {
        this.settings = settings;
        this.os = os;
    }

    /**
     * Variant without arena. Only usable if no file path option (output
     * profile, camera profile, bad pixels, dark frame) is set.
     */
    public void translate(MemorySegment p) {
        translate(p, null);
    }

    /**
     * @param p the params slice (libraw_data_t.params(handle))
     * @param arena arena that stays open until libraw_dcraw_process() has
     * finished; needed for the string options (file paths)
     */
    public void translate(MemorySegment p, Arena arena) {
        // ---- white balance ----------------------------------------------------
        // LibRaw: mixing user_mul with camera or auto white balance gives an unspecified
        // precedence (the camera/auto branch overwrites the multipliers in scale_colors).
        // So custom multipliers switch the other two off.
        boolean customWb = settings.isUseUserMultipliers() && hasMultipliers(settings.getUserMultipliers());
        libraw_output_params_t.use_camera_wb(p,
                !customWb && "CAMERA".equalsIgnoreCase(settings.getWhiteBalance()) ? 1 : 0);
        libraw_output_params_t.use_auto_wb(p, !customWb && settings.isAutoWhitebalance() ? 1 : 0);
        if (customWb) {
            fillFloats(libraw_output_params_t.user_mul(p), settings.getUserMultipliers());
        }
        if (settings.isUseGreyBox()) {                             // area for auto WB: x, y, w, h
            fillInts(libraw_output_params_t.greybox(p), settings.getGreyBox());
        }

        // ---- demosaic / quality -------------------------------------------------
        libraw_output_params_t.user_qual(p, settings.getRAWQuality());
        libraw_output_params_t.half_size(p, settings.isHalfSizeOutput() ? 1 : 0);
        libraw_output_params_t.four_color_rgb(p, settings.isRGBInterpolate4Colors() ? 1 : 0);
        libraw_output_params_t.green_matching(p, settings.isGreenMatching() ? 1 : 0);
        libraw_output_params_t.med_passes(p, settings.getMedianFilterPasses());
        if (settings.getRAWQuality() == 4) {                       // DCB only
            if (settings.getDcbIterations() >= 0) {                // -1 = LibRaw default
                libraw_output_params_t.dcb_iterations(p, settings.getDcbIterations());
            }
            libraw_output_params_t.dcb_enhance_fl(p, settings.isDcbEnhanceFl() ? 1 : 0);
        }
        libraw_output_params_t.fbdd_noiserd(p, settings.getNoiseReduction());
        if (settings.getNRThreshold() > 0) {                       // wavelet denoise
            libraw_output_params_t.threshold(p, (float) settings.getNRThreshold());
        }
        if (settings.getAberRed() != 1.0 || settings.getAberBlue() != 1.0) {   // chromatic aberration
            MemorySegment aber = libraw_output_params_t.aber(p);   // double[4]: red, green, blue, green2
            aber.setAtIndex(ValueLayout.JAVA_DOUBLE, 0, settings.getAberRed());
            aber.setAtIndex(ValueLayout.JAVA_DOUBLE, 2, settings.getAberBlue());
        }

        // ---- brightness / exposure / highlights ----------------------------------
        if (settings.isAutoBrightness()) {
            libraw_output_params_t.no_auto_bright(p, 0);
            libraw_output_params_t.auto_bright_thr(p, settings.getAutoBrightnessThreashold());
        } else {
            libraw_output_params_t.no_auto_bright(p, 1);
        }
        libraw_output_params_t.bright(p, settings.getBrightNess());
        if (settings.isEnableExposureCorrection()) {
            libraw_output_params_t.exp_correc(p, settings.getExposureCorrection());
            libraw_output_params_t.exp_shift(p, settings.getExposureShift());
            libraw_output_params_t.exp_preser(p, settings.getExpoCorrectionHighlight());
        }
        int highlight = settings.getUnclipColors();                // 0 clip, 1 unclip, 2 blend, 3-9 rebuild
        if (highlight <= 0) {
            highlight = settings.isFixColorsHighlights() ? 1 : 0;  // legacy switch
        }
        libraw_output_params_t.highlight(p, Math.min(highlight, 9));
        libraw_output_params_t.adjust_maximum_thr(p, settings.getAdjustMaximumThreshold());
        if (settings.isUseCustomGamma()) {                         // gamm[0] = 1/gamma, gamm[1] = toe slope
            MemorySegment gamm = libraw_output_params_t.gamm(p);
            gamm.setAtIndex(ValueLayout.JAVA_DOUBLE, 0, settings.getGammaPower());
            gamm.setAtIndex(ValueLayout.JAVA_DOUBLE, 1, settings.getGammaToeSlope());
        }

        // ---- levels --------------------------------------------------------------
        libraw_output_params_t.user_sat(p, settings.getSaturation());          // saturation LEVEL (dcraw -S), not colour saturation
        if (settings.isEnableBlackPoint()) {
            libraw_output_params_t.user_black(p, settings.getBlackPoint());
        }
        if (settings.isUseChannelBlack()) {
            fillInts(libraw_output_params_t.user_cblack(p), settings.getChannelBlack());
        }

        // ---- colour --------------------------------------------------------------
        libraw_output_params_t.output_color(p, settings.getOutputColorSpace());
        if (settings.getUseCameraMatrix() >= 0) {                  // 0 off, 1 default, 3 always
            libraw_output_params_t.use_camera_matrix(p, settings.getUseCameraMatrix());
        }

        // ---- geometry / frames ----------------------------------------------------
        if (settings.getUserFlip() >= 0) {                         // -1 = orientation from the file
            libraw_output_params_t.user_flip(p, settings.getUserFlip());
        }
        libraw_output_params_t.use_fuji_rotate(p, settings.isDontStretchPixels() ? 0 : 1);
        if (settings.isUseCropBox()) {                             // x, y, w, h
            fillInts(libraw_output_params_t.cropbox(p), settings.getCropBox());
        }

        // ---- output format -------------------------------------------------------
        // The effective depth is forced afterwards by LibrawImage (forceOutputBps).
        libraw_output_params_t.output_bps(p, settings.isSixteenBitsImage() ? 16 : 8);
        libraw_output_params_t.output_tiff(p, settings.isOutput_tiff() ? 1 : 0);   // only used by dcraw_ppm_tiff_writer, no effect on make_mem_image


        // ---- file paths (need an arena that outlives dcraw_process) ----------------
        setPath(p, arena, settings.getOutputProfilePath(), 0);
        setPath(p, arena, settings.getCameraProfilePath(), 1);
        setPath(p, arena, settings.getBadPixelsPath(), 2);
        setPath(p, arena, settings.getDarkFramePath(), 3);
    }

    /**
     * Forces the output bit depth (8 or 16). The depth set in the profile is
     * ignored: LibrawImage decides per API (8 bit for images, 16 bit for editing).
     */
    public void forceOutputBps(MemorySegment p, int bps) {
        libraw_output_params_t.output_bps(p, bps);
    }

    /**
     * Linear output for editing: gamma 1.0, no auto brightness, brightness 1.0,
     * sRGB primaries (output_color 1, no output ICC profile). The editing
     * pipeline assumes sRGB primaries, so the colour space settings of the
     * profile are ignored here. Call after {@link #translate}.
     */
    public void forceLinear(MemorySegment p) {
        MemorySegment gamm = libraw_output_params_t.gamm(p);
        gamm.setAtIndex(ValueLayout.JAVA_DOUBLE, 0, 1.0);
        gamm.setAtIndex(ValueLayout.JAVA_DOUBLE, 1, 1.0);
        libraw_output_params_t.no_auto_bright(p, 1);
        libraw_output_params_t.bright(p, 1.0f);
        libraw_output_params_t.output_color(p, 1);
        libraw_output_params_t.output_profile(p, MemorySegment.NULL);
    }

    /**
     * Forces half_size, used for previews. Call after {@link #translate}.
     */
    public void forceHalfSize(MemorySegment p) {
        libraw_output_params_t.half_size(p, 1);
    }

    /**
     * Decode-time options. Since LibRaw 0.20 they live in imgdata.rawparams
     * (libraw_raw_unpack_params_t) and no longer in the output params. Call
     * this after libraw_open_*() and BEFORE libraw_unpack().
     *
     * @param rawParams libraw_data_t.rawparams(handle)
     */
    public void translateDecodeParams(MemorySegment rawParams) {
        if (settings.getShotSelect() > 0) {                        // multi shot files
            libraw_raw_unpack_params_t.shot_select(rawParams, settings.getShotSelect());
        }
        if (settings.getCoolScanNEFGamma() != 1.0f) {
            libraw_raw_unpack_params_t.coolscan_nef_gamma(rawParams, settings.getCoolScanNEFGamma());
        }
        if (settings.getRawProcessingOptions() != 0) {             // LIBRAW_PROCESSING_* bit mask, OR-ed with the defaults
            int current = libraw_raw_unpack_params_t.options(rawParams);
            libraw_raw_unpack_params_t.options(rawParams, current | settings.getRawProcessingOptions());
        }
    }

    private static void setPath(MemorySegment p, Arena arena, String path, int which) {
        if (path == null || path.isBlank()) {
            return;
        }
        if (arena == null) {
            throw new IllegalArgumentException("An Arena is required for file path options");
        }
        MemorySegment str = arena.allocateFrom(path);
        switch (which) {
            case 0:
                libraw_output_params_t.output_profile(p, str);
                break;
            case 1:
                libraw_output_params_t.camera_profile(p, str);
                break;
            case 2:
                libraw_output_params_t.bad_pixels(p, str);
                break;
            default:
                libraw_output_params_t.dark_frame(p, str);
                break;
        }
    }

    private static boolean hasMultipliers(float[] m) {
        for (float v : m) {
            if (v > 0f) {
                return true;
            }
        }
        return false;
    }

    private static void fillFloats(MemorySegment target, float[] values) {
        for (int i = 0; i < values.length; i++) {
            target.setAtIndex(ValueLayout.JAVA_FLOAT, i, values[i]);
        }
    }

    private static void fillInts(MemorySegment target, int[] values) {
        for (int i = 0; i < values.length; i++) {
            target.setAtIndex(ValueLayout.JAVA_INT, i, values[i]);
        }
    }
}