package org.librawjava;

/**
 * Description of one thumbnail / preview embedded in a RAW file, see
 * {@link LibrawImage#listThumbnails(byte[])}. Reading the list is cheap: no
 * thumbnail data is loaded.
 *
 * @param index index for {@link LibrawImage#readThumbnail(byte[], int)}
 * @param format storage format in the file
 * @param width width in pixels as stored in the file (0 if unknown)
 * @param height height in pixels as stored in the file (0 if unknown)
 * @param flip orientation of this thumbnail (LibRaw flip code, see
 * {@link RawThumbnail#flip()}), -1 if unknown
 * @param length size of the data in the file in bytes
 */
public record ThumbnailInfo(int index, ThumbnailFormat format, int width, int height, int flip, int length) {

    /** @return true if LibRaw can deliver this thumbnail (JPEG or bitmap) */
    public boolean isSupported() {
        return format.isSupported();
    }

    /** @return the longer side in pixels (0 if unknown) */
    public int longSide() {
        return Math.max(width, height);
    }
}
