package org.librawjava;

import java.io.IOException;

/**
 * Error from LibRaw with the raw error code and a readable message. Extends
 * IOException, so existing code that catches IOException keeps working.
 */
public class LibrawException extends IOException {

    private static final long serialVersionUID = 1L;

    private final int code;

    public LibrawException(String message, int code) {
        super(message);
        this.code = code;
    }

    /** @return the raw LibRaw code (negative = LibRaw error, positive = operating system error) */
    public int getCode() {
        return code;
    }

    /** @return true if the file is not a RAW format supported by LibRaw (e.g. a normal TIFF, PSD, WebP) */
    public boolean isUnsupportedFormat() {
        return code == -2;
    }

    /** @return the message without the technical "[LibRaw n]" suffix, suitable for display to users */
    public String getUserMessage() {
        String m = getMessage();
        int i = m == null ? -1 : m.lastIndexOf(" [LibRaw ");
        return i > 0 ? m.substring(0, i) : m;
    }
}
