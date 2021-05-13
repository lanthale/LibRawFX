package org.librawfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX TestApp
 */
public class TestApp extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        RAWImageLoaderFactory.install();

        Parameters parameters = getParameters();
        String file = parameters.getRaw().get(0);
        String file2 = parameters.getRaw().get(1);
        String file3 = parameters.getRaw().get(2);

        VBox stack = new VBox();
        //stack.setAlignment(Pos.CENTER);
        loadImagesByStream(stack, file, file2, file3);
        //loadImagesByFile(stack);

        var scene = new Scene(stack, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void loadImagesByStream(VBox stack, String file, String file2, String file3) throws MalformedURLException {
        File initialFile = new File(file);
        File initialFile2 = new File(file2);
        File initialFile3 = new File(file3);
        //ind.progressProperty().bind(img.progressProperty());
        Button btn = new Button("Refresh");
        stack.getChildren().add(btn);
        try {
            new LibrawImage(file).getMetaData();
        } catch (IOException ex) {
            Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadImages(stack, initialFile, initialFile2, initialFile3);

        btn.setOnAction((t) -> {
            stack.getChildren().clear();
            stack.getChildren().add(btn);
            Platform.runLater(() -> {
                try {
                    loadImages(stack, initialFile, initialFile2, initialFile3);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

    }

    private void loadImages(VBox stack, File initialFile, File initialFile2, File initialFile3) throws MalformedURLException {
        boolean backgroundLoading = true;
        int size = 200;
        ImageView view = new ImageView();
        ImageView view2 = new ImageView();
        ImageView view3 = new ImageView();
        ProgressIndicator ind = new ProgressIndicator();
        ProgressIndicator ind2 = new ProgressIndicator();
        ProgressIndicator ind3 = new ProgressIndicator();
        stack.getChildren().add(ind);        
        stack.getChildren().add(ind2);        
        stack.getChildren().add(ind3);        
        Image img = new Image(initialFile.toURI().toURL().toString(), size, size, true, false, backgroundLoading);
        img.progressProperty().addListener((ov, t, t1) -> {
            if (t1.doubleValue() == 1.0) {                
                stack.getChildren().remove(ind);
                stack.getChildren().add(view);
                if (img.getException() != null) {
                    System.out.println("Exception for: " + img.getUrl());
                    img.getException().printStackTrace();
                }
            }
        });     
        ind.progressProperty().bind(img.progressProperty());
        Image img2 = new Image(initialFile2.toURI().toURL().toString(), size, size, true, false, backgroundLoading);
        img2.progressProperty().addListener((ov, t, t1) -> {
            if (t1.doubleValue() == 1.0) {                
                stack.getChildren().remove(ind2);
                stack.getChildren().add(view2);
                if (img2.getException() != null) {
                    System.out.println("Exception for: " + img2.getUrl());
                    img2.getException().printStackTrace();
                }
            }
        }); 
        ind2.progressProperty().bind(img2.progressProperty());
        Image img3 = new Image(initialFile3.toURI().toURL().toString(), size, size, true, false, backgroundLoading);
        img3.progressProperty().addListener((ov, t, t1) -> {
            if (t1.doubleValue() == 1.0) {                
                stack.getChildren().remove(ind3);
                stack.getChildren().add(view3);
                if (img3.getException() != null) {
                    System.out.println("Exception for: " + img3.getUrl());
                    img3.getException().printStackTrace();
                }
            }
        }); 
        ind3.progressProperty().bind(img3.progressProperty());
        view.setImage(img);
        view.setFitHeight(size);
        view.setFitWidth(size);
        view.setPreserveRatio(true);
        view2.setImage(img2);
        view2.setFitHeight(size);
        view2.setFitWidth(size);
        view2.setPreserveRatio(true);
        view3.setImage(img3);
        view3.setFitHeight(size);
        view3.setFitWidth(size);
        view3.setPreserveRatio(true);
    }

    private void loadImagesByFile(FlowPane stack, String file) throws IOException {
        //stack.getChildren().add(ind);
        //ind.progressProperty().bind(img.progressProperty());
        for (int i = 0; i < 1; i++) {
            File initialFile = new File(file);
            LibrawImage libraw = new LibrawImage(initialFile.getAbsolutePath());
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
        args = new String[3];
        args[2] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "sample1.cr2";
        args[1] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW_SIGMA_DP2_MERRILL.X3F";
        args[0] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW-ADOBE_DNG_Sample.dng";
        launch(args);
    }

}
