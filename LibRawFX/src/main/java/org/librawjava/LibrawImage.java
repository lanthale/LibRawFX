package org.librawjava;

import java.io.File;
import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.libraw.nativ.libraw_data_t;
import org.libraw.nativ.libraw_gps_info_t;
import org.libraw.nativ.libraw_h;
import org.libraw.nativ.libraw_imgother_t;
import org.libraw.nativ.libraw_iparams_t;
import org.libraw.nativ.libraw_lensinfo_t;
import org.libraw.nativ.libraw_makernotes_lens_t;
import org.libraw.nativ.libraw_processed_image_t;
import org.libraw.nativ.libraw_shootinginfo_t;
import org.librawdecodersettings.RawDecoderSettings;
import org.librawdecodersettings.RawDecoderToNativeTranslator;
import org.librawfx.RAWImageLoader;
import org.librawimageio.RAWImageIOReader;

/**
 * Loads the native libraw lib and processes images in native space. Entry point
 * of the whole libraw wrapper.
 *
 * Threading: one instance per loader/reader and one thread at a time. The
 * result fields (width, height, ...) are written by the decode calls and are
 * volatile, but they describe the LAST call of this instance.
 *
 * @author Clemens Lanthaler
 */
public class LibrawImage {

    private static final Logger LOG = Logger.getLogger(LibrawImage.class.getName());

    /** Receives decode progress (rows done / total rows). */
    @FunctionalInterface
    public interface ProgressListener {

        void onProgress(int done, int total);
    }

    /** Opens a source (file or buffer) on an initialised libraw handle. */
    @FunctionalInterface
    private interface NativeOpen {

        int open(MemorySegment iprc, Arena arena);
    }

    // ---- static state -------------------------------------------------------
    private static volatile boolean loaded = false;
    private static volatile String operatingSystem;

    // ---- instance state -----------------------------------------------------
    private final ConcurrentHashMap<String, RawDecoderSettings> rawSettings;
    private final RAWImageLoader loader;
    private final RAWImageIOReader imageIOReader;
    private volatile ProgressListener progressListener;
    private volatile RawDecoderSettings settingsOverride;

    private volatile String imageFileURL;
    private volatile int imageWidth;
    private volatile int imageHeight;
    private volatile int imageBits;
    private volatile int imageColors;
    private volatile int stride;
    private volatile String cameraModel;
    private volatile LocalDateTime shootingDateTime;

    public LibrawImage(String imageFile, ConcurrentHashMap<String, RawDecoderSettings> settings) {
        this.imageFileURL = imageFile;
        this.rawSettings = settings;
        this.loader = null;
        this.imageIOReader = null;
    }

    public LibrawImage(RAWImageLoader loader, ConcurrentHashMap<String, RawDecoderSettings> settings) {
        this.loader = loader;
        this.rawSettings = settings;
        this.imageIOReader = null;
        if (loader != null) {
            this.progressListener = loader::updateImageProgress;
        }
    }

    public LibrawImage(RAWImageIOReader imageIOReader, ConcurrentHashMap<String, RawDecoderSettings> settings) {
        this.imageIOReader = imageIOReader;
        this.rawSettings = settings;
        this.loader = null;
    }

    // ==========================================================================
    // Native library loading
    // ==========================================================================
    /**
     * Unpacks all native libs into a temp directory and loads them. Safe to
     * call multiple times; only the first call does the work.
     *
     * @param tempDir directory where the native libs are placed (null = default)
     * @throws IOException if the libs cannot be unpacked/loaded or the platform
     * is unsupported
     */
    public static synchronized void loadLibs(String tempDir) throws IOException {
        if (loaded) {
            return;
        }
        LOG.log(Level.FINEST, "Init native libs...");
        String os = System.getProperty("os.name").toUpperCase();
        String arch = System.getProperty("os.arch").toUpperCase();
        LOG.log(Level.FINEST, () -> "OS was: " + os);

        String[] libs;
        if (os.contains("WIN")) {
            libs = NativeUtils.loadLibraryFromJar(tempDir, "/lib/win-x86_64/libraw.dll", "/lib/win-x86_64/libjpeg.dll", "/lib/win-x86_64/zlib.dll");
        } else if (os.contains("MAC") && !arch.contains("AARCH64")) {
            libs = NativeUtils.loadLibraryFromJar(tempDir, "/lib/osx-x86_64/liblcms2.2.dylib", "/lib/osx-x86_64/libjasper.4.dylib", "/lib/osx-x86_64/libjpeg.9.dylib", "/lib/osx-x86_64/libz.1.dylib", "/lib/osx-x86_64/libraw.22.dylib");
        } else if (os.contains("MAC")) {
            libs = NativeUtils.loadLibraryFromJar(tempDir, "/lib/osx-arm64/liblcms2.2.dylib", "/lib/osx-arm64/libjasper.7.dylib", "/lib/osx-arm64/libjpeg.8.dylib", "/lib/osx-arm64/libraw_r.23.dylib");
        } else if (os.contains("NUX")) {
            libs = NativeUtils.loadLibraryFromJar(tempDir, "/lib/linux-x86_64/libgomp.so.1", "/lib/linux-x86_64/liblcms2.so.2", "/lib/linux-x86_64/libjpeg.so.62", "/lib/linux-x86_64/libraw.so.24");
        } else {
            throw new IOException("Unsupported OS/arch: " + os + " / " + arch);
        }
        if (libs == null) {
            throw new IOException("Native libs could not be extracted for " + os + " / " + arch);
        }

        LOG.log(Level.FINEST, () -> "loadLibraryFromJar: " + Arrays.toString(libs) + " , tempdir: " + tempDir);
        for (String part : libs) {
            new File(part).deleteOnExit();
        }
        for (String part : libs) {
            System.load(part);
        }
        operatingSystem = os;
        loaded = true;
        LOG.log(Level.FINEST, "Init native libs...finished");
    }

