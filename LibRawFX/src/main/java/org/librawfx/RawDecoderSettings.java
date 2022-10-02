/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawfx;

import java.lang.foreign.*;

/**
 *
 * @author cleme
 */
public class RawDecoderSettings {

    private boolean autoBrightness = true;
    private float autoBrightnessThreashold = 0.0f;
    private float brightNess = 1.0f;
    private float coolScanNEFGamma = 1.0f;
    private int exposureCorrection = 0;
    private float exposureShift = 1.0f;
    private int noiseReduction = 0;

    private boolean fixColorsHighlights = false;
    private boolean autoWhitebalance = false;
    private String whiteBalance = "CAMERA";
    private boolean sixteenBitsImage = false;
    private double brightness = 1.0;
    private String RAWQuality = "BILINEAR";
    private String inputColorSpace = "NOINPUTCS";
    private String outputColorSpace = "SRGB";
    private boolean RGBInterpolate4Colors = false;
    private boolean DontStretchPixels = false;
    private int unclipColors = 0;
    private int customWhiteBalance = 6500;
    private double customWhiteBalanceGreen = 1.0;
    private int medianFilterPasses = 0;

    private boolean halfSizeColorImage = false;

    private boolean enableBlackPoint = false;
    private int blackPoint = 0;

    private boolean enableWhitePoint = false;
    private int whitePoint = 0;

    private String NRType = "NONR";
    private int NRThreshold = 0;

    private String inputProfile = "";
    private String outputProfile = "";

    private String deadPixelMap = "";

    private String whiteBalanceArea = "";

    //-- Extended demosaicing settings ----------------------------------------------------------
    private int dcbIterations = -1;
    private boolean dcbEnhanceFl = false;
    private boolean expoCorrection = false;
    private double expoCorrectionShift = 1.0;
    private double expoCorrectionHighlight = 0.0;

    private MemorySegment parameterSegment;
    private String os;

    public RawDecoderSettings(MemorySegment parameterSegment, String os) {
        this.parameterSegment = parameterSegment;
        this.os = os;
        // setting default parameters
        if (os.toUpperCase().contains("WIN")) {
            org.libraw.win.libraw_output_params_t.use_camera_wb$set(parameterSegment, 0);
            org.libraw.win.libraw_output_params_t.use_auto_wb$set(parameterSegment, 0);
            org.libraw.win.libraw_output_params_t.output_tiff$set(parameterSegment, 0);
            org.libraw.win.libraw_output_params_t.half_size$set(parameterSegment, 0);
            org.libraw.win.libraw_output_params_t.user_qual$set(parameterSegment, 0);
        } else {
            org.libraw.linuxosx.libraw_output_params_t.use_camera_wb$set(parameterSegment, 0);
            org.libraw.linuxosx.libraw_output_params_t.use_auto_wb$set(parameterSegment, 0);
            org.libraw.linuxosx.libraw_output_params_t.output_tiff$set(parameterSegment, 0);
            org.libraw.linuxosx.libraw_output_params_t.half_size$set(parameterSegment, 0);
            org.libraw.linuxosx.libraw_output_params_t.user_qual$set(parameterSegment, 0);
        }
    }

    public boolean isFixColorsHighlights() {
        return fixColorsHighlights;
    }

    public void setFixColorsHighlights(boolean fixColorsHighlights) {
        if (os.toUpperCase().contains("WIN")) {
            if (fixColorsHighlights == true) {
                org.libraw.win.libraw_output_params_t.highlight$set(parameterSegment, 1);
            } else {
                org.libraw.win.libraw_output_params_t.highlight$set(parameterSegment, 0);
            }
        } else {
            if (fixColorsHighlights == true) {
                org.libraw.linuxosx.libraw_output_params_t.highlight$set(parameterSegment, 1);
            } else {
                org.libraw.linuxosx.libraw_output_params_t.highlight$set(parameterSegment, 0);
            }
        }
        this.fixColorsHighlights = fixColorsHighlights;
    }

    public boolean isAutoBrightness() {
        return autoBrightness;
    }

    public void setAutoBrightness(boolean autoBrightness) {
        this.autoBrightness = autoBrightness;
    }

    public boolean isSixteenBitsImage() {
        return sixteenBitsImage;
    }

