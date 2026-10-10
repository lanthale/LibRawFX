package org.librawfx;

import java.nio.charset.StandardCharsets;

public final class RawFormatDetector {

    public static final int HEADER_SIZE = 2048;

    private static final byte[][] PREFIXES = {
        "FUJIFILMCCD-RAW".getBytes(StandardCharsets.US_ASCII),                       // RAF
        "FOVb".getBytes(StandardCharsets.US_ASCII),                                  // X3F
        {0x49, 0x49, 0x1A, 0x00, 0x00, 0x00, 'H', 'E', 'A', 'P', 'C', 'C', 'D', 'R'} // CRW
    };

    private static final String[] RAW_MAKES = {
        "NIKON", "CANON", "SONY", "OLYMPUS", "OM DIGITAL", "PENTAX", "RICOH",
        "PANASONIC", "LEICA", "FUJIFILM", "HASSELBLAD", "PHASE ONE", "SAMSUNG", "KODAK"
    };

    private RawFormatDetector() {}

    public static boolean isRaw(byte[] h) {
        if (h == null || h.length < 16) return false;

        for (byte[] p : PREFIXES) {
            if (startsWith(h, p)) return true;
        }
        // CR3 (ISO-BMFF): "ftypcrx " ab Offset 4
        if (h[4] == 'f' && h[5] == 't' && h[6] == 'y' && h[7] == 'p'
                && h[8] == 'c' && h[9] == 'r' && h[10] == 'x') return true;
        // ORF ("IIRO"/"IIRS") und RW2/Leica ("II" 0x55 0x00)
        if (h[0] == 'I' && h[1] == 'I'
                && ((h[2] == 'R' && (h[3] == 'O' || h[3] == 'S')) || (h[2] == 0x55 && h[3] == 0))) return true;

        boolean le = h[0] == 'I' && h[1] == 'I' && h[2] == 42 && h[3] == 0;
        boolean be = h[0] == 'M' && h[1] == 'M' && h[2] == 0 && h[3] == 42;
        if (!le && !be) return false;

        // CR2: TIFF mit "CR" an Offset 8
        if (le && h[8] == 'C' && h[9] == 'R') return true;

        return isTiffRaw(h, le);
    }

    private static boolean isTiffRaw(byte[] h, boolean le) {
        long ifd0 = u32(h, 4, le);
        if (ifd0 < 8 || ifd0 + 2 > h.length) return false;
        int n = u16(h, (int) ifd0, le);
        String make = null;

        for (int i = 0; i < Math.min(n, 128); i++) {
            int e = (int) ifd0 + 2 + i * 12;
            if (e + 12 > h.length) break;
            int tag = u16(h, e, le);
            int type = u16(h, e + 2, le);
            long count = u32(h, e + 4, le);

            if (tag == 0xC612) return true;                       // DNGVersion
            if (tag == 0x010F && type == 2 && count > 0 && count <= 64) {   // Make
                long off = count <= 4 ? e + 8 : u32(h, e + 8, le);
                if (off + count <= h.length) {
                    make = new String(h, (int) off, (int) count, StandardCharsets.US_ASCII)
                            .trim().toUpperCase();
                }
            }
        }
        if (make != null) {
            for (String m : RAW_MAKES) {
                if (make.startsWith(m)) return true;
            }
        }
        return false;
    }

    private static boolean startsWith(byte[] d, byte[] p) {
        if (d.length < p.length) return false;
        for (int i = 0; i < p.length; i++) if (d[i] != p[i]) return false;
        return true;
    }

    private static int u16(byte[] h, int o, boolean le) {
        return le ? (h[o] & 0xFF) | (h[o + 1] & 0xFF) << 8
                  : (h[o] & 0xFF) << 8 | (h[o + 1] & 0xFF);
    }

    private static long u32(byte[] h, int o, boolean le) {
        return le ? (u16(h, o, true) | (long) u16(h, o + 2, true) << 16)
                  : ((long) u16(h, o, false) << 16 | u16(h, o + 2, false));
    }
}