    private static void ensureLoaded() {
        if (!loaded) {
            LOG.log(Level.SEVERE, "Please call loadLibs as static method first!");
            throw new IllegalStateException("Please call loadLibs as static method first!");
        }
    }

    // ==========================================================================
    // Settings / progress
    // ==========================================================================
    /**
     * Explicit settings for the next decodes of this instance. Takes precedence
     * over model specific entries and "Default". null = standard lookup.
     */
    public void setSettingsOverride(RawDecoderSettings settings) {
        this.settingsOverride = settings;
    }

    public void setProgressListener(ProgressListener listener) {
        this.progressListener = listener;
    }

    private RawDecoderSettings pickSettings(String makeAndModel) {
        RawDecoderSettings s = settingsOverride;
        if (s == null) {
            s = rawSettings.get(makeAndModel);
        }
        if (s == null) {
            s = rawSettings.get("Default");
        }
        if (s == null) {
            s = new RawDecoderSettings();
        }
        return s;
    }

    private void progress(int done, int total) {
        ProgressListener l = progressListener;
        if (l != null) {
            l.onProgress(done, total);
        }
    }

    // ==========================================================================
    // Decoding
    // ==========================================================================
    /**
     * Decodes the raw file given as bytes into 8 bit RGB pixels.
     *
     * @param sourceFileAsByteArray the complete file
     * @return interleaved RGB bytes (width * height * 3)
     * @throws IOException if the file cannot be opened or decoded
     */
    public byte[] readPixelDataFromStream(byte[] sourceFileAsByteArray) throws IOException {
        if (sourceFileAsByteArray == null) {
            LOG.log(Level.SEVERE, "sourceFileAsByteArray == null!");
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        return decode((iprc, arena) -> libraw_h.libraw_open_buffer(iprc,
                arena.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray),
                sourceFileAsByteArray.length), "file stream");
    }

