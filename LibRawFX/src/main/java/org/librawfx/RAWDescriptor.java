/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.librawfx;

import com.sun.javafx.iio.common.ImageDescriptor;

/**
 *
 * @author selfemp
 */
public class RAWDescriptor extends ImageDescriptor {

    private static final String formatName = "RAW";

    private static final String[] extensions = {"cr2", "crw", "cr3", "nef", "nrw", "raf", "x3f", "dng", "raw", "rwl", "mef", "mfw", "orf", "ori", "rw2", "pef", "srw", "arw"};

    private static final Signature[] signatures = {
        new Signature(hexStringToByteArray("49491A0000004845")),//cr2
        new Signature(hexStringToByteArray("49492A00100000004352")),//crw3
        new Signature(hexStringToByteArray("46554A4946494C4D4343")),//nef
        new Signature(hexStringToByteArray("49492A00080000001C00")),//nrw
        new Signature(hexStringToByteArray("464F5662000004000100")),//raf
        new Signature(hexStringToByteArray("464F5662000003003030")),//x3f
        new Signature(hexStringToByteArray("49492A00080000003600")),//dng
        new Signature(hexStringToByteArray("49492A00080000001300")),//sony arw
        new Signature(hexStringToByteArray("49495500080000002200")) //Leica raw
    };

    private static ImageDescriptor theInstance = null;

    private RAWDescriptor() {
        super(formatName, extensions, signatures);
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
