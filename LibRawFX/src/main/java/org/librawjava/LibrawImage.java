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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.libraw.nativ.libraw_colordata_t;
import org.libraw.nativ.libraw_data_t;
import org.libraw.nativ.libraw_gps_info_t;
import org.libraw.nativ.libraw_h;
import org.libraw.nativ.libraw_image_sizes_t;
import org.libraw.nativ.libraw_imgother_t;
import org.libraw.nativ.libraw_iparams_t;
import org.libraw.nativ.libraw_lensinfo_t;
import org.libraw.nativ.libraw_makernotes_lens_t;
import org.libraw.nativ.libraw_processed_image_t;
import org.libraw.nativ.libraw_shootinginfo_t;
import org.libraw.nativ.libraw_thumbnail_item_t;
import org.libraw.nativ.libraw_thumbnail_list_t;
import org.libraw.nativ.libraw_thumbnail_t;
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
 * Errors coming from LibRaw are thrown as {@link LibrawException} (a subclass
 * of IOException) with a readable message and the raw error code.
 *
 * @author Clemens Lanthaler
 */
public class LibrawImage {

    private static final Logger LOG = Logger.getLogger(LibrawImage.class.getName());

    /**
     * half_size is used for a preview only if the requested size is at most this
     * fraction of the image (the half size result must still be at least as big
     * as the request).
     */
    private static final double HALF_SIZE_MAX_SCALE = 0.49;

    /** Source label used in messages when the data comes from memory. */
    private static final String SRC_DATA = "Image data";

    /**
     * Progress steps reported during a decode (of PROGRESS_TOTAL). LibRaw has
     * no fine grained progress, so the phases are weighted by their typical
     * share of the decode time; demosaicing (process) is the longest one.
     */
    private static final int PROGRESS_TOTAL = 100;
    private static final int PROGRESS_OPENED = 5;
    private static final int PROGRESS_UNPACKED = 25;
    private static final int PROGRESS_PROCESSED = 85;
    private static final int PROGRESS_OUTPUT = 95;

    /** Receives decode progress (done of total, see PROGRESS_TOTAL). */
    @FunctionalInterface
    public interface ProgressListener {

        void onProgress(int done, int total);
    }

    /** Opens a source (file or buffer) on an initialised libraw handle. */
    @FunctionalInterface
    private interface NativeOpen {

        int open(MemorySegment iprc, Arena arena);
    }

    /** Result of a decode: 8 bit bytes or 16 bit samples (exactly one is set). */
    private record Decoded(byte[] data8, short[] data16, int width, int height) {

    }

    // ---- static state -------------------------------------------------------
    private static volatile boolean loaded = false;
    private static volatile String operatingSystem;

    /**
     * Global override for the automatic half_size of previews. null = use the
     * flag of the settings, FALSE = never, TRUE = always allowed. Initial value
     * from the system property librawfx.halfSizeForPreviews (true/false).
     */
    private static volatile Boolean previewHalfSizeOverride = readPreviewProperty();

    /** null = not checked yet; see thumbExAvailable(). */
    private static volatile Boolean thumbExAvailable;

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
    private volatile boolean lastDecodeHalfSize;
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
    // Error texts
    // ==========================================================================
    /**
     * Readable text for a LibRaw / libraw_open_file return code. Negative
     * values are LibRaw errors (libraw_const.h), positive values are operating
     * system errors from libraw_open_file.
     *
     * @param code the return value of the native call
     * @return text suitable for the user
     */
    public static String describeError(int code) {
        switch (code) {
            case -1:
                return "Unknown error";
            case -2:
                return "Not a supported RAW format";
            case -3:
                return "The requested image does not exist in the file";
            case -4:
                return "Internal error (call out of order)";
            case -5:
                return "No thumbnail available";
            case -6:
                return "Thumbnail format is not supported";
            case -7:
                return "Input stream was closed";
            case -8:
                return "Function not implemented";
            case -9:
                return "The requested thumbnail does not exist";
            case -100007:
                return "Not enough memory";
            case -100008:
                return "Corrupt or incomplete image data";
            case -100009:
                return "Read error (file corrupt or truncated)";
            case -100010:
                return "Operation cancelled";
            case -100011:
                return "Invalid crop area";
            case -100012:
                return "Image is too large";
            case -100013:
                return "Internal memory pool overflow";
            case 2:
                return "File not found";
            case 13:
                return "Permission denied";
            default:
                return code > 0 ? "File system error (code " + code + ")" : "Unknown LibRaw error";
        }
    }

