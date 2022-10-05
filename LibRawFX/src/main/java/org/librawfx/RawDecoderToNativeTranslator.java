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
        if (os.toUpperCase().contains("WIN")) {
            if (settings.getWhiteBalance().equalsIgnoreCase("CAMERA")) {
                org.libraw.win.libraw_output_params_t.use_camera_wb$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.use_camera_wb$set(parameterSegment, 0);
            }
            if (settings.isAutoWhitebalance()) {
                org.libraw.win.libraw_output_params_t.use_auto_wb$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.use_auto_wb$set(parameterSegment, 0);
            }
            if (settings.isOutput_tiff()) {
                org.libraw.win.libraw_output_params_t.output_tiff$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.output_tiff$set(parameterSegment, 0);
            }
            if (settings.isHalfSizeOutput()) {
                org.libraw.win.libraw_output_params_t.half_size$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.half_size$set(parameterSegment, 0);
            }
            org.libraw.win.libraw_output_params_t.user_qual$set(parameterSegment, settings.getRAWQuality());
            if (settings.isAutoBrightness()) {
                org.libraw.win.libraw_output_params_t.no_auto_bright$set(parameterSegment, 0);
                org.libraw.win.libraw_output_params_t.auto_bright_thr$set(parameterSegment, settings.getAutoBrightnessThreashold());
            } else {
                org.libraw.win.libraw_output_params_t.no_auto_bright$set(parameterSegment, 1);
            }
            org.libraw.win.libraw_output_params_t.bright$set(parameterSegment, settings.getBrightNess());
            if (settings.isEnableExposureCorrection()) {
                org.libraw.win.libraw_output_params_t.exp_correc$set(parameterSegment, settings.getExposureCorrection());
                org.libraw.win.libraw_output_params_t.exp_shift$set(parameterSegment, settings.getExposureShift());
                org.libraw.win.libraw_output_params_t.exp_preser$set(parameterSegment, settings.getExpoCorrectionHighlight());
            }
            org.libraw.win.libraw_output_params_t.fbdd_noiserd$set(parameterSegment, settings.getNoiseReduction());
            if (settings.isFixColorsHighlights()) {
                org.libraw.win.libraw_output_params_t.highlight$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.highlight$set(parameterSegment, 0);
            }
            if (settings.isSixteenBitsImage()) {
                org.libraw.win.libraw_output_params_t.output_bps$set(parameterSegment, 16);
            } else {
                org.libraw.win.libraw_output_params_t.output_bps$set(parameterSegment, 8);
            }
            org.libraw.win.libraw_output_params_t.bright$set(parameterSegment, settings.getBrightNess());
            org.libraw.win.libraw_output_params_t.output_color$set(parameterSegment, settings.getOutputColorSpace());
            org.libraw.win.libraw_output_params_t.user_sat$set(parameterSegment, settings.getSaturation());
            org.libraw.win.libraw_output_params_t.med_passes$set(parameterSegment, settings.getMedianFilterPasses());
            if (settings.isEnableBlackPoint()) {
                org.libraw.win.libraw_output_params_t.user_black$set(parameterSegment, settings.getBlackPoint());
            }
        } else {
            if (settings.getWhiteBalance().equalsIgnoreCase("CAMERA")) {
                org.libraw.linuxosx.libraw_output_params_t.use_camera_wb$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.use_camera_wb$set(parameterSegment, 0);
            }
            if (settings.isAutoWhitebalance()) {
                org.libraw.linuxosx.libraw_output_params_t.use_auto_wb$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.use_auto_wb$set(parameterSegment, 0);
            }
            if (settings.isOutput_tiff()) {
                org.libraw.linuxosx.libraw_output_params_t.output_tiff$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.output_tiff$set(parameterSegment, 0);
            }
            if (settings.isHalfSizeOutput()) {
                org.libraw.linuxosx.libraw_output_params_t.half_size$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.half_size$set(parameterSegment, 0);
            }
            org.libraw.linuxosx.libraw_output_params_t.user_qual$set(parameterSegment, settings.getRAWQuality());
            if (settings.isAutoBrightness()) {
                org.libraw.linuxosx.libraw_output_params_t.no_auto_bright$set(parameterSegment, 0);
                org.libraw.linuxosx.libraw_output_params_t.auto_bright_thr$set(parameterSegment, settings.getAutoBrightnessThreashold());
            } else {
                org.libraw.linuxosx.libraw_output_params_t.no_auto_bright$set(parameterSegment, 1);
            }
            org.libraw.linuxosx.libraw_output_params_t.bright$set(parameterSegment, settings.getBrightNess());
            if (settings.isEnableExposureCorrection()) {
                org.libraw.linuxosx.libraw_output_params_t.exp_correc$set(parameterSegment, settings.getExposureCorrection());
                org.libraw.linuxosx.libraw_output_params_t.exp_shift$set(parameterSegment, settings.getExposureShift());
                org.libraw.linuxosx.libraw_output_params_t.exp_preser$set(parameterSegment, settings.getExpoCorrectionHighlight());
            }
            org.libraw.linuxosx.libraw_output_params_t.fbdd_noiserd$set(parameterSegment, settings.getNoiseReduction());
            if (settings.isFixColorsHighlights()) {
                org.libraw.linuxosx.libraw_output_params_t.highlight$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.highlight$set(parameterSegment, 0);
            }
            if (settings.isSixteenBitsImage()) {
                org.libraw.linuxosx.libraw_output_params_t.output_bps$set(parameterSegment, 16);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.output_bps$set(parameterSegment, 8);
            }
            org.libraw.linuxosx.libraw_output_params_t.bright$set(parameterSegment, settings.getBrightNess());
            org.libraw.linuxosx.libraw_output_params_t.output_color$set(parameterSegment, settings.getOutputColorSpace());
            org.libraw.linuxosx.libraw_output_params_t.user_sat$set(parameterSegment, settings.getSaturation());
            org.libraw.linuxosx.libraw_output_params_t.med_passes$set(parameterSegment, settings.getMedianFilterPasses());
            if (settings.isEnableBlackPoint()) {
                org.libraw.linuxosx.libraw_output_params_t.user_black$set(parameterSegment, settings.getBlackPoint());
            }
        }
    }

}
