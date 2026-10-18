package org.librawjava;

import java.io.IOException;

/** Fehler aus LibRaw mit Fehlercode und benutzerlesbarer Meldung. */
public class LibrawException extends IOException {

    private final int code;

    public LibrawException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /** true, wenn die Datei kein von LibRaw unterstütztes RAW-Format ist (z. B. ein normales TIFF). */
    public boolean isUnsupportedFormat() {
        return code == -2;
    }
}