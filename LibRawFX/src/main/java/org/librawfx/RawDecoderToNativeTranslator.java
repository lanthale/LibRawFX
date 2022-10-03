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
            if (settings.isCustomQuality()) {
                org.libraw.win.libraw_output_params_t.user_qual$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.user_qual$set(parameterSegment, 0);
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
            if (settings.isCustomQuality()) {
                org.libraw.linuxosx.libraw_output_params_t.user_qual$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.user_qual$set(parameterSegment, 0);
            }
        }
    }

}
