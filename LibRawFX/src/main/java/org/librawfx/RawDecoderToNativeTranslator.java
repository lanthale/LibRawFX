/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawfx;

import java.lang.foreign.*;

/**
 *
 * @author selfemp
 */
public class RawDecoderToNativeTranslator {

    private final RawDecoderSettings settings;
    private String os;

    public RawDecoderToNativeTranslator(RawDecoderSettings settings, String os) {
        this.settings = settings;
        this.os = os;
    }

    public void translate(MemorySegment parameterSegment) {
        if (settings.getWhiteBalance().equalsIgnoreCase("CAMERA")) {
            org.libraw.nativ.libraw_output_params_t.use_camera_wb(parameterSegment, 1);
        } else {
            org.libraw.nativ.libraw_output_params_t.use_camera_wb(parameterSegment, 0);
        }
        if (settings.isAutoWhitebalance()) {
            org.libraw.nativ.libraw_output_params_t.use_auto_wb(parameterSegment, 1);
        } else {
            org.libraw.nativ.libraw_output_params_t.use_auto_wb(parameterSegment, 0);
        }
        if (settings.isOutput_tiff()) {
            org.libraw.nativ.libraw_output_params_t.output_tiff(parameterSegment, 1);
        } else {
            org.libraw.nativ.libraw_output_params_t.output_tiff(parameterSegment, 0);
        }
        if (settings.isHalfSizeOutput()) {
            org.libraw.nativ.libraw_output_params_t.half_size(parameterSegment, 1);
        } else {
            org.libraw.nativ.libraw_output_params_t.half_size(parameterSegment, 0);
        }
        org.libraw.nativ.libraw_output_params_t.user_qual(parameterSegment, settings.getRAWQuality());
        if (settings.isAutoBrightness()) {
            org.libraw.nativ.libraw_output_params_t.no_auto_bright(parameterSegment, 0);
            org.libraw.nativ.libraw_output_params_t.auto_bright_thr(parameterSegment, settings.getAutoBrightnessThreashold());
        } else {
            org.libraw.nativ.libraw_output_params_t.no_auto_bright(parameterSegment, 1);
        }
        org.libraw.nativ.libraw_output_params_t.bright(parameterSegment, settings.getBrightNess());
        if (settings.isEnableExposureCorrection()) {
            org.libraw.nativ.libraw_output_params_t.exp_correc(parameterSegment, settings.getExposureCorrection());
            org.libraw.nativ.libraw_output_params_t.exp_shift(parameterSegment, settings.getExposureShift());
            org.libraw.nativ.libraw_output_params_t.exp_preser(parameterSegment, settings.getExpoCorrectionHighlight());
        }
        org.libraw.nativ.libraw_output_params_t.fbdd_noiserd(parameterSegment, settings.getNoiseReduction());
        if (settings.isFixColorsHighlights()) {
            org.libraw.nativ.libraw_output_params_t.highlight(parameterSegment, 1);
        } else {
            org.libraw.nativ.libraw_output_params_t.highlight(parameterSegment, 0);
        }
        if (settings.isSixteenBitsImage()) {
            org.libraw.nativ.libraw_output_params_t.output_bps(parameterSegment, 16);
        } else {
            org.libraw.nativ.libraw_output_params_t.output_bps(parameterSegment, 8);
        }
        org.libraw.nativ.libraw_output_params_t.bright(parameterSegment, settings.getBrightNess());
        org.libraw.nativ.libraw_output_params_t.output_color(parameterSegment, settings.getOutputColorSpace());
        org.libraw.nativ.libraw_output_params_t.user_sat(parameterSegment, settings.getSaturation());
        org.libraw.nativ.libraw_output_params_t.med_passes(parameterSegment, settings.getMedianFilterPasses());
        if (settings.isEnableBlackPoint()) {
            org.libraw.nativ.libraw_output_params_t.user_black(parameterSegment, settings.getBlackPoint());
        }
    }

}
