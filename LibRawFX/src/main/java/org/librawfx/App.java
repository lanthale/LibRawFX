package org.librawfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        LibrawImage rawImg = new LibrawImage("/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.cr2");
        int[] raw = rawImg.readPixelData();
        WritableImage img = new WritableImage(rawImg.getImageWidth(), rawImg.getImageHeight());
        PixelWriter pw = img.getPixelWriter();
        pw.setPixels(0, 0, rawImg.getImageWidth(), rawImg.getImageHeight(), PixelFormat.getIntArgbInstance(), raw, 0, rawImg.getImageWidth());

        StackPane stack = new StackPane();
        ProgressIndicator ind = new ProgressIndicator();
        stack.getChildren().add(ind);
        ind.progressProperty().bind(img.progressProperty());
        ImageView view = new ImageView();
        view.setFitHeight(500);
        view.setFitWidth(500);
        view.setPreserveRatio(true);
        stack.getChildren().add(view);        
        ind.setVisible(false);
        view.setImage(img);
        stack.getChildren().remove(ind);

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(stack, 640, 480);
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
