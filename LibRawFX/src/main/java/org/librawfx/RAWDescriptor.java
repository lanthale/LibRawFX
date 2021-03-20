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

    private static final String formatName = "CR2";

    private static final String[] extensions = {"cr2", "crw"};

    private static final Signature[] signatures = {
        new Signature(hexStringToByteArray("49491A0000004845")),new Signature(hexStringToByteArray("49492A00100000004352"))};

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