    private static LibrawException error(String source, int code) {
        return new LibrawException(source + ": " + describeError(code) + " [LibRaw " + code + "]", code);
    }

    /** Only the file name, not the full path, for messages shown to users. */
    private static String fileLabel(String path) {
        return "File '" + new File(path).getName() + "'";
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

    private static Boolean readPreviewProperty() {
        String v = System.getProperty("librawfx.halfSizeForPreviews");
        return v == null || v.isBlank() ? null : Boolean.valueOf(v.trim());
    }

    /**
     * Switches the automatic half_size for previews on or off for ALL loaders
     * (JavaFX install mode and ImageIO) without touching any settings profile.
     * Takes effect for the next decode.
     *
     * @param value FALSE = never use half_size for previews, TRUE = allowed
     * even if a profile disables it, null = follow the profiles (default)
     */
    public static void setPreviewHalfSizeOverride(Boolean value) {
        previewHalfSizeOverride = value;
        LOG.log(Level.FINE, "Preview half_size override: {0}", value);
    }

    public static Boolean getPreviewHalfSizeOverride() {
        return previewHalfSizeOverride;
    }

    private static boolean previewHalfAllowed(RawDecoderSettings s) {
        Boolean o = previewHalfSizeOverride;
        return o != null ? o : s.isHalfSizeForPreviews();
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
        return readPixelDataFromStream(sourceFileAsByteArray, 0, 0);
    }

    /**
     * Like {@link #readPixelDataFromStream(byte[])}, but tells LibRaw which size
     * the caller needs. If that is a preview (small enough, see
     * {@link RawDecoderSettings#isHalfSizeForPreviews()}), the image is decoded
     * with half_size, which is much faster. The real size of the result is
     * available from getImageWidth()/getImageHeight().
     *
     * @param targetWidth requested width in pixels, 0 = unspecified
     * @param targetHeight requested height in pixels, 0 = unspecified (both 0 = full size)
     */
    public byte[] readPixelDataFromStream(byte[] sourceFileAsByteArray, int targetWidth, int targetHeight)
            throws IOException {
        if (sourceFileAsByteArray == null) {
            LOG.log(Level.SEVERE, "sourceFileAsByteArray == null!");
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        return decode((iprc, arena) -> libraw_h.libraw_open_buffer(iprc,
                arena.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray),
                sourceFileAsByteArray.length), SRC_DATA, targetWidth, targetHeight, 8, null).data8();
    }

    /**
     * Decodes to 16 bit RGB for editing (full size unless the profile enables
     * halfSizeOutput). The result is independent of the sixteenBitsImage flag.
     * Linear output uses sRGB primaries; see
     * {@link #readPixelData16(byte[], boolean, OutputColor)} for ProPhoto.
     *
     * @param sourceFileAsByteArray the complete file
     * @param linear true = linear light (gamma 1.0, no auto brightness, bright
     * 1.0, sRGB primaries): the right input for exposure and white balance.
     * false = gamma encoded as configured in the profile.
     * @return interleaved RGB, unsigned 16 bit in a short[] (use {@code & 0xFFFF})
     * @throws IOException if the file cannot be opened or decoded
     */
    public RawPixels16 readPixelData16(byte[] sourceFileAsByteArray, boolean linear) throws IOException {
        return readPixelData16(sourceFileAsByteArray, linear, OutputColor.SRGB);
    }

    /**
     * Like {@link #readPixelData16(byte[], boolean)}, with the primaries of
     * the linear output. The colour space of the profile (outputColorSpace,
     * output ICC profile) is ignored for linear output.
     *
     * @param color primaries of linear output; ignored if linear is false
     */
    public RawPixels16 readPixelData16(byte[] sourceFileAsByteArray, boolean linear, OutputColor color)
            throws IOException {
        if (sourceFileAsByteArray == null) {
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        if (linear && color == null) {
            throw new IllegalArgumentException("color == null!");
        }
        Decoded d = decode((iprc, arena) -> libraw_h.libraw_open_buffer(iprc,
                arena.allocateFrom(ValueLayout.JAVA_BYTE, sourceFileAsByteArray),
                sourceFileAsByteArray.length), SRC_DATA, 0, 0, 16, linear ? color : null);
        return new RawPixels16(d.width(), d.height(), 3, d.data16(), linear);
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
                fileLabel(url), 0, 0, 8, null).data8();
        return convertToINT(rgb);
    }

    private Decoded decode(NativeOpen opener, String source, int targetW, int targetH, int bps, OutputColor linear)
            throws IOException {
        long start = System.nanoTime();
        long startWall = System.currentTimeMillis();
        try {
            return decode0(opener, source, targetW, targetH, bps, linear);
        } finally {
            LOG.log(Level.FINE, "{0} decode total (incl. arena close) ms: {1}, thread={2}, start={3}, end={4} (ms of the current 1000 s)",
                    new Object[]{source, ms(start, System.nanoTime()), Thread.currentThread().getName(),
                        String.valueOf(startWall % 1_000_000), String.valueOf(System.currentTimeMillis() % 1_000_000)});
        }
    }

    private Decoded decode0(NativeOpen opener, String source, int targetW, int targetH, int bps, OutputColor linear)
            throws IOException {
        ensureLoaded();
        try (Arena scope = Arena.ofConfined()) {          // confined: cheaper to close than shared
            MemorySegment iprc = libraw_h.libraw_init(0);
            if (isNull(iprc)) {
                throw new IOException("libraw_init failed");
            }
            MemorySegment memImage = MemorySegment.NULL;
            try {
                progress(0, 1);
                long t0 = System.nanoTime();
                int k = opener.open(iprc, scope);          // includes the native copy of the file
                if (k != 0) {
                    throw error(source, k);
                }
                long tOpen = System.nanoTime();
                progress(PROGRESS_OPENED, PROGRESS_TOTAL);

                // make and model are known after open: pick the settings, then apply the
                // decode-time options (rawparams) BEFORE unpack
                MemorySegment iParams = libraw_h.libraw_get_iparams(iprc);
                String make = str(libraw_iparams_t.make(iParams));
                String model = str(libraw_iparams_t.model(iParams));
                RawDecoderSettings chosen = pickSettings(make + " " + model);
                RawDecoderToNativeTranslator translator = new RawDecoderToNativeTranslator(chosen, operatingSystem);
                translator.translateDecodeParams(libraw_data_t.rawparams(iprc));

                // preview requested? -> half_size (decided here, the size is known after open)
                boolean previewHalf = false;
                if (previewHalfAllowed(chosen) && !chosen.isHalfSizeOutput() && (targetW > 0 || targetH > 0)) {
                    int[] sz = sizesOf(iprc);
                    previewHalf = previewFitsHalf(sz[0], sz[1], targetW, targetH);
                    LOG.log(Level.FINE, "{0} {1}: half_size for preview={2} (image {3}x{4}, requested {5}x{6})",
                            new Object[]{make, model, previewHalf, sz[0], sz[1], targetW, targetH});
                }

                k = libraw_h.libraw_unpack(iprc);
                if (k < 0) {
                    throw error(source + " (decoding)", k);
                }
                long tUnpack = System.nanoTime();
                progress(PROGRESS_UNPACKED, PROGRESS_TOTAL);

                translator.translate(libraw_data_t.params(iprc), scope);   // scope: arena for file path options
                if (previewHalf) {
                    translator.forceHalfSize(libraw_data_t.params(iprc));
                }
                lastDecodeHalfSize = previewHalf || chosen.isHalfSizeOutput();
                translator.forceOutputBps(libraw_data_t.params(iprc), bps);   // 8 bit for images, 16 bit for editing
                if (linear != null) {
                    translator.forceLinear(libraw_data_t.params(iprc), linear.libRawCode());
                }

                cameraModel = model;
                long timestamp = libraw_imgother_t.timestamp(libraw_h.libraw_get_imgother(iprc));
                shootingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                        TimeZone.getDefault().toZoneId());

                k = libraw_h.libraw_dcraw_process(iprc);
                if (k < 0) {
                    throw error(source + " (processing)", k);
                }
                long tProcess = System.nanoTime();
                progress(PROGRESS_PROCESSED, PROGRESS_TOTAL);
                if (LOG.isLoggable(Level.FINE)) {
                    MemorySegment color = libraw_data_t.color(iprc);
                    LOG.log(Level.FINE, "{0} {1}: WB cam_mul={2}, pre_mul (used)={3}",
                            new Object[]{make, model, floats4(libraw_colordata_t.cam_mul(color)),
                                floats4(libraw_colordata_t.pre_mul(color))});
                }

                MemorySegment errorCode = scope.allocate(libraw_h.C_INT);
                memImage = libraw_h.libraw_dcraw_make_mem_image(iprc, errorCode);
                int memErr = errorCode.get(ValueLayout.JAVA_INT, 0);
                if (isNull(memImage) || memErr != 0) {
                    memImage = MemorySegment.NULL;         // nothing to free
                    throw error(source + " (output)", memErr != 0 ? memErr : -1);
                }
                long tMem = System.nanoTime();
                progress(PROGRESS_OUTPUT, PROGRESS_TOTAL);

                // All values come from the same processed_image_t that holds the pixels
                int w = libraw_processed_image_t.width(memImage);
                int h = libraw_processed_image_t.height(memImage);
                int bits = libraw_processed_image_t.bits(memImage);
                int colors = libraw_processed_image_t.colors(memImage);

                if (bits != bps || colors != 3) {
                    throw new IOException(source + ": Unsupported pixel format (bits=" + bits
                            + ", colors=" + colors + ")");
                }
                long rowBytes = (long) w * colors * (bits / 8);
                long total = rowBytes * h;
                long samples = (long) w * h * colors;
                if (w <= 0 || h <= 0 || total > Integer.MAX_VALUE - 8 || samples > Integer.MAX_VALUE - 8) {
                    throw new IOException(source + ": Invalid image size " + w + "x" + h);
                }

                MemorySegment data$slice = libraw_processed_image_t.data(memImage);
                MemorySegment pixels = MemorySegment.ofAddress(data$slice.address()).reinterpret(total);

                byte[] data8 = null;
                short[] data16 = null;
                if (bits == 8) {
                    data8 = new byte[(int) total];
                    MemorySegment.copy(pixels, ValueLayout.JAVA_BYTE, 0, data8, 0, (int) total);   // one bulk copy
                } else {
                    data16 = new short[(int) samples];     // LibRaw stores 16 bit in host byte order
                    MemorySegment.copy(pixels, ValueLayout.JAVA_SHORT_UNALIGNED, 0, data16, 0, (int) samples);
                }
                long tCopy = System.nanoTime();

                // publish results only after everything was validated
                imageWidth = w;
                imageHeight = h;
                imageBits = bits;
                imageColors = colors;
                stride = (int) rowBytes;
                progress(h, h);
                LOG.log(Level.FINE, "{0} phases ms: open+native copy={1}, unpack={2}, process={3}, make_mem_image={4}, copy to byte[]={5}",
                        new Object[]{source, ms(t0, tOpen), ms(tOpen, tUnpack), ms(tUnpack, tProcess),
                            ms(tProcess, tMem), ms(tMem, tCopy)});
                return new Decoded(data8, data16, w, h);
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
     * output size LibRaw reports after open, already swapped for the 90 degree
     * rotation stored in the file. It does not account for the user_flip
     * setting, for the Fuji SuperCCD rotation or for half_size. For exact sizes
     * decode once and use getImageWidth()/getImageHeight().
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
                    throw error(SRC_DATA, k);
                }
                int[] sz = sizesOf(iprc);
                boolean swap = (sz[2] & 4) != 0;          // flip bit 4 = 90 degree rotation
                imageWidth = swap ? sz[1] : sz[0];
                imageHeight = swap ? sz[0] : sz[1];
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
     *
     * @throws LibrawException if LibRaw cannot open the file; check
     * {@link LibrawException#isUnsupportedFormat()} for non-RAW files
     */
    public HashMap<String, String> getMetaData() throws IOException {
        final String url = imageFileURL;
        if (url == null) {
            LOG.log(Level.FINEST, "imageFileURL == null!");
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        return readMeta((iprc, arena) -> libraw_h.libraw_open_file(iprc, arena.allocateFrom(url)),
                fileLabel(url));
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
                sourceFileAsByteArray.length), SRC_DATA);
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
                    throw error(fileLabel(url), k);
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
                    throw error(source, k);
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
    // Thumbnails (embedded previews, no RAW decoding)
    // ==========================================================================
    /** Chooses a thumbnail index from the list; -1 = LibRaw's default (the largest). */
    @FunctionalInterface
    private interface ThumbSelector {

        int select(List<ThumbnailInfo> list);
    }

    /**
     * Lists the thumbnails / previews embedded in the given file bytes. Cheap:
     * only the file header is parsed, no image data is loaded.
     *
     * @return all thumbnails, possibly empty. Empty as well if the native
     * library is too old for thumbnail lists (LibRaw before 0.21)
     * @throws LibrawException if LibRaw cannot open the data
     */
    public List<ThumbnailInfo> listThumbnails(byte[] sourceFileAsByteArray) throws IOException {
        if (sourceFileAsByteArray == null) {
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        return withOpened(bufferOpener(sourceFileAsByteArray), SRC_DATA, LibrawImage::thumbList);
    }

    /** Like {@link #listThumbnails(byte[])} for the file set via constructor / setImageFileURL. */
    public List<ThumbnailInfo> listThumbnails() throws IOException {
        String url = requireUrl();
        return withOpened(fileOpener(url), fileLabel(url), LibrawImage::thumbList);
    }

    /**
     * Extracts the largest embedded thumbnail / preview of the given file
     * bytes. Much faster than decoding the RAW data, ideal for image browsers.
     *
     * @throws LibrawException with code -5 if the file has no thumbnail, -6 if
     * its format is not supported (e.g. HEIF preview of CR3 files)
     */
    public RawThumbnail readThumbnail(byte[] sourceFileAsByteArray) throws IOException {
        return readThumbnail(sourceFileAsByteArray, -1);
    }

    /**
     * Extracts the thumbnail with the given index of {@link #listThumbnails(byte[])}.
     *
     * @param index index in the list, -1 = the largest
     * @throws LibrawException with code -9 if the index does not exist
     */
    public RawThumbnail readThumbnail(byte[] sourceFileAsByteArray, int index) throws IOException {
        if (sourceFileAsByteArray == null) {
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        return readThumb(bufferOpener(sourceFileAsByteArray), SRC_DATA, list -> index);
    }

    /**
     * Extracts the smallest supported thumbnail that is at least as big as the
     * requested size (checked in both orientations), or the largest one if
     * none is big enough. Use this for lists / grids of previews.
     *
     * @param minWidth requested width, 0 = unspecified
     * @param minHeight requested height, 0 = unspecified
     */
    public RawThumbnail readThumbnail(byte[] sourceFileAsByteArray, int minWidth, int minHeight) throws IOException {
        if (sourceFileAsByteArray == null) {
            throw new IllegalArgumentException("sourceFileAsByteArray == null!");
        }
        return readThumb(bufferOpener(sourceFileAsByteArray), SRC_DATA, list -> pickThumb(list, minWidth, minHeight));
    }

    /**
     * Extracts the largest thumbnail of the file set via constructor /
     * setImageFileURL. LibRaw reads only the needed parts of the file, so this
     * is the fastest way for large RAW files.
     */
    public RawThumbnail readThumbnail() throws IOException {
        String url = requireUrl();
        return readThumb(fileOpener(url), fileLabel(url), list -> -1);
    }

    /** Like {@link #readThumbnail(byte[], int)} for the file set via constructor / setImageFileURL. */
    public RawThumbnail readThumbnail(int index) throws IOException {
        String url = requireUrl();
        return readThumb(fileOpener(url), fileLabel(url), list -> index);
    }

    /** Like {@link #readThumbnail(byte[], int, int)} for the file set via constructor / setImageFileURL. */
    public RawThumbnail readThumbnail(int minWidth, int minHeight) throws IOException {
        String url = requireUrl();
        return readThumb(fileOpener(url), fileLabel(url), list -> pickThumb(list, minWidth, minHeight));
    }

    /**
     * Smallest supported thumbnail covering the request, else the largest
     * supported one; -1 (LibRaw default) if the list is empty or no size is given.
     */
    static int pickThumb(List<ThumbnailInfo> list, int reqW, int reqH) {
        if (list.isEmpty() || (reqW <= 0 && reqH <= 0)) {
            return -1;
        }
        ThumbnailInfo best = null;
        ThumbnailInfo largest = null;
        for (ThumbnailInfo t : list) {
            if (!t.isSupported() || t.width() <= 0 || t.height() <= 0) {
                continue;
            }
            if (largest == null || area(t) > area(largest)) {
                largest = t;
            }
            boolean fits = (Math.max(reqW, 0) <= t.width() && Math.max(reqH, 0) <= t.height())
                    || (Math.max(reqW, 0) <= t.height() && Math.max(reqH, 0) <= t.width());
            if (fits && (best == null || area(t) < area(best))) {
                best = t;
            }
        }
        ThumbnailInfo r = best != null ? best : largest;
        return r == null ? -1 : r.index();
    }

    private static long area(ThumbnailInfo t) {
        return (long) t.width() * t.height();
    }

    private RawThumbnail readThumb(NativeOpen opener, String source, ThumbSelector selector) throws IOException {
        return withOpened(opener, source, (iprc, scope) -> {
            List<ThumbnailInfo> list = thumbList(iprc, scope);
            int index = selector.select(list);
            int k;
            if (index >= 0 && thumbExAvailable()) {
                k = libraw_h.libraw_unpack_thumb_ex(iprc, index);
            } else if (index > 0) {
                throw new LibrawException(source + ": Thumbnail index " + index
                        + " needs LibRaw 0.21 or newer [LibRaw -8]", -8);
            } else {
                k = libraw_h.libraw_unpack_thumb(iprc);
            }
            if (k != 0) {
                throw error(source + " (thumbnail)", k);
            }
            // the thumbnail's own orientation if the file has one, else the one of the image
            int flip = -1;
            for (ThumbnailInfo t : list) {
                if (t.index() == index) {
                    flip = t.flip();
                }
            }
            MemorySegment sizes = libraw_data_t.sizes(iprc);
            if (flip <= 0) {
                flip = libraw_image_sizes_t.flip(sizes);
            }
            RawThumbnail t = memThumb(iprc, scope, source, flip);
            int fixed = thumbFlip(t.flip(), libraw_image_sizes_t.width(sizes) & 0xFFFF,
                    libraw_image_sizes_t.height(sizes) & 0xFFFF, t.width(), t.height());
            return fixed == t.flip() ? t
                    : new RawThumbnail(t.type(), t.width(), t.height(), t.colors(), fixed, t.data());
        });
    }

    /**
     * Some cameras (e.g. Nikon) store the preview already rotated while the
     * RAW data carries a 90 degree flip. If the thumbnail has the aspect of the
     * rotated image, no further 90 degree rotation is needed.
     *
     * @param rawW width of the RAW image before rotation
     */
    static int thumbFlip(int flip, int rawW, int rawH, int thumbW, int thumbH) {
        if ((flip == 5 || flip == 6) && rawW > 0 && rawH > 0 && thumbW > 0 && thumbH > 0
                && rawW != rawH && thumbW != thumbH && (rawW > rawH) != (thumbW > thumbH)) {
            return 0;
        }
        return flip;
    }

    private static RawThumbnail memThumb(MemorySegment iprc, Arena scope, String source, int flip) throws IOException {
        MemorySegment errorCode = scope.allocate(libraw_h.C_INT);
        MemorySegment mem = libraw_h.libraw_dcraw_make_mem_thumb(iprc, errorCode);
        int err = errorCode.get(ValueLayout.JAVA_INT, 0);
        if (isNull(mem) || err != 0) {
            throw error(source + " (thumbnail)", err != 0 ? err : -1);
        }
        try {
            int type = libraw_processed_image_t.type(mem);
            int w = libraw_processed_image_t.width(mem) & 0xFFFF;
            int h = libraw_processed_image_t.height(mem) & 0xFFFF;
            int colors = libraw_processed_image_t.colors(mem);
            int bits = libraw_processed_image_t.bits(mem);
            long size = Integer.toUnsignedLong(libraw_processed_image_t.data_size(mem));
            if (size <= 0 || size > Integer.MAX_VALUE - 8) {
                throw new IOException(source + ": Invalid thumbnail size " + size);
            }
            MemorySegment data = MemorySegment.ofAddress(libraw_processed_image_t.data(mem).address()).reinterpret(size);

            if (type == libraw_h.LIBRAW_IMAGE_JPEG()) {
                if (w == 0 || h == 0) {                   // LibRaw leaves the size empty for JPEG
                    MemorySegment t = libraw_data_t.thumbnail(iprc);
                    w = libraw_thumbnail_t.twidth(t) & 0xFFFF;
                    h = libraw_thumbnail_t.theight(t) & 0xFFFF;
                }
                return new RawThumbnail(RawThumbnail.Type.JPEG, w, h, 3, flip, data.toArray(ValueLayout.JAVA_BYTE));
            }
            if (type != libraw_h.LIBRAW_IMAGE_BITMAP() || (bits != 8 && bits != 16) || colors <= 0) {
                throw new LibrawException(source + ": " + describeError(-6) + " [LibRaw -6]", -6);
            }
            long samples = (long) w * h * colors;
            if (samples <= 0 || samples * (bits / 8) > size) {
                throw new IOException(source + ": Invalid thumbnail " + w + "x" + h + "x" + colors);
            }
            byte[] px = new byte[(int) samples];
            if (bits == 8) {
                MemorySegment.copy(data, ValueLayout.JAVA_BYTE, 0, px, 0, px.length);
            } else {                                          // 16 bit (host order) -> 8 bit
                for (int i = 0; i < px.length; i++) {
                    px[i] = (byte) (data.getAtIndex(ValueLayout.JAVA_SHORT_UNALIGNED, i) >>> 8);
                }
            }
            return new RawThumbnail(RawThumbnail.Type.BITMAP, w, h, colors, flip, px);
        } finally {
            libraw_h.libraw_dcraw_clear_mem(mem);
        }
    }

    private static List<ThumbnailInfo> thumbList(MemorySegment iprc, Arena scope) {
        if (!thumbExAvailable()) {
            return List.of();                              // LibRaw < 0.21: no thumbs_list
        }
        MemorySegment tl = libraw_data_t.thumbs_list(iprc);
        int count = Math.min(libraw_thumbnail_list_t.thumbcount(tl), libraw_h.LIBRAW_THUMBNAIL_MAXCOUNT());
        // slice manually: the generated indexed accessor thumblist(struct, i) ignores the field offset
        MemorySegment items = libraw_thumbnail_list_t.thumblist(tl);
        long itemSize = libraw_thumbnail_item_t.layout().byteSize();
        List<ThumbnailInfo> list = new ArrayList<>(Math.max(count, 0));
        for (int i = 0; i < count; i++) {
            MemorySegment item = items.asSlice(i * itemSize, itemSize);
            short tflip = libraw_thumbnail_item_t.tflip(item);
            list.add(new ThumbnailInfo(i,
                    ThumbnailFormat.ofInternal(libraw_thumbnail_item_t.tformat(item)),
                    libraw_thumbnail_item_t.twidth(item) & 0xFFFF,
                    libraw_thumbnail_item_t.theight(item) & 0xFFFF,
                    tflip == (short) 0xFFFF ? -1 : tflip,
                    libraw_thumbnail_item_t.tlength(item)));
        }
        return List.copyOf(list);
    }

    /** True if the native library has libraw_unpack_thumb_ex (LibRaw 0.21+). */
    private static boolean thumbExAvailable() {
        Boolean b = thumbExAvailable;
        if (b == null) {
            try {
                libraw_h.libraw_unpack_thumb_ex$address();
                b = Boolean.TRUE;
            } catch (LinkageError e) {                    // symbol missing in older libs
                LOG.log(Level.FINE, "libraw_unpack_thumb_ex not available: {0}", e.toString());
                b = Boolean.FALSE;
            }
            thumbExAvailable = b;
        }
        return b;
    }

    // ==========================================================================
    // Native call scaffolding
    // ==========================================================================
    /** Work on an opened libraw handle. */
    @FunctionalInterface
    private interface OpenedCall<T> {

        T call(MemorySegment iprc, Arena scope) throws IOException;
    }

    /** init + open + call + close. */
    private <T> T withOpened(NativeOpen opener, String source, OpenedCall<T> call) throws IOException {
        ensureLoaded();
        try (Arena scope = Arena.ofConfined()) {
            MemorySegment iprc = libraw_h.libraw_init(0);
            if (isNull(iprc)) {
                throw new IOException("libraw_init failed");
            }
            try {
                int k = opener.open(iprc, scope);
                if (k != 0) {
                    throw error(source, k);
                }
                return call.call(iprc, scope);
            } finally {
                libraw_h.libraw_close(iprc);
            }
        }
    }

    private static NativeOpen bufferOpener(byte[] bytes) {
        return (iprc, arena) -> libraw_h.libraw_open_buffer(iprc,
                arena.allocateFrom(ValueLayout.JAVA_BYTE, bytes), bytes.length);
    }

    private static NativeOpen fileOpener(String url) {
        return (iprc, arena) -> libraw_h.libraw_open_file(iprc, arena.allocateFrom(url));
    }

    private String requireUrl() {
        String url = imageFileURL;
        if (url == null) {
            throw new IllegalArgumentException("imageFileURL == null!");
        }
        return url;
    }

    // ==========================================================================
    // Helpers
    // ==========================================================================
    /** {width, height, flip} as known right after open (before processing). */
    private static int[] sizesOf(MemorySegment iprc) {
        MemorySegment sizes = libraw_data_t.sizes(iprc);
        int w = libraw_image_sizes_t.width(sizes) & 0xFFFF;
        int h = libraw_image_sizes_t.height(sizes) & 0xFFFF;
        int flip = libraw_image_sizes_t.flip(sizes);
        if (w <= 0 || h <= 0) {
            w = libraw_h.libraw_get_iwidth(iprc);
            h = libraw_h.libraw_get_iheight(iprc);
        }
        return new int[]{w, h, flip};
    }

    /**
     * True if the request is so small that the half size image is still at
     * least as big as the request. Conservative: it is checked for both
     * orientations, because the requested size is given for the rotated image.
     */
    static boolean previewFitsHalf(int imageW, int imageH, int reqW, int reqH) {
        if (imageW <= 0 || imageH <= 0) {
            return false;
        }
        double required = 0;
        if (reqW > 0) {
            required = Math.max(required, Math.max((double) reqW / imageW, (double) reqW / imageH));
        }
        if (reqH > 0) {
            required = Math.max(required, Math.max((double) reqH / imageH, (double) reqH / imageW));
        }
        return required > 0 && required <= HALF_SIZE_MAX_SCALE;
    }

    private static String ms(long fromNanos, long toNanos) {
        return String.valueOf((toNanos - fromNanos) / 1_000_000);
    }

    /** Four floats of a float[4] field as text (for diagnostics). */
    private static String floats4(MemorySegment s) {
        if (s.byteSize() < 16) {
            return "n/a";
        }
        return s.getAtIndex(ValueLayout.JAVA_FLOAT, 0) + ", " + s.getAtIndex(ValueLayout.JAVA_FLOAT, 1) + ", "
                + s.getAtIndex(ValueLayout.JAVA_FLOAT, 2) + ", " + s.getAtIndex(ValueLayout.JAVA_FLOAT, 3);
    }

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
    /**
     * @return true if the last decode ran with half_size (preview or the
     * halfSizeOutput setting), so the result is smaller than the full image
     */
    public boolean isLastDecodeHalfSize() {
        return lastDecodeHalfSize;
    }

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