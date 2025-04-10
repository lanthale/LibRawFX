package org.librawfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX TestApp
 */
public class TestApp extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        RAWImageLoaderFactory.install();
        HashMap<String, RawDecoderSettings> decoderSettings = RAWImageLoaderFactory.getDecoderSettings(); //.setEnableExposureCorrection(false);
        decoderSettings.put("Sigma DP2 Merrill", new RawDecoderSettings());
        decoderSettings.get("Sigma DP2 Merrill").setEnableExposureCorrection(true);
        decoderSettings.get("Sigma DP2 Merrill").setExposureCorrection(1);
        decoderSettings.get("Sigma DP2 Merrill").setExpoCorrectionShift(2.0f);
        decoderSettings.get("Sigma DP2 Merrill").setBrightNess(6.0f);

        Parameters parameters = getParameters();
        String file = parameters.getRaw().get(0);
        String file2 = parameters.getRaw().get(1);
        String file3 = parameters.getRaw().get(2);

        VBox stack = new VBox();
        stack.setPadding(new Insets(10));
        stack.setSpacing(5);
        //stack.setAlignment(Pos.TOP_CENTER);
        loadImagesByStream(stack, file, file2, file3);
        //loadImagesByFile(stack, file2);

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
        stack.getChildren().add(new ProgressBar());
        loadImages(stack, initialFile, initialFile2, initialFile3);

        btn.setOnAction((t) -> {
            stack.getChildren().clear();
            stack.getChildren().add(btn);
            final ProgressBar pg=new ProgressBar();
            stack.getChildren().add(pg);
            Platform.runLater(() -> {
                try {
                    loadImages(stack, initialFile, initialFile2, initialFile3);
                    stack.getChildren().remove(pg);
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
                HBox hb = new HBox();
                stack.getChildren().add(hb);
                hb.getChildren().add(view);
                Platform.runLater(() -> {
                    try {
                        HashMap<String, RawDecoderSettings> settings = new HashMap<>();
                        settings.put("Default", new RawDecoderSettings());
                        long start=System.currentTimeMillis();
                        HashMap<String, String> metaData = new LibrawImage(initialFile.getAbsolutePath(), settings).getMetaData();
                        long end=System.currentTimeMillis();
                        System.out.println("meta file: "+(end-start)+" ms");
                        ScrollPane sc = new ScrollPane();
                        TextArea vb = new TextArea();
                        metaData.entrySet().forEach((entry) -> {
                            //Label l = new Label(entry.getKey() + " " + entry.getValue());
                            vb.appendText(entry.getKey() + " " + entry.getValue() + "\n");
                            //vb.getChildren().add(l);
                        });
                        sc.setContent(vb);
                        hb.getChildren().add(sc);
                    } catch (IOException ex) {
                        Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
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
                HBox hb = new HBox();
                stack.getChildren().add(hb);
                hb.getChildren().add(view2);
                Platform.runLater(() -> {
                    try {
                        HashMap<String, RawDecoderSettings> settings = new HashMap<>();
                        settings.put("Default", new RawDecoderSettings());
                        long start=System.currentTimeMillis();
                        HashMap<String, String> metaData = new LibrawImage(initialFile2.getAbsolutePath(), settings).getMetaData();
                        long end=System.currentTimeMillis();
                        System.out.println("meta file: "+(end-start)+" ms");
                        ScrollPane sc = new ScrollPane();
                        TextArea vb = new TextArea();
                        metaData.entrySet().forEach((entry) -> {
                            //Label l = new Label(entry.getKey() + " " + entry.getValue());
                            vb.appendText(entry.getKey() + " " + entry.getValue() + "\n");
                            //vb.getChildren().add(l);
                        });
                        sc.setContent(vb);
                        hb.getChildren().add(sc);
                    } catch (IOException ex) {
                        Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
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
                HBox hb = new HBox();
                stack.getChildren().add(hb);
                hb.getChildren().add(view3);
                Platform.runLater(() -> {
                    try {
                        HashMap<String, RawDecoderSettings> settings = new HashMap<>();
                        settings.put("Default", new RawDecoderSettings());
                        long start=System.currentTimeMillis();
                        HashMap<String, String> metaData = new LibrawImage(initialFile3.getAbsolutePath(), settings).getMetaData();
                        long end=System.currentTimeMillis();
                        System.out.println("meta file: "+(end-start)+" ms");
                        ScrollPane sc = new ScrollPane();
                        TextArea vb = new TextArea();
                        metaData.entrySet().forEach((entry) -> {
                            //Label l = new Label(entry.getKey() + " " + entry.getValue());
                            vb.appendText(entry.getKey() + " " + entry.getValue() + "\n");
                            //vb.getChildren().add(l);
                        });
                        sc.setContent(vb);
                        hb.getChildren().add(sc);
                    } catch (IOException ex) {
                        Logger.getLogger(TestApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
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

    private void loadImagesByFile(VBox stack, String file) throws IOException {
        //stack.getChildren().add(ind);
        //ind.progressProperty().bind(img.progressProperty());
        for (int i = 0; i < 1; i++) {
            File initialFile = new File(file);
            HashMap<String, RawDecoderSettings> settings = new HashMap<>();
            settings.put("Default", new RawDecoderSettings());
            settings.put("Sigma DP2 Merrill", new RawDecoderSettings());
            settings.get("Sigma DP2 Merrill").setEnableExposureCorrection(true);
            settings.get("Sigma DP2 Merrill").setExposureCorrection(1);
            settings.get("Sigma DP2 Merrill").setExpoCorrectionShift(2.0f);
            settings.get("Sigma DP2 Merrill").setBrightNess(6.0f);
            LibrawImage libraw = new LibrawImage(initialFile.getAbsolutePath(), settings);
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
        args[2] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW-ADOBE_DNG_Sample.dng";
        args[1] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW_SIGMA_DP2_MERRILL.X3F";
        args[0] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW_fujifilm_x_t2-Sample.raf";
        Logger logger = Logger.getLogger("");
        Handler handler = new ConsoleHandler();
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        launch(args);
    }

}
