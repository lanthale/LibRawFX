package org.librawdecodersettings;

import java.util.Arrays;

/**
 * Settings for one decode (default or per camera model). They are translated
 * to libraw_output_params_t by {@link RawDecoderToNativeTranslator}.
 *
 * Conventions:
 * - Optional values: -1, or the matching "use..." flag is false, means "leave
 *   the LibRaw default untouched".
 * - Thread safety: configure an instance completely, then publish it (for
 *   example put it into the settings map) and do not change it anymore while
 *   images are loading. To change values, edit a {@link #copy()} and replace
 *   the map entry.
 *
 * @author cleme
 */
public class RawDecoderSettings {

    // ---- white balance --------------------------------------------------------
    /** "CAMERA" = use the white balance of the camera, anything else = do not. */
    private String whiteBalance = "CAMERA";
    private boolean autoWhitebalance = false;
    /**
     * user_mul: R, G1, B, G2 multipliers (only the ratios matter). When enabled,
     * camera and auto white balance are switched off. The default 1,1,1,1 means
     * "no white balance"; set real values with setUserMultipliers(...).
     */
    private boolean useUserMultipliers = false;
    private float[] userMultipliers = {1f, 1f, 1f, 1f};
    /** greybox: x, y, width, height of the area used by auto white balance. */
    private boolean useGreyBox = false;
    private int[] greyBox = {0, 0, 0, 0};

    // ---- demosaic / noise ------------------------------------------------------
    /**
     * 0 linear, 1 VNG, 2 PPG, 3 AHD, 4 DCB, 11 DHT, 12 modified AHD (by Anton
     * Petrusevich). LibRaw's own default is -1. Foveon (Sigma) ignores this.
     */
    private int RAWQuality = 1;
    private boolean halfSizeOutput = false;
    /**
     * Decode with half_size automatically when only a small preview is needed
     * (requested size at most about half of the image). Full size requests are
     * not affected. Has no effect if halfSizeOutput is already true. A global
     * override (LibrawImage.setPreviewHalfSizeOverride or the system property
     * -Dlibrawfx.halfSizeForPreviews=false) takes precedence over this flag.
     */
    private boolean halfSizeForPreviews = true;
    /** four_color_rgb: interpolate RGB as four colors. */
    private boolean RGBInterpolate4Colors = false;
    private boolean greenMatching = false;
    private int medianFilterPasses = 0;
    /** fbdd_noiserd: 0 off, 1 light, 2 full. */
    private int noiseReduction = 0;
    /** threshold: wavelet denoise threshold, 0 = off. */
    private int NRThreshold = 0;
    /** Legacy, no effect. Use {@link #NRThreshold}. */
    private String NRType = "NONR";
    /** DCB only (quality 4). iterations -1 = LibRaw default. */
    private int dcbIterations = -1;
    private boolean dcbEnhanceFl = false;
    /** aber: chromatic aberration scale for red and blue, 1.0 = off. */
    private double aberRed = 1.0;
    private double aberBlue = 1.0;

    // ---- brightness / exposure / highlights ------------------------------------
    private boolean autoBrightness = true;
    private float autoBrightnessThreashold = 0.01f;
    private float brightnes = 1.0f;
    /** exp_correc on/off. exp_shift range 0.25 .. 8.0, 1.0 = unchanged. */
    private boolean enableExposureCorrection = false;
    private float exposureShift = 1.0f;
    private float expoCorrectionHighlight = 0.0f;
    /** highlight 1 (legacy switch). Superseded by {@link #unclipColors} when that is > 0. */
    private boolean fixColorsHighlights = false;
    /** highlight mode: 0 clip, 1 unclip, 2 blend, 3-9 rebuild. 0 = use {@link #fixColorsHighlights}. */
    private int unclipColors = 0;
    private float adjustMaximumThreshold = 0.75f;
    /** gamm: gammaPower = 1 / gamma (sRGB like 0.45), gammaToeSlope 4.5. */
    private boolean useCustomGamma = false;
    private double gammaPower = 0.45;
    private double gammaToeSlope = 4.5;

