package org.librawfx;

import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Spliterator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jdk.incubator.foreign.CLinker;
import static jdk.incubator.foreign.CLinker.C_CHAR;
import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_POINTER;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.NativeScope;
import jdk.incubator.foreign.SequenceLayout;
import org.libraw.RuntimeHelper;
import org.libraw.libraw_h;
import org.libraw.libraw_h.libraw_data_t;
import org.libraw.libraw_h.libraw_output_params_t;
import org.libraw.libraw_h.libraw_processed_image_t;
import org.libraw.libraw_h.libraw_rawdata_t;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        //var libraw = LibraryLookup.ofPath(Path.of("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRaw/bin/libraw.dylib"));        
        LibraryLookup[] LIBRARIES = RuntimeHelper.libraries(new String[]{"/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/Libraw-temp/libraw-osx/lib/.libs/libraw.20.dylib"});

        MemoryAddress iprc = libraw_h.libraw_init(0);
        System.out.println("setting libraw params");
        MemorySegment datasegment = libraw_data_t.ofAddressRestricted(iprc);
        MemorySegment params$slice = libraw_data_t.params$slice(datasegment);
        //MemorySegment allocate = libraw_output_params_t.allocate();        
        libraw_output_params_t.half_size$set(params$slice, 1);
        libraw_output_params_t.use_camera_wb$set(params$slice, 0);
        libraw_output_params_t.use_auto_wb$set(params$slice, 0);
        libraw_output_params_t.output_tiff$set(params$slice, 0);
        System.out.println("setting libraw params - finished");

        int libraw_open_file = libraw_h.libraw_open_file(iprc, CLinker.toCString("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.cr2").address());
        System.out.println("Process file "+libraw_open_file);
        libraw_h.libraw_unpack(iprc);
        libraw_h.libraw_dcraw_process(iprc);        
        
        System.out.println("Process file - finished");
        /*System.out.println("Writing tiff");
        libraw_h.libraw_dcraw_ppm_tiff_writer(iprc, CLinker.toCString("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.ppm").address());
        System.out.println("Writing tiff - finished");*/
        
        
        MemorySegment errorCode = MemorySegment.allocateNative(C_INT.byteSize());        
        System.out.println("Call make mem image");
        MemoryAddress mem_image_adr = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode.address());
        System.out.println("Call make mem image - finished");
        System.out.println("Access image in mem");
        MemorySegment imageMemSegment = libraw_processed_image_t.ofAddressRestricted(mem_image_adr);
        MemorySegment data$slice = libraw_processed_image_t.data$slice(imageMemSegment);
        System.out.println("Access image in mem - finished");        
        
        int data_size$get = libraw_processed_image_t.data_size$get(imageMemSegment);        
        System.out.println("datasize "+data_size$get);        
        
        System.out.println("Free image from memory");
        libraw_h.libraw_dcraw_clear_mem(mem_image_adr);        
        System.out.println("Free image from memory-finished");
        
        System.out.println("closing raw processor");
        libraw_h.libraw_close(iprc);
        System.out.println("closing raw processor - finished");

        /* iprc->params.half_size = 1;
    iprc->params.use_camera_wb = use_camera_wb;
    iprc->params.use_auto_wb = use_auto_wb;
    iprc->params.output_tiff = tiff_mode;

    ret = libraw_open_file(iprc, fn);
    if (verbose)
      fprintf(stderr, "%s: %s/%s\n", fn, iprc->idata.make, iprc->idata.model);
    HANDLE_ERRORS(ret);

    ret = libraw_unpack(iprc);
    HANDLE_ERRORS(ret);

    ret = libraw_dcraw_process(iprc);
    HANDLE_ERRORS(ret);

    snprintf(outfn, 1023, "%s.%s", fn, tiff_mode ? "tiff" : "ppm");

    if (verbose)
      fprintf(stderr, "Writing file %s\n", outfn);
    ret = libraw_dcraw_ppm_tiff_writer(iprc, outfn);
    HANDLE_ERRORS(ret);
    count++;
  }
  libraw_close(iprc);*/
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static int fromByteArray(byte[] bytes) {
        return bytes[0] << 16 | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF);
    }

}
