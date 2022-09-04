module org.librawfx {
    requires java.se;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;        
    requires java.logging;
    exports org.librawfx;    
    exports org.libraw.linuxosx;
    exports org.libraw.win;
    opens org.libraw.linuxosx;
    opens org.libraw.win;
}