    // ---- levels ----------------------------------------------------------------
    /** user_sat: saturation LEVEL (dcraw -S), not colour saturation. -1 = off. */
    private int saturation = -1;
    private boolean enableBlackPoint = false;
    private int blackPoint = 0;
    /** user_cblack: black level per channel. */
    private boolean useChannelBlack = false;
    private int[] channelBlack = {0, 0, 0, 0};

    // ---- colour ----------------------------------------------------------------
    /** [0-6] raw, sRGB, Adobe, Wide, ProPhoto, XYZ, ACES (more depending on the LibRaw version). */
    private int outputColorSpace = 1;
    /** 0 off, 1 default, 3 always, -1 = untouched. */
    private int useCameraMatrix = -1;

    // ---- geometry / frames -------------------------------------------------------
    /** -1 orientation from the file, 0 none, 3 = 180, 5 = 90 CCW, 6 = 90 CW. */
    private int userFlip = -1;
    /** true = do not stretch or rotate raw pixels (use_fuji_rotate = 0). */
    private boolean DontStretchPixels = false;
    private boolean useCropBox = false;
    private int[] cropBox = {0, 0, 0, 0};
    /** multi shot files (rawparams.shot_select, decode-time option). */
    private int shotSelect = 0;

    // ---- output / misc -----------------------------------------------------------
    /** Ignored: LibrawImage forces 8 bit for images and 16 bit for readPixelData16(...). */
    private boolean sixteenBitsImage = false;
    /** No effect on the in-memory decode (only used when writing a TIFF file). */
    private boolean output_tiff = false;
    /** rawparams.coolscan_nef_gamma (decode-time option). */
    private float coolScanNEFGamma = 1.0f;
    /** Bit mask of LIBRAW_PROCESSING_* (see libraw_const.h), written to rawparams.options and OR-ed with the LibRaw defaults. */
    private int rawProcessingOptions = 0;
    private String outputProfilePath;
    private String cameraProfilePath;
    private String badPixelsPath;
    private String darkFramePath;

    public RawDecoderSettings() {
    }

    /**
     * Deep copy, for editing without touching an instance that is in use.
     */
    public RawDecoderSettings copy() {
        RawDecoderSettings c = new RawDecoderSettings();
        c.whiteBalance = whiteBalance;
        c.autoWhitebalance = autoWhitebalance;
        c.useUserMultipliers = useUserMultipliers;
        c.userMultipliers = userMultipliers.clone();
        c.useGreyBox = useGreyBox;
        c.greyBox = greyBox.clone();
        c.RAWQuality = RAWQuality;
        c.halfSizeOutput = halfSizeOutput;
        c.halfSizeForPreviews = halfSizeForPreviews;
        c.RGBInterpolate4Colors = RGBInterpolate4Colors;
        c.greenMatching = greenMatching;
        c.medianFilterPasses = medianFilterPasses;
        c.noiseReduction = noiseReduction;
        c.NRThreshold = NRThreshold;
        c.NRType = NRType;
        c.dcbIterations = dcbIterations;
        c.dcbEnhanceFl = dcbEnhanceFl;
        c.aberRed = aberRed;
        c.aberBlue = aberBlue;
        c.autoBrightness = autoBrightness;
        c.autoBrightnessThreashold = autoBrightnessThreashold;
        c.brightnes = brightnes;
        c.enableExposureCorrection = enableExposureCorrection;
        c.exposureShift = exposureShift;
        c.expoCorrectionHighlight = expoCorrectionHighlight;
        c.fixColorsHighlights = fixColorsHighlights;
        c.unclipColors = unclipColors;
        c.adjustMaximumThreshold = adjustMaximumThreshold;
        c.useCustomGamma = useCustomGamma;
        c.gammaPower = gammaPower;
        c.gammaToeSlope = gammaToeSlope;
        c.saturation = saturation;
        c.enableBlackPoint = enableBlackPoint;
        c.blackPoint = blackPoint;
        c.useChannelBlack = useChannelBlack;
        c.channelBlack = channelBlack.clone();
        c.outputColorSpace = outputColorSpace;
        c.useCameraMatrix = useCameraMatrix;
        c.userFlip = userFlip;
        c.DontStretchPixels = DontStretchPixels;
        c.useCropBox = useCropBox;
        c.cropBox = cropBox.clone();
        c.shotSelect = shotSelect;
        c.sixteenBitsImage = sixteenBitsImage;
        c.output_tiff = output_tiff;
        c.coolScanNEFGamma = coolScanNEFGamma;
        c.rawProcessingOptions = rawProcessingOptions;
        c.outputProfilePath = outputProfilePath;
        c.cameraProfilePath = cameraProfilePath;
        c.badPixelsPath = badPixelsPath;
        c.darkFramePath = darkFramePath;
        return c;
    }

