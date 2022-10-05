/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawfx;

/**
 *
 * @author cleme
 */
public class RawDecoderSettings {

    private String whiteBalance = "CUSTOM"; //CAMERA
    private boolean autoWhitebalance = false;
    private boolean output_tiff = false;
    private boolean halfSizeOutput = false;    
    private int RAWQuality = 0;//0 - linear interpolation, 1 - VNG interpolation, 2 - PPG interpolation, 3 - AHD interpolation, 4 - DCB interpolation, 11 - DHT intepolation, 12 - Modified AHD intepolation (by Anton Petrusevich)
    private boolean autoBrightness = false;
    private float autoBrightnessThreashold = 0.0f;
    private float brightnes = 1.0f;
    private float coolScanNEFGamma = 1.0f;
    private boolean enableExposureCorrection = false;
    private int exposureCorrection = 0;
    private float exposureShift = 1.0f;
    private float expoCorrectionHighlight = 0.0f;
    private int noiseReduction = 0;

    private boolean fixColorsHighlights = false;        
    private boolean sixteenBitsImage = false;
    private double brightness = 1.0;  
    private int saturation = 0;
    private int outputColorSpace = 1; //[0-6] Output colorspace (raw, sRGB, Adobe, Wide, ProPhoto, XYZ, ACES)
    private boolean RGBInterpolate4Colors = false;
    private boolean DontStretchPixels = false;
    private int unclipColors = 0;
    //private int customWhiteBalance = 6500;
    //private double customWhiteBalanceGreen = 1.0;
    private int medianFilterPasses = 0;
    

    private boolean enableBlackPoint = false;
    private int blackPoint = 0;

    //private boolean enableWhitePoint = false;
    //private int whitePoint = 0;

    private String NRType = "NONR";
    private int NRThreshold = 0;

    //private String inputProfile = "";
    //private String outputProfile = "";

    //private String deadPixelMap = "";

    //private String whiteBalanceArea = "";

    //-- Extended demosaicing settings ----------------------------------------------------------
    private int dcbIterations = -1;
    private boolean dcbEnhanceFl = false;            
    

    public RawDecoderSettings() {
    }
    
    public boolean isFixColorsHighlights() {
        return fixColorsHighlights;
    }

    public void setFixColorsHighlights(boolean fixColorsHighlights) {        
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

    public double getBrightnes() {
        return brightness;
    }

    public void setBrightnes(double brightness) {
        this.brightness = brightness;
    }

    public int getRAWQuality() {
        return RAWQuality;
    }

    public void setRAWQuality(int RAWQuality) {
        this.RAWQuality = RAWQuality;
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

    public int getMedianFilterPasses() {
        return medianFilterPasses;
    }

    public void setMedianFilterPasses(int medianFilterPasses) {
        this.medianFilterPasses = medianFilterPasses;
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

    public boolean isEnableExposureCorrection() {
        return enableExposureCorrection;
    }

    public void setEnableExposureCorrection(boolean enableExposureCorrection) {
        this.enableExposureCorrection = enableExposureCorrection;
    }
        

    public float getExpoCorrectionShift() {
        return exposureShift;
    }

    public void setExpoCorrectionShift(float expoCorrectionShift) {
        this.exposureShift = expoCorrectionShift;
    }

    public float getExpoCorrectionHighlight() {
        return expoCorrectionHighlight;
    }

    public void setExpoCorrectionHighlight(float expoCorrectionHighlight) {
        this.expoCorrectionHighlight = expoCorrectionHighlight;
    }
    
    public boolean isAutoWhitebalance() {
        return autoWhitebalance;
    }

    public void setAutoWhitebalance(boolean autoWhitebalance) {
        this.autoWhitebalance = autoWhitebalance;
    }

    public boolean isOutput_tiff() {
        return output_tiff;
    }

    public void setOutput_tiff(boolean output_tiff) {
        this.output_tiff = output_tiff;
    }

    public boolean isHalfSizeOutput() {
        return halfSizeOutput;
    }

    public void setHalfSizeOutput(boolean halfSizeOutput) {
        this.halfSizeOutput = halfSizeOutput;
    }

    public float getAutoBrightnessThreashold() {
        return autoBrightnessThreashold;
    }

    public void setAutoBrightnessThreashold(float autoBrightnessThreashold) {
        this.autoBrightnessThreashold = autoBrightnessThreashold;
    }

    public float getBrightNess() {
        return brightnes;
    }

    public void setBrightNess(float brightNess) {
        this.brightnes = brightNess;
    }

    public float getCoolScanNEFGamma() {
        return coolScanNEFGamma;
    }

    public void setCoolScanNEFGamma(float coolScanNEFGamma) {
        this.coolScanNEFGamma = coolScanNEFGamma;
    }

    public int getExposureCorrection() {
        return exposureCorrection;
    }

    public void setExposureCorrection(int exposureCorrection) {
        this.exposureCorrection = exposureCorrection;
    }

    public float getExposureShift() {
        return exposureShift;
    }

    public void setExposureShift(float exposureShift) {
        this.exposureShift = exposureShift;
    }

    public int getNoiseReduction() {
        return noiseReduction;
    }

    public void setNoiseReduction(int noiseReduction) {
        this.noiseReduction = noiseReduction;
    }

    public void setOutputColorSpace(int outputColorSpace) {
        this.outputColorSpace = outputColorSpace;
    }

    public int getOutputColorSpace() {
        return outputColorSpace;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    @Override
    public String toString() {
        return "RawDecoderSettings{" + "whiteBalance=" + whiteBalance + ", autoWhitebalance=" + autoWhitebalance + ", output_tiff=" + output_tiff + ", halfSizeOutput=" + halfSizeOutput + ", RAWQuality=" + RAWQuality + ", autoBrightness=" + autoBrightness + ", autoBrightnessThreashold=" + autoBrightnessThreashold + ", brightNess=" + brightnes + ", coolScanNEFGamma=" + coolScanNEFGamma + ", enableExposureCorrection=" + enableExposureCorrection + ", exposureCorrection=" + exposureCorrection + ", exposureShift=" + exposureShift + ", expoCorrectionHighlight=" + expoCorrectionHighlight + ", noiseReduction=" + noiseReduction + ", fixColorsHighlights=" + fixColorsHighlights + ", sixteenBitsImage=" + sixteenBitsImage + ", brightness=" + brightness + ", saturation=" + saturation + ", outputColorSpace=" + outputColorSpace + ", RGBInterpolate4Colors=" + RGBInterpolate4Colors + ", DontStretchPixels=" + DontStretchPixels + ", unclipColors=" + unclipColors + ", medianFilterPasses=" + medianFilterPasses + ", enableBlackPoint=" + enableBlackPoint + ", blackPoint=" + blackPoint + ", NRType=" + NRType + ", NRThreshold=" + NRThreshold + ", dcbIterations=" + dcbIterations + ", dcbEnhanceFl=" + dcbEnhanceFl + '}';
    }
    
    

}
