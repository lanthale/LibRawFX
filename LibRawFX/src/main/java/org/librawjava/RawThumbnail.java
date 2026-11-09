package org.librawjava;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Thumbnail / preview embedded in a RAW file, as delivered by LibRaw without
 * decoding the RAW data. Plain data holder: JPEG data can be shown directly,
 * e.g. in JavaFX with {@code new Image(new ByteArrayInputStream(t.data()))}.
 *
 * @param type JPEG (data is a complete JPEG file) or BITMAP (data is
 * interleaved 8 bit pixels, width * height * colors bytes)
 * @param width width in pixels. For JPEG the value stored in the RAW file; it
 * can be 0 or differ from the real JPEG size, decode the JPEG for exact values
 * @param height height in pixels, see width
 * @param colors colour channels of a bitmap (3 = RGB, 1 = grey); 3 for JPEG
 * @param flip orientation of the image in the file (LibRaw flip code): 0 =
 * none, 3 = rotate 180, 5 = rotate 90 counter clockwise, 6 = rotate 90
 * clockwise. Embedded previews are usually stored unrotated; apply this to
 * show them upright (see {@link #toBufferedImage(boolean)})
 * @param data JPEG bytes or bitmap pixels
 */
public record RawThumbnail(Type type, int width, int height, int colors, int flip, byte[] data) {

    /** Kind of data in {@link #data()}. */
    public enum Type {
        /** A complete JPEG file. */
        JPEG,
        /** Interleaved 8 bit pixels without header. */
        BITMAP
    }

    public RawThumbnail {
        if (type == null || data == null || data.length == 0) {
            throw new IllegalArgumentException("type and data are required");
        }
        if (type == Type.BITMAP && (width <= 0 || height <= 0 || colors <= 0
                || data.length != (long) width * height * colors)) {
            throw new IllegalArgumentException("Bitmap data does not match " + width + "x" + height + "x" + colors);
        }
    }

    public boolean isJpeg() {
        return type == Type.JPEG;
    }

    /** @return true if the flip code turns the image by 90 degrees (width and height swap) */
    public boolean isRotated90() {
        return flip == 5 || flip == 6;
    }

    /**
     * Converts to a BufferedImage (JPEG via ImageIO, bitmap directly).
     *
     * @param applyFlip true = rotate according to {@link #flip()} so the
     * image is upright
     * @throws IOException if the JPEG data cannot be decoded
     */
    public BufferedImage toBufferedImage(boolean applyFlip) throws IOException {
        BufferedImage img;
        if (type == Type.JPEG) {
            img = ImageIO.read(new ByteArrayInputStream(data));
            if (img == null) {
                throw new IOException("Embedded JPEG thumbnail cannot be decoded");
            }
        } else {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int[] argb = new int[width * height];
            for (int p = 0, i = 0; p < argb.length; p++, i += colors) {
                int r = data[i] & 0xFF;
                int g = colors >= 3 ? data[i + 1] & 0xFF : r;
                int b = colors >= 3 ? data[i + 2] & 0xFF : r;
                argb[p] = 0xFF000000 | r << 16 | g << 8 | b;
            }
            img.setRGB(0, 0, width, height, argb, 0, width);
        }
        return applyFlip ? rotate(img, flip) : img;
    }

    private static BufferedImage rotate(BufferedImage img, int flip) {
        int w = img.getWidth();
        int h = img.getHeight();
        AffineTransform t;
        switch (flip) {
            case 3 -> t = new AffineTransform(-1, 0, 0, -1, w, h);
            case 5 -> t = new AffineTransform(0, -1, 1, 0, 0, w);    // 90 counter clockwise
            case 6 -> t = new AffineTransform(0, 1, -1, 0, h, 0);    // 90 clockwise
            default -> {
                return img;
            }
        }
        boolean swap = flip != 3;
        BufferedImage out = new BufferedImage(swap ? h : w, swap ? w : h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        try {
            g.drawImage(img, t, null);
        } finally {
            g.dispose();
        }
        return out;
    }
}
