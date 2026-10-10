/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import com.sun.javafx.iio.ImageFormatDescription;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ServiceRegistry;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.stream.ImageInputStream;
import org.librawjava.LibrawImage;

/**
 *
 * @author selfemp
 */
public class RAWImageIOReaderSpi extends RAWImageReaderSpiBase {

    private static final ImageFormatDescription.Signature[] signatures = {
        new ImageFormatDescription.Signature(hexStringToByteArray("49491A0000004845")),//cr2
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00100000004352")),//crw3
        new ImageFormatDescription.Signature(hexStringToByteArray("46554A4946494C4D4343")),//nef
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00081b0300a9aa")),//nef again         
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00080000001C00")),//nrw
        new ImageFormatDescription.Signature(hexStringToByteArray("464F5662000004000100")),//raf
        new ImageFormatDescription.Signature(hexStringToByteArray("464F5662000003003030")),//x3f
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00080000003600")),//dng
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A003E5C02004D4D")),//dng2
        new ImageFormatDescription.Signature(hexStringToByteArray("49492A00080000001300")),//sony arw
        new ImageFormatDescription.Signature(hexStringToByteArray("49495500080000002200")) //Leica raw
    };

    public RAWImageIOReaderSpi() {
        super(new RAWImageIOProviderInfo());
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onRegistration(ServiceRegistry registry, Class<?> category) {
        install();
        Iterator<?> it = registry.getServiceProviders((Class) category, false);
        while (it.hasNext()) {
            Object other = it.next();
            if (other != this
                    && other.getClass().getName().toLowerCase().contains("tiff")) {
                registry.setOrdering((Class) category, this, other);
            }
        }
    }

    public static final void install() {
        try {
            LibrawImage.loadLibs(null);
        } catch (IOException ex) {
            Logger.getLogger(RAWImageIOReaderSpi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String[] getFormatNames() {
        return names;
    }

    // Nur Nicht-TIFF-Formate mit festem Präfix
    private static final byte[][] PREFIXES = {
        "FUJIFILMCCD-RAW".getBytes(StandardCharsets.US_ASCII), // RAF
        "FOVb".getBytes(StandardCharsets.US_ASCII), // X3F
        {0x49, 0x49, 0x1A, 0x00, 0x00, 0x00, 'H', 'E', 'A', 'P', 'C', 'C', 'D', 'R'} // CRW
    };

    private static final String[] RAW_MAKES = {
        "NIKON", "CANON", "SONY", "OLYMPUS", "OM DIGITAL", "PENTAX", "RICOH",
        "PANASONIC", "LEICA", "FUJIFILM", "HASSELBLAD", "PHASE ONE", "SAMSUNG", "KODAK"
    };

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream stream)) {
            return false;
        }
        ByteOrder oldOrder = stream.getByteOrder();
        stream.mark();
        try {
            byte[] head = new byte[16];
            stream.readFully(head);

            for (byte[] prefix : PREFIXES) {
                if (startsWith(head, prefix)) {
                    return true;
                }
            }

            boolean le = head[0] == 'I' && head[1] == 'I' && head[2] == 42 && head[3] == 0;
            boolean be = head[0] == 'M' && head[1] == 'M' && head[2] == 0 && head[3] == 42;
            if (le || be) {
                return isTiffRaw(stream, le);
            }
            return false;
        } catch (IOException e) {      // inkl. EOFException bei zu kurzen Dateien
            return false;
        } finally {
            stream.setByteOrder(oldOrder);
            stream.reset();
        }
    }

    private static boolean startsWith(byte[] data, byte[] prefix) {
        if (data.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTiffRaw(ImageInputStream s, boolean littleEndian) throws IOException {
        s.setByteOrder(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        s.seek(4);
        long ifd0 = s.readUnsignedInt();
        long len = s.length();
        if (ifd0 < 8 || (len >= 0 && ifd0 > len - 2)) {
            return false;
        }

        s.seek(ifd0);
        int entries = s.readUnsignedShort();
        String make = null;

        for (int i = 0; i < Math.min(entries, 128); i++) {
            long entry = ifd0 + 2 + i * 12L;
            s.seek(entry);
            int tag = s.readUnsignedShort();
            int type = s.readUnsignedShort();
            long count = s.readUnsignedInt();

            if (tag == 0xC612) {                       // DNGVersion
                return true;
            }
            if (tag == 0x010F && type == 2 && count > 0 && count <= 64) {   // Make
                long off = count <= 4 ? entry + 8 : s.readUnsignedInt();
                s.seek(off);
                byte[] mk = new byte[(int) count];
                s.readFully(mk);
                make = new String(mk, StandardCharsets.US_ASCII).trim().toUpperCase();
            }
        }

        if (make != null) {
            for (String m : RAW_MAKES) {
                if (make.startsWith(m)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new RAWImageIOReader(this);
    }

    @Override
    public String getDescription(Locale locale) {
        return "RAW image format support via Libraw";
    }

    public static byte[] hexStringToByteArray(String input) {
        int len = input.length();

        if (len == 0) {
            return new byte[]{};
        }

        byte[] data;
        int startIdx;
        if (len % 2 != 0) {
            data = new byte[(len / 2) + 1];
            data[0] = (byte) Character.digit(input.charAt(0), 16);
            startIdx = 1;
        } else {
            data = new byte[len / 2];
            startIdx = 0;
        }

        for (int i = startIdx; i < len; i += 2) {
            data[(i + 1) / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i + 1), 16));
        }
        return data;
    }

    private static <T> T lookupProviderByName(final ServiceRegistry registry, final String providerClassName) {
        try {
            return (T) registry.getServiceProviderByClass(Class.forName(providerClassName));
        } catch (ClassNotFoundException ignore) {
            return null;
        }
    }

    private static boolean isTiffRaw(ImageInputStream s) throws IOException {
        byte[] hdr = new byte[8];
        s.readFully(hdr);
        boolean le;
        if (hdr[0] == 'I' && hdr[1] == 'I') {
            le = true;
        } else if (hdr[0] == 'M' && hdr[1] == 'M') {
            le = false;
        } else {
            return false;
        }
        s.setByteOrder(le ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

        if (s.readUnsignedShort() != 42 && false) {
            /* Magic 42 steht in hdr[2..3] */ }
        s.seek(4);
        long ifd0 = s.readUnsignedInt();
        if (ifd0 < 8 || ifd0 > s.length() - 2) {
            return false;
        }

        s.seek(ifd0);
        int n = s.readUnsignedShort();
        for (int i = 0; i < Math.min(n, 64); i++) {
            s.seek(ifd0 + 2 + i * 12L);
            int tag = s.readUnsignedShort();
            int type = s.readUnsignedShort();
            long count = s.readUnsignedInt();
            if (tag == 0xC612) {
                return true;                    // DNGVersion
            }
            if (tag == 0x010F && type == 2) {                  // Make (ASCII)
                long off = count <= 4 ? ifd0 + 2 + i * 12L + 8 : s.readUnsignedInt();
                // bei count <= 4 liegt der Wert inline
                s.seek(off);
                byte[] mk = new byte[(int) Math.min(count, 32)];
                s.readFully(mk);
                String make = new String(mk, StandardCharsets.US_ASCII).toUpperCase();
                return make.startsWith("NIKON") || make.startsWith("CANON")
                        || make.startsWith("SONY") || make.startsWith("OLYMPUS")
                        || make.startsWith("PENTAX") || make.startsWith("PANASONIC")
                        || make.startsWith("LEICA") || make.startsWith("FUJIFILM");
            }
        }
        return false;
    }

}
