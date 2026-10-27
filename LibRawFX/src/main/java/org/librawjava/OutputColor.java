package org.librawjava;

/**
 * Colour space (primaries) of linear 16 bit output, see
 * {@link LibrawImage#readPixelData16(byte[], boolean, OutputColor)}.
 */
public enum OutputColor {

    /** sRGB / Rec. 709 primaries, white D65 (LibRaw output_color 1). */
    SRGB(1),
    /**
     * ProPhoto (ROMM) primaries, white D50 (LibRaw output_color 4). Much wider
     * than sRGB: saturated colours are not clipped while editing.
     */
    PROPHOTO(4);

    private final int libRawCode;

    OutputColor(int libRawCode) {
        this.libRawCode = libRawCode;
    }

    /** @return the value for LibRaw's output_color */
    public int libRawCode() {
        return libRawCode;
    }
}