    /**
     * Decodes the file given by {@link #setImageFileURL(String)}.
     *
     * @return ARGB int pixels
     * @throws IOException if the given file cannot be read
     */
    public synchronized int[] readPixelData() throws IOException {
        final String url = imageFileURL;
        if (url == null) {
            LOG.log(Level.SEVERE, "imageFileURL == null!");
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        byte[] rgb = decode((iprc, arena) -> libraw_h.libraw_open_file(iprc, arena.allocateFrom(url)),
                "file '" + url + "'");
        return convertToINT(rgb);
    }

    private byte[] decode(NativeOpen opener, String source) throws IOException {
        ensureLoaded();
        try (Arena scope = Arena.ofConfined()) {          // confined: cheaper to close than shared
            MemorySegment iprc = libraw_h.libraw_init(0);
            if (isNull(iprc)) {
                throw new IOException("libraw_init failed");
            }
            MemorySegment memImage = MemorySegment.NULL;
            try {
                progress(0, 1);
                int k = opener.open(iprc, scope);
                if (k != 0) {
                    throw new IOException("Cannot open " + source + ", LibRaw return value was: " + k);
                }
                k = libraw_h.libraw_unpack(iprc);
                if (k < 0) {
                    throw new IOException("libraw_unpack failed, return value was: " + k);
                }

                MemorySegment iParams = libraw_h.libraw_get_iparams(iprc);
                String make = str(libraw_iparams_t.make(iParams));
                String model = str(libraw_iparams_t.model(iParams));
                new RawDecoderToNativeTranslator(pickSettings(make + " " + model), operatingSystem)
                        .translate(libraw_data_t.params(iprc));

                cameraModel = model;
                long timestamp = libraw_imgother_t.timestamp(libraw_h.libraw_get_imgother(iprc));
                shootingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                        TimeZone.getDefault().toZoneId());

                k = libraw_h.libraw_dcraw_process(iprc);
                if (k < 0) {
                    throw new IOException("libraw_dcraw_process failed, return value was: " + k);
                }

                MemorySegment errorCode = scope.allocate(libraw_h.C_INT);
                memImage = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode);
                int memErr = errorCode.get(ValueLayout.JAVA_INT, 0);
                if (isNull(memImage) || memErr != 0) {
                    memImage = MemorySegment.NULL;         // nothing to free
                    throw new IOException("libraw_dcraw_make_mem_image failed, error code: " + memErr);
                }

                // All values come from the same processed_image_t that holds the pixels
                int w = libraw_processed_image_t.width(memImage);
                int h = libraw_processed_image_t.height(memImage);
                int bits = libraw_processed_image_t.bits(memImage);
                int colors = libraw_processed_image_t.colors(memImage);

                if (bits != 8 || colors != 3) {
                    throw new IOException("Unsupported pixel format: bits=" + bits + ", colors=" + colors);
                }
                long rowBytes = (long) w * colors * (bits / 8);
                long total = rowBytes * h;
                if (w <= 0 || h <= 0 || total > Integer.MAX_VALUE - 8) {
                    throw new IOException("Invalid image size: " + w + "x" + h);
                }

                MemorySegment data$slice = libraw_processed_image_t.data(memImage);
                MemorySegment pixels = MemorySegment.ofAddress(data$slice.address()).reinterpret(total);

                byte[] data = new byte[(int) total];
                MemorySegment.copy(pixels, ValueLayout.JAVA_BYTE, 0, data, 0, (int) total);   // one bulk copy

                // publish results only after everything was validated
                imageWidth = w;
                imageHeight = h;
                imageBits = bits;
                imageColors = colors;
                stride = (int) rowBytes;
                progress(h, h);
                return data;
            } finally {
                if (!isNull(memImage)) {
                    libraw_h.libraw_dcraw_clear_mem(memImage);
                }
                libraw_h.libraw_close(iprc);
            }
        }
    }

    /**
     * Converts RGB bytes to opaque ARGB ints.
     *
     * @param rawBytes interleaved RGB bytes
     * @return int array with exactly one entry per pixel
     */
    public int[] convertToINT(byte[] rawBytes) {
        int pixels = rawBytes.length / 3;
        int[] raw = new int[pixels];
        for (int j = 0, i = 0; j < pixels; j++, i += 3) {
            raw[j] = 0xFF000000
                    | ((rawBytes[i] & 0xFF) << 16)
                    | ((rawBytes[i + 1] & 0xFF) << 8)
                    | (rawBytes[i + 2] & 0xFF);
        }
        return raw;
    }

    // ==========================================================================
    // Size queries
    // ==========================================================================
    /**
     * Cheap size query: only opens the file, no demosaicing. The result is the
     * raw sensor output size (libraw iwidth/iheight) and does NOT account for
     * rotation (flip) or half_size. For exact sizes decode once and use
     * getImageWidth()/getImageHeight().
     */
    public void getQuickSize(byte[] sourceFileAsByteArray) throws IOException {
        if (sourceFileAsByteArray == null) {
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        ensureLoaded();
        try (Arena scope = Arena.ofConfined()) {
            MemorySegment iprc = libraw_h.libraw_init(0);
            if (isNull(iprc)) {
                throw new IOException("libraw_init failed");
            }
            try {
                int k = libraw_h.libraw_open_buffer(iprc,
                        scope.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray),
                        sourceFileAsByteArray.length);
                if (k != 0) {
                    throw new IOException("Cannot open file stream, LibRaw return value was: " + k);
                }
                imageWidth = libraw_h.libraw_get_iwidth(iprc);
                imageHeight = libraw_h.libraw_get_iheight(iprc);
            } finally {
                libraw_h.libraw_close(iprc);
            }
        }
    }

    /**
     * @deprecated performs a FULL decode only to learn the size. Use
     * {@link #getQuickSize(byte[])} or decode once and cache the result.
     */
    @Deprecated
    public void getBasicMetaInfo(byte[] sourceFileAsByteArray) throws IOException {
        readPixelDataFromStream(sourceFileAsByteArray);
    }

    // ==========================================================================
    // Metadata
    // ==========================================================================
    /**
     * Metadata of the file set via the constructor / setImageFileURL.
     * Timestamps are epoch SECONDS.
     */
    public HashMap<String, String> getMetaData() throws IOException {
        final String url = imageFileURL;
        if (url == null) {
            LOG.log(Level.FINEST, "imageFileURL == null!");
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        return readMeta((iprc, arena) -> libraw_h.libraw_open_file(iprc, arena.allocateFrom(url)),
                "file '" + url + "'");
    }

    /**
     * Metadata of the given file bytes. Timestamps are epoch SECONDS.
     */
    public HashMap<String, String> getMetaData(byte[] sourceFileAsByteArray) throws IOException {
        if (sourceFileAsByteArray == null) {
            LOG.log(Level.SEVERE, "sourceFileAsByteArray == null!");
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        return readMeta((iprc, arena) -> libraw_h.libraw_open_buffer(iprc,
                arena.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray),
                sourceFileAsByteArray.length), "file stream");
    }

    /** @return "Maker Model" of the file set via constructor / setImageFileURL */
    public String getCameraMaker() throws IOException {
        final String url = imageFileURL;
        if (url == null) {
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        ensureLoaded();
        try (Arena scope = Arena.ofConfined()) {
            MemorySegment iprc = libraw_h.libraw_init(0);
            if (isNull(iprc)) {
                throw new IOException("libraw_init failed");
            }
            try {
                int k = libraw_h.libraw_open_file(iprc, scope.allocateFrom(url));
                if (k != 0) {
                    throw new IOException("Cannot open file '" + url + "', LibRaw return value was: " + k);
                }
                MemorySegment iParams = libraw_h.libraw_get_iparams(iprc);
                return str(libraw_iparams_t.make(iParams)) + " " + str(libraw_iparams_t.model(iParams));
            } finally {
                libraw_h.libraw_close(iprc);
            }
        }
    }

    private HashMap<String, String> readMeta(NativeOpen opener, String source) throws IOException {
        ensureLoaded();
        HashMap<String, String> m = new HashMap<>();
        try (Arena scope = Arena.ofConfined()) {
            MemorySegment iprc = libraw_h.libraw_init(0);
            if (isNull(iprc)) {
                throw new IOException("libraw_init failed");
            }
            try {
                int k = opener.open(iprc, scope);
                if (k != 0) {
                    throw new IOException("Cannot open " + source + ", LibRaw return value was: " + k);
                }
                fillMeta(iprc, m);
                return m;
            } finally {
                libraw_h.libraw_close(iprc);
            }
        }
    }

    private static void fillMeta(MemorySegment iprc, HashMap<String, String> m) {
        MemorySegment iParams = libraw_h.libraw_get_iparams(iprc);
        m.put("CameraMaker", str(libraw_iparams_t.make(iParams)));
        m.put("CameraModel", str(libraw_iparams_t.model(iParams)));

        // XMP: xmpdata is a POINTER, xmplen its length (the old code used the length as address)
        int xmpLen = libraw_iparams_t.xmplen(iParams);
        if (xmpLen > 0) {
            MemorySegment xmpPtr = libraw_iparams_t.xmpdata(iParams);
            if (!isNull(xmpPtr)) {
                byte[] xmp = xmpPtr.reinterpret(xmpLen).toArray(ValueLayout.JAVA_BYTE);
                m.put("XMP", new String(xmp, StandardCharsets.UTF_8));
            }
        }

        MemorySegment other = libraw_h.libraw_get_imgother(iprc);

        MemorySegment gps = libraw_imgother_t.parsed_gps(other);
        m.put("GPS Altitude", "" + libraw_gps_info_t.altitude(gps));
        m.put("GPS Position", libraw_gps_info_t.latref(gps) + ";" + libraw_gps_info_t.longref(gps));
        MemorySegment gpsTime = libraw_gps_info_t.gpstimestamp(gps);     // float[3]: h, m, s
        if (gpsTime.byteSize() >= 12) {
            m.put("GPS Time", gpsTime.get(ValueLayout.JAVA_FLOAT, 0) + ":"
                    + gpsTime.get(ValueLayout.JAVA_FLOAT, 4) + ":"
                    + gpsTime.get(ValueLayout.JAVA_FLOAT, 8));
        }

        float aperture = libraw_imgother_t.aperture(other);
        float focalLen = libraw_imgother_t.focal_len(other);
        float isoSpeed = libraw_imgother_t.iso_speed(other);
        float shutter = libraw_imgother_t.shutter(other);
        long timestamp = libraw_imgother_t.timestamp(other);
        m.put("CurFocal", "" + focalLen);
        m.put("ISO speed", "" + isoSpeed);
        m.put("shutter", "" + shutter);
        m.put("Timestamp (EpocheSec)", "" + timestamp);

        MemorySegment lens = libraw_data_t.lens(iprc);
        m.put("MaxAperture @CurFocal", "f/" + libraw_lensinfo_t.EXIF_MaxAp(lens));
        m.put("FocalLengthIn35mmFormat", libraw_lensinfo_t.FocalLengthIn35mmFormat(lens) + " mm");
        m.put("InternalLensSerial$slice", str(libraw_lensinfo_t.InternalLensSerial(lens)));
        m.put("Lens", str(libraw_lensinfo_t.Lens(lens)));
        m.put("LensMake", str(libraw_lensinfo_t.LensMake(lens)));
        m.put("LensSerial$slice", str(libraw_lensinfo_t.LensSerial(lens)));
        m.put("MaxAp @MaxFocal", "f/" + libraw_lensinfo_t.MaxAp4MaxFocal(lens));
        m.put("MaxAp @MinFocal", "f/" + libraw_lensinfo_t.MaxAp4MinFocal(lens));
        m.put("MaxFocal", libraw_lensinfo_t.MaxFocal(lens) + " mm");
        m.put("MinFocal", libraw_lensinfo_t.MinFocal(lens) + " mm");
        MemorySegment makernotes = libraw_lensinfo_t.makernotes(lens);
        m.put("CurFocal", "" + libraw_makernotes_lens_t.CurFocal(makernotes));
        m.put("CurAperture", "" + libraw_makernotes_lens_t.CurAp(makernotes));

        MemorySegment shooting = libraw_data_t.shootinginfo(iprc);
        m.put("AFPoint", "" + libraw_shootinginfo_t.AFPoint(shooting));
        m.put("DriveMode", "" + libraw_shootinginfo_t.DriveMode(shooting));
        m.put("ExposureMode", "" + libraw_shootinginfo_t.ExposureMode(shooting));
        m.put("ExposureProgram", "" + libraw_shootinginfo_t.ExposureProgram(shooting));
        m.put("FocusMode", "" + libraw_shootinginfo_t.FocusMode(shooting));
        m.put("ImageStabiMode", "" + libraw_shootinginfo_t.ImageStabilization(shooting));
        m.put("MedteringMode", "" + libraw_shootinginfo_t.MeteringMode(shooting));

        m.put("ImageWidth", "" + libraw_h.libraw_get_iwidth(iprc));
        m.put("ImageHeight", "" + libraw_h.libraw_get_iheight(iprc));
        m.put("ColorMaximum", "" + libraw_h.libraw_get_color_maximum(iprc));

        m.put("Aperture", "f/" + aperture);
        m.put("Focal length", focalLen + " mm");
        m.put("ISO speed", "" + isoSpeed);
        m.put("Shutter", shutter > 0 ? "" + (1 / shutter) : "0");   // no Infinity for shutter == 0
    }

    // ==========================================================================
    // Helpers
    // ==========================================================================
    private static boolean isNull(MemorySegment s) {
        return s == null || s.address() == 0L;
    }

    /** Reads a NUL terminated C string from a fixed size char[] field. */
    private static String str(MemorySegment s) {
        return s.getString(0);
    }

    // ==========================================================================
    // Accessors
    // ==========================================================================
    public String getImageFileURL() {
        return imageFileURL;
    }

    public void setImageFileURL(String url) {
        imageFileURL = url;
    }

    /** @return width of the last decoded image (after flip) */
    public int getImageWidth() {
        return imageWidth;
    }

    /** @return height of the last decoded image (after flip) */
    public int getImageHeight() {
        return imageHeight;
    }

    /** @return bits per channel (8 or 16) */
    public int getImageBits() {
        return imageBits;
    }

    /** @return number of colour channels (3 = RGB) */
    public int getImageColors() {
        return imageColors;
    }

    /** @return bytes per image row */
    public int getStride() {
        return stride;
    }

    /** @return bytes per pixel (colors * bits / 8) */
    public int getNumBands() {
        return imageColors * (imageBits / 8);
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public LocalDateTime getShootingDateTime() {
        return shootingDateTime;
    }

    @Override
    public String toString() {
        return "LibrawImage{imageFileURL=" + imageFileURL + ", imageWidth=" + imageWidth
                + ", imageHeight=" + imageHeight + ", imageBits=" + imageBits
                + ", imageColors=" + imageColors + ", stride=" + stride
                + ", cameraModel=" + cameraModel + ", shootingDateTime=" + shootingDateTime + '}';
    }
}