    public void setSixteenBitsImage(boolean sixteenBitsImage) {
        this.sixteenBitsImage = sixteenBitsImage;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public String getRAWQuality() {
        return RAWQuality;
    }

    public void setRAWQuality(String RAWQuality) {
        this.RAWQuality = RAWQuality;
    }

    public String getInputColorSpace() {
        return inputColorSpace;
    }

    public void setInputColorSpace(String inputColorSpace) {
        this.inputColorSpace = inputColorSpace;
    }

    public String getOutputColorSpace() {
        return outputColorSpace;
    }

    public void setOutputColorSpace(String outputColorSpace) {
        this.outputColorSpace = outputColorSpace;
    }

    public boolean isRGBInterpolate4Colors() {
        return RGBInterpolate4Colors;
    }

    public void setRGBInterpolate4Colors(boolean RGBInterpolate4Colors) {
        this.RGBInterpolate4Colors = RGBInterpolate4Colors;
    }

    public boolean isDontStretchPixels() {
        return DontStretchPixels;
    }

    public void setDontStretchPixels(boolean DontStretchPixels) {
        this.DontStretchPixels = DontStretchPixels;
    }

    public int getUnclipColors() {
        return unclipColors;
    }

    public void setUnclipColors(int unclipColors) {
        this.unclipColors = unclipColors;
    }

    public String getWhiteBalance() {
        return whiteBalance;
    }

    public void setWhiteBalance(String whiteBalance) {
        this.whiteBalance = whiteBalance;
    }

    public int getCustomWhiteBalance() {
        return customWhiteBalance;
    }

    public void setCustomWhiteBalance(int customWhiteBalance) {
        this.customWhiteBalance = customWhiteBalance;
    }

    public double getCustomWhiteBalanceGreen() {
        return customWhiteBalanceGreen;
    }

    public void setCustomWhiteBalanceGreen(double customWhiteBalanceGreen) {
        this.customWhiteBalanceGreen = customWhiteBalanceGreen;
    }

    public int getMedianFilterPasses() {
        return medianFilterPasses;
    }

    public void setMedianFilterPasses(int medianFilterPasses) {
        this.medianFilterPasses = medianFilterPasses;
    }

    public boolean isHalfSizeColorImage() {
        return halfSizeColorImage;
    }

    public void setHalfSizeColorImage(boolean halfSizeColorImage) {
        this.halfSizeColorImage = halfSizeColorImage;
    }

    public boolean isEnableBlackPoint() {
        return enableBlackPoint;
    }

    public void setEnableBlackPoint(boolean enableBlackPoint) {
        this.enableBlackPoint = enableBlackPoint;
    }

    public int getBlackPoint() {
        return blackPoint;
    }

    public void setBlackPoint(int blackPoint) {
        this.blackPoint = blackPoint;
    }

    public boolean isEnableWhitePoint() {
        return enableWhitePoint;
    }

    public void setEnableWhitePoint(boolean enableWhitePoint) {
        this.enableWhitePoint = enableWhitePoint;
    }

    public int getWhitePoint() {
        return whitePoint;
    }

    public void setWhitePoint(int whitePoint) {
        this.whitePoint = whitePoint;
    }

    public String getNRType() {
        return NRType;
    }

    public void setNRType(String NRType) {
        this.NRType = NRType;
    }

    public int getNRThreshold() {
        return NRThreshold;
    }

    public void setNRThreshold(int NRThreshold) {
        this.NRThreshold = NRThreshold;
    }

    public String getInputProfile() {
        return inputProfile;
    }

    public void setInputProfile(String inputProfile) {
        this.inputProfile = inputProfile;
    }

    public String getOutputProfile() {
        return outputProfile;
    }

    public void setOutputProfile(String outputProfile) {
        this.outputProfile = outputProfile;
    }

    public String getDeadPixelMap() {
        return deadPixelMap;
    }

    public void setDeadPixelMap(String deadPixelMap) {
        this.deadPixelMap = deadPixelMap;
    }

    public String getWhiteBalanceArea() {
        return whiteBalanceArea;
    }

    public void setWhiteBalanceArea(String whiteBalanceArea) {
        this.whiteBalanceArea = whiteBalanceArea;
    }

    public int getDcbIterations() {
        return dcbIterations;
    }

    public void setDcbIterations(int dcbIterations) {
        this.dcbIterations = dcbIterations;
    }

    public boolean isDcbEnhanceFl() {
        return dcbEnhanceFl;
    }

    public void setDcbEnhanceFl(boolean dcbEnhanceFl) {
        this.dcbEnhanceFl = dcbEnhanceFl;
    }

    public boolean isExpoCorrection() {
        return expoCorrection;
    }

    public void setExpoCorrection(boolean expoCorrection) {
        this.expoCorrection = expoCorrection;
    }

    public double getExpoCorrectionShift() {
        return expoCorrectionShift;
    }

    public void setExpoCorrectionShift(double expoCorrectionShift) {
        this.expoCorrectionShift = expoCorrectionShift;
    }

    public double getExpoCorrectionHighlight() {
        return expoCorrectionHighlight;
    }

    public void setExpoCorrectionHighlight(double expoCorrectionHighlight) {
        this.expoCorrectionHighlight = expoCorrectionHighlight;
    }

}
