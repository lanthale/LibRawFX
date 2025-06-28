/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javafx.application.Application;
import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author selfemp
 */
public class TestAppSwing extends JFrame {

    public TestAppSwing() {
        String[] args;
        IIORegistry registry = IIORegistry.getDefaultInstance();
        registry.registerServiceProvider(new RAWImageIOReaderSpi());
        Iterator<Class<?>> categories = registry.getCategories();
        // JFrame initialisieren
        setTitle("Test Raw Images via ImageIO");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Bild laden
        BufferedImage img = null;
        Image scaledImage = null;
        BufferedImage scaledBufferedImage = null;
        try {
            args = new String[3];
            args[2] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW-ADOBE_DNG_Sample.dng";
            args[1] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW_SIGMA_DP2_MERRILL.X3F";
            args[0] = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "ressources" + File.separator + "RAW_fujifilm_x_t2-Sample.raf";
            String file = args[0];
            img = ImageIO.read(new File(file));
            scaledImage = img.getScaledInstance(600, 300, BufferedImage.SCALE_DEFAULT);
            scaledBufferedImage = new BufferedImage(600, 300, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledBufferedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Bild in einem JLabel anzeigen
        JLabel picLabel = new JLabel(new ImageIcon(scaledBufferedImage));
        add(picLabel);

        // JFrame anzeigen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Erstelle eine Instanz der Klasse BildLaden
        new TestAppSwing();
    }

}
