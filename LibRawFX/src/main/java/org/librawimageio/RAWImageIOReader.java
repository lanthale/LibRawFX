/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import org.librawjava.LibrawImage;
import org.librawdecodersettings.RawDecoderSettings;

/**
 *
 * @author selfemp
 */
public class RAWImageIOReader extends ImageReader {

    private final LibrawImage libraw;
    ImageInputStream stream = null;
    int width, height;
    int colorType;

    // Constants enumerating the values of colorType
    static final int COLOR_TYPE_GRAY = 0;
    static final int COLOR_TYPE_RGB = 1;

    boolean gotHeader = false;
    RAWIOFormatMetadata metadata = null; // class defined below

    private static final HashMap<String, RawDecoderSettings> settings = new HashMap<>();

    public RAWImageIOReader() {
        super(new RAWImageIOReaderSpi());
        initSettings();
        libraw = new LibrawImage(this, settings);
    }

    public RAWImageIOReader(ImageReaderSpi originatingProvider) {
        super(originatingProvider);
        initSettings();
        libraw = new LibrawImage(this, settings);
    }

    @Override
    public void setInput(Object input) {
        super.setInput(input);
        stream = (ImageInputStream) input;
    }

    @Override
    public void setInput(Object input, boolean seekForwardOnly, boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        stream = (ImageInputStream) input;
    }

    @Override
    public int getNumImages(boolean allowSearch) throws IOException {
        return 1; // format can only encode a single image
    }

    private void checkIndex(int imageIndex) {
        if (imageIndex != 0) {
            throw new IndexOutOfBoundsException("bad index");
        }
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        if (libraw.getImageWidth() == 0) {
            getBasicMetaData();
            width = libraw.getImageWidth();
        } else {
            height = libraw.getImageWidth();
        }
        return width;
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        if (libraw.getImageHeight() == 0) {
            getBasicMetaData();
            height = libraw.getImageHeight();
        } else {
            height = libraw.getImageHeight();
        }
        return height;
    }

    public void readHeader() throws IIOException {
        if (gotHeader) {
            return;
        }
        gotHeader = true;

        if (stream == null) {
            throw new IllegalStateException("No input stream");
        }
        // Read width, height, color type, newline
        try {
            libraw.getMetaData();
            this.width = libraw.getImageWidth();
            this.height = libraw.getImageWidth();
            this.colorType = COLOR_TYPE_RGB;
        } catch (IOException e) {
            throw new IIOException("Error reading header", e);
        }
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        //readHeader();
        colorType = COLOR_TYPE_RGB;

        ImageTypeSpecifier imageType = null;
        int datatype = DataBuffer.TYPE_BYTE;
        java.util.List l = new ArrayList();
        switch (colorType) {
            case COLOR_TYPE_GRAY:
                imageType = ImageTypeSpecifier.createGrayscale(8,
                        datatype,
                        false);
                break;

            case COLOR_TYPE_RGB:
                ColorSpace rgb
                        = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                int[] bandOffsets = new int[3];
                bandOffsets[0] = 0;
                bandOffsets[1] = 1;
                bandOffsets[2] = 2;
                imageType
                        = ImageTypeSpecifier.createInterleaved(rgb,
                                bandOffsets,
                                datatype,
                                false,
                                false);
                break;
        }
        l.add(imageType);
        return l.iterator();
    }

