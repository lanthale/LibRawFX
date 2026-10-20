package org.librawimageio;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageReader;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageInputStream;
import org.librawjava.RawFormatDetector;
import org.librawjava.LibrawImage;

/**
 * ImageIO SPI. Nicht-TIFF-Formate werden über RawFormatDetector erkannt.
 *
 * TIFF-Container werden IMMER übernommen. Grund: JavaFX' J2DImageLoader stürzt
 * bei Readern, die sourceRenderSize nicht unterstützen (JDK-/TwelveMonkeys-TIFF-
 * Reader), beim Skalieren mit einer ClassCastException ab. RAWImageIOReader
 * unterstützt sourceRenderSize, entscheidet selbst (RAW über LibRaw, sonst
 * Delegation an den normalen TIFF-Reader) und liefert immer die exakte Größe.
 * Voraussetzung: dieser SPI muss VOR den TIFF-Readern einsortiert sein.
 */
public class RAWImageIOReaderSpi extends RAWImageReaderSpiBase {

    private static final int QUICK_HEAD = 16;

    public RAWImageIOReaderSpi() {
        super(new RAWImageIOProviderInfo());
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onRegistration(ServiceRegistry registry, Class<?> category) {
        install();
        // Vor TIFF-Readern einsortieren, damit z. B. DNG nicht dort landet.
        // canDecodeInput lehnt normale TIFFs ab, sie fallen also an den TIFF-Reader durch.
        Iterator<?> it = registry.getServiceProviders((Class) category, false);
        while (it.hasNext()) {
            Object other = it.next();
            if (other != this
                    && other.getClass().getName().toLowerCase(Locale.ROOT).contains("tiff")) {
                registry.setOrdering((Class) category, this, other);
            }
        }
    }

    public static void install() {
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

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream stream)) {
            return false;
        }
        ByteOrder oldOrder = stream.getByteOrder();
        stream.mark();
        try {
            // Schneller Pfad: nur 16 Bytes. JPEG, PNG usw. sind damit sofort erledigt.
            byte[] head = readUpTo(stream, QUICK_HEAD);
            if (head.length < QUICK_HEAD) {
                return false;
            }
            if (isTiffHeader(head)) {
                return true;                            // der Reader entscheidet: RAW oder normales TIFF
            }
            return RawFormatDetector.isRaw(head);       // RAF, X3F, CRW, CR3, ORF, RW2, MRW, IIQ
        } catch (IOException e) {
            return false;
        } finally {
            stream.setByteOrder(oldOrder);
            stream.reset();
        }
    }

    private static boolean isTiffHeader(byte[] h) {
        return (h[0] == 'I' && h[1] == 'I' && h[2] == 42 && h[3] == 0)
                || (h[0] == 'M' && h[1] == 'M' && h[2] == 0 && h[3] == 42);
    }

    private static byte[] readUpTo(ImageInputStream s, int len) throws IOException {
        byte[] buf = new byte[len];
        int n = 0;
        while (n < len) {
            int r = s.read(buf, n, len - n);
            if (r < 0) {
                break;
            }
            n += r;
        }
        return n == len ? buf : Arrays.copyOf(buf, n);
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new RAWImageIOReader(this);
    }

    @Override
    public String getDescription(Locale locale) {
        return "RAW image format support via Libraw";
    }
}