package org.librawfx;

import com.sun.javafx.iio.common.ImageDescriptor;

public class RAWDescriptor extends ImageDescriptor {

    private static final String formatName = "RAW";

    private static final String[] extensions = {"cr2", "crw", "cr3", "nef", "nrw", "raf", "x3f", "dng", "raw", "rwl", "mef", "mfw", "orf", "ori", "rw2", "pef", "srw", "arw"};

    private static final Signature[] signatures = {
        // TIFF-Container (NEF, NRW, DNG, ARW, CR2, PEF, SRW, RW2-Varianten ...)
        new Signature(hexStringToByteArray("49492A00")),       // II*\0  little endian
        new Signature(hexStringToByteArray("4D4D002A")),       // MM\0*  big endian
        // Sonderfälle mit eigener Kennung
        new Signature(hexStringToByteArray("49495500")),       // II U\0 (RW2, Leica)
        new Signature(hexStringToByteArray("49495253")),       // IIRS (ORF)
        new Signature(hexStringToByteArray("4949524F")),       // IIRO (ORF)
        new Signature(hexStringToByteArray("46554A4946494C4D4343")), // FUJIFILMCC (RAF, wie bisher)
        new Signature(hexStringToByteArray("46554A494649")),   // FUJIFI
        new Signature(hexStringToByteArray("464F5662")),       // FOVb (X3F)
        new Signature(hexStringToByteArray("49491A00000048454150")) // CRW
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

    public static byte[] hexStringToByteArray(String input) {
        int len = input.length();
        if (len == 0) {
            return new byte[]{};
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