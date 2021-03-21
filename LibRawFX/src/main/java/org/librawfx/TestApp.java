package org.librawfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * JavaFX TestApp
 */
public class TestApp extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        RAWImageLoaderFactory.install();        
        
        FlowPane stack = new FlowPane();
        loadImagesByStream(stack);
        //loadImagesByFile(stack);

        var scene = new Scene(stack, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    private void loadImagesByStream(FlowPane stack) throws MalformedURLException {
        File initialFile = new File("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.cr2");
        //stack.getChildren().add(ind);
        //ind.progressProperty().bind(img.progressProperty());
        for (int i = 0; i < 5; i++) {            
            Image img=new Image(initialFile.toURI().toURL().toString(), false);
            ImageView view = new ImageView();
            view.setFitHeight(200);
            view.setFitWidth(200);
            view.setPreserveRatio(true);
            stack.getChildren().add(view);
            view.setImage(img);
        }
        //stack.getChildren().remove(ind);
    }

    private void loadImagesByFile(FlowPane stack) throws IOException {
        //stack.getChildren().add(ind);
        //ind.progressProperty().bind(img.progressProperty());
        for (int i = 0; i < 5; i++) {
            File initialFile = new File("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.cr2");
            LibrawImage libraw=new LibrawImage(initialFile.getAbsolutePath());
            int[] raw = libraw.readPixelData();            
            WritableImage img = new WritableImage(libraw.getImageWidth(), libraw.getImageHeight());
            PixelWriter pw = img.getPixelWriter();
            pw.setPixels(0, 0, libraw.getImageWidth(), libraw.getImageHeight(), PixelFormat.getIntArgbInstance(), raw, 0, libraw.getImageWidth());
            ImageView view = new ImageView();
            view.setFitHeight(200);
            view.setFitWidth(200);
            view.setPreserveRatio(true);
            stack.getChildren().add(view);
            view.setImage(img);
        }
        //stack.getChildren().remove(ind);
    }

    public static void main(String[] args) {
        launch();
    }

    public static int fromByteArray(byte[] bytes) {
        return bytes[0] << 16 | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF);
    }

}