    // ============================== white balance ==============================
    public String getWhiteBalance() {
        return whiteBalance;
    }

    public void setWhiteBalance(String whiteBalance) {
        this.whiteBalance = whiteBalance;
    }

    public boolean isAutoWhitebalance() {
        return autoWhitebalance;
    }

    public void setAutoWhitebalance(boolean autoWhitebalance) {
        this.autoWhitebalance = autoWhitebalance;
    }

    public boolean isUseUserMultipliers() {
        return useUserMultipliers;
    }

    public void setUseUserMultipliers(boolean useUserMultipliers) {
        this.useUserMultipliers = useUserMultipliers;
    }

    public float[] getUserMultipliers() {
        return userMultipliers.clone();
    }

    public void setUserMultipliers(float r, float g1, float b, float g2) {
        this.userMultipliers = new float[]{r, g1, b, g2};
    }

    public boolean isUseGreyBox() {
        return useGreyBox;
    }

    public void setUseGreyBox(boolean useGreyBox) {
        this.useGreyBox = useGreyBox;
    }

    public int[] getGreyBox() {
        return greyBox.clone();
    }

    public void setGreyBox(int x, int y, int w, int h) {
        this.greyBox = new int[]{x, y, w, h};
    }

    // ============================ demosaic / noise =============================
    public int getRAWQuality() {
        return RAWQuality;
    }

    public void setRAWQuality(int RAWQuality) {
        this.RAWQuality = RAWQuality;
    }

    public boolean isHalfSizeOutput() {
        return halfSizeOutput;
    }

    public void setHalfSizeOutput(boolean halfSizeOutput) {
        this.halfSizeOutput = halfSizeOutput;
    }

    public boolean isHalfSizeForPreviews() {
        return halfSizeForPreviews;
    }

    public void setHalfSizeForPreviews(boolean halfSizeForPreviews) {
        this.halfSizeForPreviews = halfSizeForPreviews;
    }

    public boolean isRGBInterpolate4Colors() {
        return RGBInterpolate4Colors;
    }

    public void setRGBInterpolate4Colors(boolean RGBInterpolate4Colors) {
        this.RGBInterpolate4Colors = RGBInterpolate4Colors;
    }

    public boolean isGreenMatching() {
        return greenMatching;
    }

    public void setGreenMatching(boolean greenMatching) {
        this.greenMatching = greenMatching;
    }

    public int getMedianFilterPasses() {
        return medianFilterPasses;
    }

    public void setMedianFilterPasses(int medianFilterPasses) {
        this.medianFilterPasses = Math.max(0, medianFilterPasses);
    }

    public int getNoiseReduction() {
        return noiseReduction;
    }

    public void setNoiseReduction(int noiseReduction) {
        this.noiseReduction = Math.max(0, Math.min(2, noiseReduction));
    }

    public int getNRThreshold() {
        return NRThreshold;
    }

    public void setNRThreshold(int NRThreshold) {
        this.NRThreshold = Math.max(0, NRThreshold);
    }

    /** @deprecated no effect, use {@link #setNRThreshold(int)} */
    @Deprecated
    public String getNRType() {
        return NRType;
    }

