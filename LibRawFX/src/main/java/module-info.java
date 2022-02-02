module org.librawfx {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires jdk.incubator.foreign; 
    requires java.logging;
    exports org.librawfx;    
    exports org.libraw.linuxosx;
    exports org.libraw.win;
    opens org.libraw.linuxosx;
    opens org.libraw.win;
}
