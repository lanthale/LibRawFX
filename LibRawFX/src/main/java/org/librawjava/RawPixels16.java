package org.librawjava;

/**
 * 16 bit RGB pixels as decoded by LibRaw. A plain data holder without any
 * JavaFX types, so it can be used from Swing applications as well.
 *
 * @param width width in pixels
 * @param height height in pixels
 * @param channels number of colour channels (3 = interleaved R, G, B)
 * @param data unsigned 16 bit samples stored in a short[]; read them with
 * {@code data[i] & 0xFFFF}. Length is width * height * channels.
 * @param linear true = linear light (gamma 1.0, the right input for exposure
 * and white balance), false = gamma encoded as configured in the settings
 */
public record RawPixels16(int width, int height, int channels, short[] data, boolean linear) {

    public RawPixels16 {
        if (width <= 0 || height <= 0 || channels <= 0) {
            throw new IllegalArgumentException("Invalid size " + width + "x" + height + "x" + channels);
        }
        if (data == null || data.length != (long) width * height * channels) {
            throw new IllegalArgumentException("data length does not match width * height * channels");
        }
    }
}