    /** @deprecated no effect, use {@link #setNRThreshold(int)} */
    @Deprecated
    public void setNRType(String NRType) {
        this.NRType = NRType;
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

    public double getAberRed() {
        return aberRed;
    }

    public double getAberBlue() {
        return aberBlue;
    }

    public void setAber(double red, double blue) {
        this.aberRed = red;
        this.aberBlue = blue;
    }

    // ================= brightness / exposure / highlights ======================
    public boolean isAutoBrightness() {
        return autoBrightness;
    }

    public void setAutoBrightness(boolean autoBrightness) {
        this.autoBrightness = autoBrightness;
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

    /** @deprecated duplicate of {@link #getBrightNess()} */
    @Deprecated
    public double getBrightnes() {
        return brightnes;
    }

    /** @deprecated duplicate of {@link #setBrightNess(float)} */
    @Deprecated
    public void setBrightnes(double brightness) {
        this.brightnes = (float) brightness;
    }

    public boolean isEnableExposureCorrection() {
        return enableExposureCorrection;
    }

    public void setEnableExposureCorrection(boolean enableExposureCorrection) {
        this.enableExposureCorrection = enableExposureCorrection;
    }

    /** exp_correc as LibRaw expects it: 1 when the correction is enabled, otherwise 0. */
    public int getExposureCorrection() {
        return enableExposureCorrection ? 1 : 0;
    }

    /** Same switch as {@link #setEnableExposureCorrection(boolean)}: 0 = off, anything else = on. */
    public void setExposureCorrection(int exposureCorrection) {
        this.enableExposureCorrection = exposureCorrection != 0;
    }

    public float getExposureShift() {
        return exposureShift;
    }

    public void setExposureShift(float exposureShift) {
        this.exposureShift = exposureShift;
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

    public boolean isFixColorsHighlights() {
        return fixColorsHighlights;
    }

    public void setFixColorsHighlights(boolean fixColorsHighlights) {
        this.fixColorsHighlights = fixColorsHighlights;
    }

    public int getUnclipColors() {
        return unclipColors;
    }

    /** 0 = use the legacy switch, 1 unclip, 2 blend, 3-9 rebuild (5 is a good compromise). */
    public void setUnclipColors(int unclipColors) {
        this.unclipColors = Math.max(0, Math.min(9, unclipColors));
    }

    public float getAdjustMaximumThreshold() {
        return adjustMaximumThreshold;
    }

    public void setAdjustMaximumThreshold(float adjustMaximumThreshold) {
        this.adjustMaximumThreshold = adjustMaximumThreshold;
    }

    public boolean isUseCustomGamma() {
        return useCustomGamma;
    }

    public void setUseCustomGamma(boolean useCustomGamma) {
        this.useCustomGamma = useCustomGamma;
    }

    public double getGammaPower() {
        return gammaPower;
    }

    public double getGammaToeSlope() {
        return gammaToeSlope;
    }

    public void setGamma(double power, double toeSlope) {
        this.gammaPower = power;
        this.gammaToeSlope = toeSlope;
    }

    // ================================= levels ==================================
    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
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

    public boolean isUseChannelBlack() {
        return useChannelBlack;
    }

    public void setUseChannelBlack(boolean useChannelBlack) {
        this.useChannelBlack = useChannelBlack;
    }

    public int[] getChannelBlack() {
        return channelBlack.clone();
    }

    public void setChannelBlack(int c0, int c1, int c2, int c3) {
        this.channelBlack = new int[]{c0, c1, c2, c3};
    }

    // ================================= colour ==================================
    public int getOutputColorSpace() {
        return outputColorSpace;
    }

    public void setOutputColorSpace(int outputColorSpace) {
        this.outputColorSpace = outputColorSpace;
    }

    public int getUseCameraMatrix() {
        return useCameraMatrix;
    }

    public void setUseCameraMatrix(int useCameraMatrix) {
        this.useCameraMatrix = useCameraMatrix;
    }

    // ============================ geometry / frames ============================
    public int getUserFlip() {
        return userFlip;
    }

    public void setUserFlip(int userFlip) {
        this.userFlip = userFlip;
    }

    public boolean isDontStretchPixels() {
        return DontStretchPixels;
    }

    public void setDontStretchPixels(boolean DontStretchPixels) {
        this.DontStretchPixels = DontStretchPixels;
    }

    public boolean isUseCropBox() {
        return useCropBox;
    }

    public void setUseCropBox(boolean useCropBox) {
        this.useCropBox = useCropBox;
    }

    public int[] getCropBox() {
        return cropBox.clone();
    }

    public void setCropBox(int x, int y, int w, int h) {
        this.cropBox = new int[]{x, y, w, h};
    }

    public int getShotSelect() {
        return shotSelect;
    }

    public void setShotSelect(int shotSelect) {
        this.shotSelect = Math.max(0, shotSelect);
    }

    // ============================== output / misc ==============================
    public boolean isSixteenBitsImage() {
        return sixteenBitsImage;
    }

    public void setSixteenBitsImage(boolean sixteenBitsImage) {
        this.sixteenBitsImage = sixteenBitsImage;
    }

    public boolean isOutput_tiff() {
        return output_tiff;
    }

    public void setOutput_tiff(boolean output_tiff) {
        this.output_tiff = output_tiff;
    }

    public float getCoolScanNEFGamma() {
        return coolScanNEFGamma;
    }

    public void setCoolScanNEFGamma(float coolScanNEFGamma) {
        this.coolScanNEFGamma = coolScanNEFGamma;
    }

    public int getRawProcessingOptions() {
        return rawProcessingOptions;
    }

    public void setRawProcessingOptions(int rawProcessingOptions) {
        this.rawProcessingOptions = rawProcessingOptions;
    }

    public String getOutputProfilePath() {
        return outputProfilePath;
    }

    public void setOutputProfilePath(String outputProfilePath) {
        this.outputProfilePath = outputProfilePath;
    }

    public String getCameraProfilePath() {
        return cameraProfilePath;
    }

    public void setCameraProfilePath(String cameraProfilePath) {
        this.cameraProfilePath = cameraProfilePath;
    }

    public String getBadPixelsPath() {
        return badPixelsPath;
    }

    public void setBadPixelsPath(String badPixelsPath) {
        this.badPixelsPath = badPixelsPath;
    }

    public String getDarkFramePath() {
        return darkFramePath;
    }

    public void setDarkFramePath(String darkFramePath) {
        this.darkFramePath = darkFramePath;
    }

    @Override
    public String toString() {
        return "RawDecoderSettings{whiteBalance=" + whiteBalance + ", autoWhitebalance=" + autoWhitebalance
                + ", userMul=" + (useUserMultipliers ? Arrays.toString(userMultipliers) : "off")
                + ", greyBox=" + (useGreyBox ? Arrays.toString(greyBox) : "off")
                + ", RAWQuality=" + RAWQuality + ", halfSizeOutput=" + halfSizeOutput
                + ", halfSizeForPreviews=" + halfSizeForPreviews
                + ", RGBInterpolate4Colors=" + RGBInterpolate4Colors + ", greenMatching=" + greenMatching
                + ", medianFilterPasses=" + medianFilterPasses + ", noiseReduction=" + noiseReduction
                + ", NRThreshold=" + NRThreshold + ", dcbIterations=" + dcbIterations
                + ", dcbEnhanceFl=" + dcbEnhanceFl + ", aber=" + aberRed + "/" + aberBlue
                + ", autoBrightness=" + autoBrightness + ", autoBrightnessThreashold=" + autoBrightnessThreashold
                + ", brightNess=" + brightnes + ", enableExposureCorrection=" + enableExposureCorrection
                + ", exposureShift=" + exposureShift + ", expoCorrectionHighlight=" + expoCorrectionHighlight
                + ", fixColorsHighlights=" + fixColorsHighlights + ", unclipColors=" + unclipColors
                + ", adjustMaximumThreshold=" + adjustMaximumThreshold
                + ", gamma=" + (useCustomGamma ? gammaPower + "/" + gammaToeSlope : "default")
                + ", saturation=" + saturation + ", blackPoint=" + (enableBlackPoint ? blackPoint : "off")
                + ", channelBlack=" + (useChannelBlack ? Arrays.toString(channelBlack) : "off")
                + ", outputColorSpace=" + outputColorSpace + ", useCameraMatrix=" + useCameraMatrix
                + ", userFlip=" + userFlip + ", DontStretchPixels=" + DontStretchPixels
                + ", cropBox=" + (useCropBox ? Arrays.toString(cropBox) : "off")
                + ", shotSelect=" + shotSelect + ", sixteenBitsImage=" + sixteenBitsImage
                + ", output_tiff=" + output_tiff + ", coolScanNEFGamma=" + coolScanNEFGamma
                + ", rawProcessingOptions=" + rawProcessingOptions + '}';
    }
}