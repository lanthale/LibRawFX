package org.librawfx;

import com.sun.javafx.iio.common.ImageDescriptor;

/**
 * Descriptor für alle von LibRaw (libraw.org) unterstützten RAW-Formate.
 *
 * Die Signaturen sind bewusst großzügig und enthalten auch die generischen
 * TIFF-Header, weil NEF, NRW, DNG, ARW, PEF, SRW, 3FR, SR2 usw. keine eigene
 * Kennung haben. Die eigentliche Unterscheidung RAW / normales TIFF passiert
 * im RAWImageLoader (RawFormatDetector, LibRaw, ImageIO-Fallback).
 */
public class RAWDescriptor extends ImageDescriptor {

    private static final String formatName = "RAW";

    private static final String[] extensions = {
        "3fr", "ari", "arw", "bay", "cap", "cr2", "cr3", "crw", "dcr", "dcs",
        "dng", "drf", "eip", "erf", "fff", "iiq", "k25", "kdc", "mdc", "mef",
        "mfw", "mos", "mrw", "nef", "nrw", "orf", "ori", "pef", "ptx", "pxn",
        "raf", "raw", "rdc", "rw2", "rwl", "rwz", "sr2", "srf", "srw", "x3f"
    };

    private static final Signature[] signatures = {
        // --- TIFF-Container (NEF, NRW, DNG, ARW, SR2, SRF, PEF, SRW, 3FR, CR2, ERF, MEF, KDC, DCR ...)
        // Erfasst auch normale TIFFs; diese werden im Loader an ImageIO weitergereicht.
        sig("49492A00"),                              // II*\0  little endian
        sig("4D4D002A"),                              // MM\0*  big endian

        // --- Formate mit eigener Kennung (identisch zu RawFormatDetector.PREFIXES)
        sig("46554A4946494C4D4343442D524157"),        // RAF  "FUJIFILMCCD-RAW"
        sig("464F5662"),                              // X3F  "FOVb"
        sig("49491A0000004845415043434452"),          // CRW  "II" 1A 00 00 00 "HEAPCCDR"
        sig("004D524D"),                              // MRW  "\0MRM"
        sig("4949524F"),                              // ORF  "IIRO"
        sig("49495253"),                              // ORF  "IIRS"
        sig("4D4D4F52"),                              // ORF  "MMOR"
        sig("49495500"),                              // RW2 / RWL  "II" 0x55 0x00
        sig("49494949"),                              // IIQ  "IIII"
        sig("4D4D4D4D"),                              // IIQ  "MMMM"

        // --- CR3 (ISO-BMFF): Präfix-Vergleich kennt keine Wildcards,
        // daher die zwei gängigen Box-Größen vor "ftypcrx ".
        sig("0000001866747970" + "63727820"),         // Boxgröße 0x18
        sig("0000001C66747970" + "63727820")          // Boxgröße 0x1C
    };

    private static final String[] mimeSubtypes = extensions;

    private static ImageDescriptor theInstance = null;

    private RAWDescriptor() {
        super(formatName, extensions, signatures, mimeSubtypes);
    }

    public static synchronized ImageDescriptor getInstance() {
        if (theInstance == null) {
            theInstance = new RAWDescriptor();
        }
        return theInstance;
    }

    private static Signature sig(String hex) {
        return new Signature(hexStringToByteArray(hex));
    }

    public static byte[] hexStringToByteArray(String input) {
        int len = input.length();
        if (len == 0) {
            return new byte[0];
        }
        byte[] data;
        int startIdx;
        if (len % 2 != 0) {
            data = new byte[(len / 2) + 1];
            data[0] = (byte) Character.digit(input.charAt(0), 16);
            startIdx = 1;
        } else {
            data = new byte[len / 2];
            startIdx = 0;
        }
        for (int i = startIdx; i < len; i += 2) {
            data[(i + 1) / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i + 1), 16));
        }
        return data;
    }
}