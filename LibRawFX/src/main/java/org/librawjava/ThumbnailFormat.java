package org.librawjava;

/**
 * Storage format of an embedded thumbnail / preview in the RAW file (from
 * LibRaw's internal thumbnail formats). Except UNKNOWN, LibRaw converts every
 * format to JPEG or bitmap when the thumbnail is read (see {@link RawThumbnail}).
 */
public enum ThumbnailFormat {

    UNKNOWN,
    /** JPEG stream, the usual case (embedded camera preview). */
    JPEG,
    /** Uncompressed 8 bit bitmap (PPM). */
    BITMAP,
    /** Uncompressed 16 bit bitmap (PPM16). */
    BITMAP16,
    /** Kodak specific thumbnail formats. */
    KODAK,
    /** Layered bitmap (Foveon). */
    LAYER,
    /** Rollei format. */
    ROLLEI,
    /** Sigma X3F preview. */
    X3F;

    /** @return true if LibRaw can deliver this thumbnail */
    public boolean isSupported() {
        return this != UNKNOWN;
    }

    /**
     * @param code value of LibRaw's LibRaw_internal_thumbnail_formats
     * (tformat of libraw_thumbnail_item_t)
     * @return the format, UNKNOWN for unknown values
     */
    public static ThumbnailFormat ofInternal(int code) {
        return switch (code) {
            case 1, 2, 3 -> KODAK;
            case 4 -> JPEG;
            case 5 -> LAYER;
            case 6 -> ROLLEI;
            case 7 -> BITMAP;
            case 8 -> BITMAP16;
            case 9 -> X3F;
            default -> UNKNOWN;
        };
    }
}
