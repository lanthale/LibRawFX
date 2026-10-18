module org.librawfx {
    requires java.desktop;
    requires javafx.controls;
    requires java.logging;
    requires javafx.graphics;      // javafx.base kommt transitiv
    // requires javafx.controls;   // nur, falls du Controls-Klassen nutzt

    exports org.librawfx;
    exports org.librawimageio;
    exports org.librawdecodersettings;
    exports org.librawjava;
    exports org.libraw.nativ;

    provides javax.imageio.spi.ImageReaderSpi
            with org.librawimageio.RAWImageIOReaderSpi;
}