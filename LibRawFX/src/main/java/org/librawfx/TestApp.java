package org.librawfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
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

        Parameters parameters = getParameters();
        String file = parameters.getRaw().get(0);

        FlowPane stack = new FlowPane();
        loadImagesByStream(stack, file);
        //loadImagesByFile(stack);

        var scene = new Scene(stack, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void loadImagesByStream(FlowPane stack, String file) throws MalformedURLException {
        File initialFile = new File(file);
        ProgressIndicator ind = new ProgressIndicator();        
        //ind.progressProperty().bind(img.progressProperty());
        boolean backgroundLoading = true;
        int size = 300;
        for (int i = 0; i < 1; i++) {
            Image img = new Image(initialFile.toURI().toURL().toString(), size, size, true, false, backgroundLoading);
            ImageView view = new ImageView();
            img.progressProperty().addListener((ov, t, t1) -> {
                if ((double) t1 == 1.0) {
                    Image img2;
                    try {
                        stack.getChildren().add(view);
                        stack.getChildren().add(ind);
                        System.out.println("Loading second image...");
                        img2 = new Image(initialFile.toURI().toURL().toString(), backgroundLoading);
                        ImageView view2 = new ImageView();
                        view2.setFitHeight(600);
                        view2.setFitWidth(600);
                        view2.setPreserveRatio(true);
                        ind.progressProperty().bind(img2.progressProperty());
                        img2.progressProperty().addListener((ov2, tx, tx1) -> {
                            if ((double) tx1 == 1.0) {
                                stack.getChildren().remove(ind);
                                stack.getChildren().add(view2);
                            }
                        });
                        view2.setImage(img2);
                        System.out.println("Loading second image...finished!");
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            view.setFitHeight(size);
            view.setFitWidth(size);
            view.setPreserveRatio(true);
            view.setImage(img);
            /*if (backgroundLoading == false) {
                backgroundLoading = true;
            } else {
                backgroundLoading = false;
            }*/
        }
        //stack.getChildren().remove(ind);
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
        args = new String[1];
        //args[0]="/home/lanthale/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.cr2";        
        args[0] = "/System/Volumes/Data/mnt/NAS-ITA/Development/NetBeansProjects/LibRawFX/LibRawFX/src/main/ressources/sample1.cr2";
        //args[0]="C:\\Users\\admin\\Desktop\\RAW-CANON-Sample.cr2";
        launch(args);
    }

}