    private void getBasicMetaData() {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] datab = new byte[1024];
            long reading = System.currentTimeMillis();
            while ((nRead = stream.read(datab, 0, datab.length)) != -1) {
                buffer.write(datab, 0, nRead);
            }
            buffer.flush();
            byte[] targetArray = buffer.toByteArray();
            libraw.getBasicMetaInfo(targetArray);
            stream.seek(0);
        } catch (IOException ex) {
            Logger.getLogger(RAWImageIOReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ImageReadParam getDefaultReadParam() {
        return new RAWImageIOImageReadParam();
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        if (imageIndex != 0) {
            throw new IndexOutOfBoundsException("imageIndex != 0!");
        }
        //readMetadata();
        return metadata;
    }

    @Override
    public BufferedImage read(int imageIndex) throws IOException {
        return super.read(imageIndex);
    }

    public void readMetadata() throws IIOException {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] datab = new byte[1024];
            long reading = System.currentTimeMillis();
            while ((nRead = stream.read(datab, 0, datab.length)) != -1) {
                buffer.write(datab, 0, nRead);
            }
            buffer.flush();
            byte[] targetArray = buffer.toByteArray();
            HashMap<String, String> metaData = libraw.getMetaData(targetArray);
            width = libraw.getImageWidth();
            height = libraw.getImageHeight();
            stream.seek(0);
            this.metadata = new RAWIOFormatMetadata();
            metaData.entrySet().forEach((var entry) -> {
                String key = entry.getKey();
                String value = entry.getValue();
                metadata.keywords.add(key);
                metadata.values.add(value);
            });
        } catch (IOException ex) {
            Logger.getLogger(RAWImageIOReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initSettings() {
        if (settings.isEmpty()) {
            settings.put("Default", new RawDecoderSettings());
        }
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] datab = new byte[1024];
        long reading = System.currentTimeMillis();
        if (width == 0) {
            getWidth(0);
        }
        if (height == 0) {
            getHeight(0);
        }
        stream.seek(0);

        while ((nRead = stream.read(datab, 0, datab.length)) != -1) {
            buffer.write(datab, 0, nRead);
        }
        buffer.flush();
        byte[] targetArray = buffer.toByteArray();
        byte[] raw = libraw.readPixelDataFromStream(targetArray);
        double diff = (System.currentTimeMillis() - reading) / 1000;
        Logger.getLogger(RAWImageIOReader.class.getName()).log(Level.FINE, null, "Raw convert took: " + diff + "s");

        stream.seek(0);
        readMetadata(); // Stream is positioned at start of image data
// Compute initial source region, clip against destination later
        Rectangle sourceRegion = getSourceRegion(param, width, height);

        // Set everything to default values
        int sourceXSubsampling = 1;
        int sourceYSubsampling = 1;
        int[] sourceBands = null;
        int[] destinationBands = null;
        Point destinationOffset = new Point(0, 0);

        // Get values from the ImageReadParam, if any
        if (param != null) {
            sourceXSubsampling = param.getSourceXSubsampling();
            sourceYSubsampling = param.getSourceYSubsampling();
            sourceBands = param.getSourceBands();
            destinationBands = param.getDestinationBands();
            destinationOffset = param.getDestinationOffset();
        }
        // Get the specified detination image or create a new one
        /*BufferedImage dst = getDestination(param,
                getImageTypes(0),
                width, height);*/

        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);

        ColorModel model = new ComponentColorModel(
                colorSpace,
                false,
                true,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE
        );
        WritableRaster raster = Raster.createInterleavedRaster(
                DataBuffer.TYPE_BYTE,
                width,
                height,
                3,
                null
        );

        BufferedImage dst = new BufferedImage(model, raster, true, null);
        dst.setData(raster);
        byte[] imagePixels = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        System.arraycopy(raw, 0, imagePixels, 0, raw.length);
        // Enure band settings from param are compatible with images

        double dstWidth = 0;
        double dstHeight = 0;
        if (param != null) {
            if (param.getSourceRenderSize() != null) {
                dstWidth = param.getSourceRenderSize().getWidth();
                dstHeight = param.getSourceRenderSize().getHeight();
            } else {
                param.setSourceRenderSize(new Dimension(width, height));
                dstWidth = width;
                dstHeight = height;
            }
        }

        BufferedImage resized = new BufferedImage((int) dstWidth, (int) dstHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(dst, 0, 0, (int) dstWidth, (int) dstHeight, 0, 0, dst.getWidth(),
                dst.getHeight(), null);
        g.dispose();

        return resized;
    }

}
