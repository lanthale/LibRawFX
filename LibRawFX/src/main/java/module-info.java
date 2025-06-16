module org.librawfx {
    requires java.se;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;        
    requires java.logging;
    exports org.librawfx;
    exports org.librawimageio;
    exports org.librawdecodersettings;
    exports org.librawjava;
    exports org.libraw.nativ;
    opens org.libraw.nativ;
}
