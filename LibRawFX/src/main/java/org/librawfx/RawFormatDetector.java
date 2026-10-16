package org.librawfx;

import java.nio.charset.StandardCharsets;

/**
 * Heuristische Vorerkennung von RAW-Dateien anhand der ersten Bytes.
 * Entscheidet nur, ob die Datei an LibRaw geht oder an ImageIO.
 * Die endgültige Instanz bleibt LibRaw (siehe Fallback im RAWImageLoader).
 */
public final class RawFormatDetector {

    /** Je größer, desto zuverlässiger die TIFF-IFD-Analyse (Make-String liegt oft hinter dem IFD). */
    public static final int HEADER_SIZE = 65536;

    private static final int MAX_IFD_ENTRIES = 512;

    // TIFF-Tags
    private static final int TAG_NEW_SUBFILE_TYPE = 0x00FE;
    private static final int TAG_COMPRESSION      = 0x0103;
    private static final int TAG_PHOTOMETRIC      = 0x0106;
    private static final int TAG_MAKE             = 0x010F;
    private static final int TAG_SUB_IFDS         = 0x014A;
    private static final int TAG_DNG_VERSION      = 0xC612;

    // Photometric-Werte
    private static final int PHOTO_CFA        = 32803;
    private static final int PHOTO_LINEAR_RAW = 34892;

    /** Eindeutige Prefixe am Dateianfang. */
    private static final byte[][] PREFIXES = {
        ascii("FUJIFILMCCD-RAW"),                                   // RAF
        ascii("FOVb"),                                              // X3F (Sigma/Foveon)
        {0x49, 0x49, 0x1A, 0x00, 0x00, 0x00, 'H', 'E', 'A', 'P', 'C', 'C', 'D', 'R'}, // CRW
        {0x00, 'M', 'R', 'M'},                                      // MRW (Minolta)
        ascii("IIRO"), ascii("IIRS"), ascii("MMOR"),                // ORF (Olympus)
        {'I', 'I', 0x55, 0x00},                                     // RW2 / RWL (Panasonic, Leica)
        ascii("IIII"), ascii("MMMM")                                // IIQ (Phase One)
    };

    /** Kamerahersteller, die RAW-Dateien mit TIFF-Container erzeugen (Vergleich per contains, Großbuchstaben). */
    private static final String[] RAW_MAKES = {
        "NIKON", "CANON", "SONY", "OLYMPUS", "OM DIGITAL", "PENTAX", "RICOH",
        "PANASONIC", "LEICA", "FUJIFILM", "HASSELBLAD", "PHASE ONE", "SAMSUNG",
        "KODAK", "MINOLTA", "EPSON", "MAMIYA", "LEAF", "SIGMA", "CASIO",
        "AGFA", "SINAR", "IMACON", "CONTAX"
    };

    private RawFormatDetector() {}

    public static boolean isRaw(byte[] h) {
        if (h == null || h.length < 16) return false;

        for (byte[] p : PREFIXES) {
            if (startsWith(h, p)) return true;
        }

        // CR3 (ISO-BMFF): Box-Größe beliebig, "ftyp" ab Offset 4, Brand "crx "
        if (h[4] == 'f' && h[5] == 't' && h[6] == 'y' && h[7] == 'p'
                && h[8] == 'c' && h[9] == 'r' && h[10] == 'x' && h[11] == ' ') return true;

        boolean le = h[0] == 'I' && h[1] == 'I' && h[2] == 42 && h[3] == 0;
        boolean be = h[0] == 'M' && h[1] == 'M' && h[2] == 0 && h[3] == 42;
        if (!le && !be) return false;

        // CR2: TIFF (little endian) mit "CR" an Offset 8
        if (le && h[8] == 'C' && h[9] == 'R') return true;

        return isTiffRaw(h, le);
    }

    private static boolean isTiffRaw(byte[] h, boolean le) {
        long ifd0 = u32(h, 4, le);
        if (ifd0 < 8 || ifd0 + 2 > h.length) return false;
        int base = (int) ifd0;
        int n = Math.min(u16(h, base, le), MAX_IFD_ENTRIES);

        String make = null;
        boolean hasSubIfds = false;
        long newSubfileType = -1;
        long photometric = -1;
        long compression = -1;

        for (int i = 0; i < n; i++) {
            int e = base + 2 + i * 12;
            if (e + 12 > h.length) break;

            int tag = u16(h, e, le);
            int type = u16(h, e + 2, le);
            long count = u32(h, e + 4, le);

            switch (tag) {
                case TAG_DNG_VERSION:
                    return true;                                   // DNG (auch Linear-DNG)
                case TAG_SUB_IFDS:
                    hasSubIfds = true;
                    break;
                case TAG_NEW_SUBFILE_TYPE:
                    newSubfileType = scalar(h, e, type, le);
                    break;
                case TAG_PHOTOMETRIC:
                    photometric = scalar(h, e, type, le);
                    break;
                case TAG_COMPRESSION:
                    compression = scalar(h, e, type, le);
                    break;
                case TAG_MAKE:
                    if (type == 2 && count >= 2 && count <= 64) {
                        long off = count <= 4 ? e + 8 : u32(h, e + 8, le);
                        if (off >= 0 && off + count <= h.length) {
                            make = readAscii(h, (int) off, (int) count);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        // Sensordaten direkt in IFD0 (CFA / LinearRaw): kein Foto-TIFF, ImageIO kann das nicht
        if (photometric == PHOTO_CFA || photometric == PHOTO_LINEAR_RAW) return true;
        // Herstellerspezifische Kompression (Sony ARW, Nikon NEF, Samsung, Pentax, Kodak ...)
        if (isRawCompression(compression)) return true;

        if (make == null || !matchesRawMake(make)) return false;

        // Make passt, aber sieht IFD0 nach exportiertem Foto-TIFF aus?
        // (Lightroom/Photoshop behalten Make/Model der Kamera.)
        boolean standardPhotometric = photometric == 0 || photometric == 1 || photometric == 2
                || photometric == 3 || photometric == 5 || photometric == 6 || photometric == 8;
        boolean looksExported = standardPhotometric && !hasSubIfds && newSubfileType != 1;
        return !looksExported;
    }

    private static boolean isRawCompression(long c) {
        return c == 32767      // Sony ARW
            || c == 32769      // Panasonic/Epson
            || c == 32770      // Samsung SRW
            || c == 34713      // Nikon NEF
            || c == 65000      // Kodak
            || c == 65535;     // Pentax PEF
    }

    private static boolean matchesRawMake(String make) {
        for (String m : RAW_MAKES) {
            if (make.contains(m)) return true;
        }
        return false;
    }

    /** Liest einen einzelnen inline gespeicherten Wert (BYTE, SHORT, LONG). */
    private static long scalar(byte[] h, int e, int type, boolean le) {
        switch (type) {
            case 1:  return h[e + 8] & 0xFF;
            case 3:  return u16(h, e + 8, le);
            case 4:  return u32(h, e + 8, le);
            default: return -1;
        }
    }

    private static String readAscii(byte[] h, int off, int count) {
        int len = 0;
        while (len < count && h[off + len] != 0) len++;       // am ersten NUL abschneiden
        return new String(h, off, len, StandardCharsets.US_ASCII).trim().toUpperCase();
    }

    private static byte[] ascii(String s) {
        return s.getBytes(StandardCharsets.US_ASCII);
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