/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawfx;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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
import static org.librawfx.RAWImageLoader.initSettings;

/**
 *
 * @author selfemp
 */
public class RAWImageIOReader extends ImageReader {

    private LibrawImage libraw;
    ImageInputStream stream = null;
    int width, height;
    int colorType;

    // Constants enumerating the values of colorType
    static final int COLOR_TYPE_GRAY = 0;
    static final int COLOR_TYPE_RGB = 1;

    boolean gotHeader = false;
    RAWIOFormatMetadata metadata = null; // class defined below
    
    private static HashMap<String, RawDecoderSettings> settings=new HashMap<>();

    public RAWImageIOReader(ImageReaderSpi originatingProvider) {
        super(originatingProvider);
        initSettings();
        libraw = new LibrawImage(this, settings);
    }

    public void setInput(Object input, boolean isStreamable) {
        super.setInput(input, isStreamable);
        if (input == null) {
            this.stream = null;
            return;
        }
        if (input instanceof ImageInputStream) {
            this.stream = (ImageInputStream) input;
        } else {
            throw new IllegalArgumentException("bad input");
        }
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
        width=libraw.getImageWidth();        
        return width;
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        height=libraw.getImageHeight();        
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

        // Read `myformat\n' from the stream
        byte[] signature = new byte[9];
        try {
            stream.readFully(signature);
        } catch (IOException e) {
            throw new IIOException("Error reading signature", e);
        }
        if (signature[0] != (byte) 'm') { // etc.
            throw new IIOException("Bad file signature!");
        }
        // Read width, height, color type, newline
        try {
            this.width = libraw.getImageWidth();
            this.height = libraw.getImageWidth();
            this.colorType = COLOR_TYPE_RGB;
            stream.readUnsignedByte(); // skip newline character
        } catch (IOException e) {
            throw new IIOException("Error reading header", e);
        }
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        checkIndex(imageIndex);
        readHeader();

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

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        if (imageIndex != 0) {
            throw new IndexOutOfBoundsException("imageIndex != 0!");
        }
        readMetadata();
        return metadata;
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] datab = new byte[1024];
        long reading = System.currentTimeMillis();
        while ((nRead = stream.read(datab, 0, datab.length)) != -1) {
            buffer.write(datab, 0, nRead);
        }
        buffer.flush();
        byte[] targetArray = buffer.toByteArray();
        byte[] raw = libraw.readPixelDataFromStream(targetArray);
        double diff = (System.currentTimeMillis() - reading) / 1000;
        Logger.getLogger(RAWImageIOReader.class.getName()).log(Level.FINE, null, "Raw convert took: " + diff + "s");
        int[] rgbData = libraw.convertToINT(raw);
                
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
        BufferedImage dst = getDestination(param,
                getImageTypes(0),
                libraw.getImageWidth(), libraw.getImageHeight());
        // Enure band settings from param are compatible with images
        int inputBands = (colorType == COLOR_TYPE_RGB) ? 3 : 1;
        checkReadParamBandSettings(param, inputBands,
                dst.getSampleModel().getNumBands());
        int[] bandOffsets = new int[inputBands];
        for (int i = 0; i < inputBands; i++) {
            bandOffsets[i] = i;
        }
        int bytesPerRow = width * inputBands;
        DataBufferByte rowDB = new DataBufferByte(bytesPerRow);
        WritableRaster rowRas
                = Raster.createInterleavedRaster(rowDB,
                        width, 1, bytesPerRow,
                        inputBands, bandOffsets,
                        new Point(0, 0));
        byte[] rowBuf = rowDB.getData();

        // Create an int[] that can a single pixel
        int[] pixel = rowRas.getPixel(0, 0, (int[]) null);
        WritableRaster imRas = dst.getWritableTile(0, 0);
        int dstMinX = imRas.getMinX();
        int dstMaxX = dstMinX + imRas.getWidth() - 1;
        int dstMinY = imRas.getMinY();
        int dstMaxY = dstMinY + imRas.getHeight() - 1;
        
        //imRas.set

        // Create a child raster exposing only the desired source bands
        if (sourceBands != null) {
            rowRas = rowRas.createWritableChild(0, 0,
                    width, 1,
                    0, 0,
                    sourceBands);
        }

        // Create a child raster exposing only the desired dest bands
        if (destinationBands != null) {
            imRas = imRas.createWritableChild(0, 0,
                    imRas.getWidth(),
                    imRas.getHeight(),
                    0, 0,
                    destinationBands);
        }

        for (int srcY = 0; srcY < height; srcY++) {
            // Read the row
            try {
                stream.readFully(rowBuf);
            } catch (IOException e) {
                throw new IIOException("Error reading line " + srcY, e);
            }

            // Reject rows that lie outside the source region,
            // or which aren't part of the subsampling
            if ((srcY < sourceRegion.y)
                    || (srcY >= sourceRegion.y + sourceRegion.height)
                    || (((srcY - sourceRegion.y)
                    % sourceYSubsampling) != 0)) {
                continue;
            }

            // Determine where the row will go in the destination
            int dstY = destinationOffset.y
                    + (srcY - sourceRegion.y) / sourceYSubsampling;
            if (dstY < dstMinY) {
                continue; // The row is above imRas
            }
            if (dstY > dstMaxY) {
                break; // We're done with the image
            }

            // Copy each (subsampled) source pixel into imRas
            for (int srcX = sourceRegion.x;
                    srcX < sourceRegion.x + sourceRegion.width;
                    srcX++) {
                if (((srcX - sourceRegion.x) % sourceXSubsampling) != 0) {
                    continue;
                }
                int dstX = destinationOffset.x
                        + (srcX - sourceRegion.x) / sourceXSubsampling;
                if (dstX < dstMinX) {
                    continue;  // The pixel is to the left of imRas
                }
                if (dstX > dstMaxX) {
                    break; // We're done with the row
                }

                // Copy the pixel, sub-banding is done automatically
                rowRas.getPixel(srcX, 0, pixel);
                imRas.setPixel(dstX, dstY, pixel);
            }
        }
        return dst;
    }

    public void readMetadata() throws IIOException {
        if (metadata != null) {
            return;
        }
        readHeader();
        this.metadata = new RAWIOFormatMetadata();

        try {
            while (true) {

                String keyword = stream.readUTF();
                stream.readUnsignedByte();
                if (keyword.equals("END")) {
                    break;
                }
                String value = stream.readUTF();
                stream.readUnsignedByte();

                metadata.keywords.add(keyword);
                metadata.values.add(value);
            }
        } catch (IOException ex) {
            Logger.getLogger(RAWImageIOReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
