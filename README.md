# LibRawFX
Integration of LibRaw (https://www.libraw.org) library for JavaFX for all major operating systems (Linux, Windows, OSX). 
All raw formats can be loaded with the Image class and manipulated by Pixelwriter/Pixelreader. Limitation is that the image class only supports 8-bit color deph but converts all 16bit image format to 8bit automatically.

New is now that the plugin supports now a moore general plugin type which is ImageIO. Therefore also Swing developers can use the lib to load images.
Beginning of 1.9.3 the ImageIO function is included. On Javafx side you can choose which method you will use, all others use automatically ImageIO. The support for ImageIO is now beta.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?hosted_button_id=CXWX6CAQ5MMV4)

**JDK 18 is required for v1.8.0** because of the foreign linker API usage and the big changes for threading happenend in Panama in JDK18

**JDK 25 and JavaFX 25/26/27 is required for v2.0.1** because of the foreign linker API usage, changes in JavaFX 24 and the big changes for threading happenend in Panama in JDK22. Major rewrite without separate Win/Linux code anymore.

## Status
Now the lib is in production ready status. That means it is tested on all operating systems (OSX, Linux, Win10) and under different threading scenarious.

Supported OS:
- Linux (min. glibc 2.27 which means Ubuntu 18 or later)
- OSX up to 12.5 including Apple M1
- Windows 10/11

I have integrated LibRaw SNAPSHOT version 0.21.4 (https://www.libraw.org/download#stable)

All formats are now enabled which are support by libraw:
Some of those are:
- Adobe DNG
- Nikon NEF
- Canon CRW/CR2
- SIMGA Merrill/Quattro X3F
- Fuji X-Trans RAF
- Sony
- Leica

You can now specify for each camera model different settings for the raw decoder (sample below).
ImageIO plugin is provided and should be automatically installed.

## Open topics
- Writing of metadata back to raw files

## Usage
Point to the maven coordinates:

**JDK18**
```
<dependency>  
    <groupId>org.librawfx</groupId>    
    <artifactId>LibRawFX</artifactId>  
    <version>1.8.0</version>  
</dependency>  
```
**JDK25**
```
<dependency>  
    <groupId>org.librawfx</groupId>    
    <artifactId>LibRawFX</artifactId>  
    <version>2.0.1</version>  
</dependency>  
```

Version 2.1.0 adds the 16 bit API for image editing (`readPixelData16`, `RawPixels16`), the
`RawFormatDetector`, readable errors (`LibrawException`) and faster previews (see below).

## Usage

LibRaw is loaded through the Foreign Function & Memory API. Start your application with
`--enable-native-access=org.librawfx` (module path) or `--enable-native-access=ALL-UNNAMED` (classpath).

### ImageIO mode

Add the dependency. RAW files are then read like any other image:

```java
BufferedImage img = ImageIO.read(new File("DSC_0001.NEF"));
```

In JavaFX, `new Image(file.toURI().toString(), width, height, true, true)` uses the same reader
through the ImageIO fallback of JavaFX. Non-RAW files (TIFF, PSD, WebP, ...) are handed over to
the next ImageIO reader (e.g. TwelveMonkeys).

#### Camera settings

The key is `"<maker> <model>"`, exactly as reported by LibRaw (case sensitive). Read it from a real file:

```java
LibrawImage.loadLibs(null);
System.out.println(new LibrawImage("DSC_0001.NEF", RAWImageIOReader.getSettings()).getCameraMaker());
// e.g. "Nikon D850"
```

Configure the settings once at startup, before the first image is loaded:

```java
RawDecoderSettings d850 = new RawDecoderSettings();
// set the values with the setters of RawDecoderSettings
RAWImageIOReader.getSettings().put("Nikon D850", d850);      // this model only
// optional, for all other cameras:
// RAWImageIOReader.getSettings().put("Default", defaultSettings);
```

Thread safety: fully configure the settings object before you put it into the map and do not change
it while images are loading. To change values, create a new object and replace the map entry.

For a single call, independent of the camera model, pass the settings through the read parameter:
`RAWImageIOImageReadParam.setSettings(...)`.

### JavaFX internal mode

Call this once, before the first image is loaded (for example at the start of `start()`):

```java
RAWImageLoaderFactory.install();
Map<String, RawDecoderSettings> settings = RAWImageLoaderFactory.getDecoderSettings();

RawDecoderSettings sigma = new RawDecoderSettings();
sigma.setEnableExposureCorrection(true);
sigma.setExposureCorrection(1);
sigma.setExpoCorrectionShift(2.0f);
sigma.setBrightNess(6.0f);
settings.put("Sigma DP2 Merrill", sigma);                    // key = "<maker> <model>"
```

The settings take effect on the next image that is loaded. Find the key with `getCameraMaker()`
(returns "maker model") or read it from the metadata. This mode also needs
`--add-exports=javafx.graphics/com.sun.javafx.iio=org.librawfx` and
`--add-exports=javafx.graphics/com.sun.javafx.iio.common=org.librawfx`.

Note: the settings of the ImageIO mode and of the JavaFX internal mode are separate. Settings put
into one are not used by the other.

### Using `LibrawImage` directly

Both modes above are built on `org.librawjava.LibrawImage`. You can use it yourself, e.g. for
editing or batch processing. Without `RAWImageLoaderFactory.install()` load the native library once:

```java
LibrawImage.loadLibs(null);              // null = default temp directory for the native files
```

Create one `LibrawImage` per decode. It is cheap; the second argument is the settings map that is
searched for `"<maker> <model>"` and then `"Default"`:

```java
LibrawImage raw = new LibrawImage(file.toString(), RAWImageLoaderFactory.getDecoderSettings());
```

### 16 bit decoding for image editing (since 2.1.0)

JavaFX images have 8 bit per channel. For editing (exposure, white balance, curves) you want the
full precision of the sensor, so decode to 16 bit and convert to 8 bit only for display or export:

```java
byte[] bytes = Files.readAllBytes(file);
LibrawImage raw = new LibrawImage(file.toString(), RAWImageLoaderFactory.getDecoderSettings());
RawPixels16 px = raw.readPixelData16(bytes, true);    // true = linear light

int w = px.width();
int h = px.height();
short[] rgb = px.data();                              // R, G, B interleaved, w * h * 3 values
int red = rgb[(y * w + x) * 3] & 0xFFFF;              // unsigned: always read with & 0xFFFF
```

`RawPixels16` is a plain record without JavaFX types, so it also works in Swing or on a server.

- **`linear = true`** gives linear light: twice the value means twice the light. This is the right
  input for exposure (multiply by 2^stops) and white balance (multiply per channel). LibRaw is
  forced to gamma 1.0, no auto brightness, brightness 1.0 and **sRGB primaries** (`output_color = 1`,
  no output ICC profile). These settings of the profile are ignored in linear mode.
- **`linear = false`** gives gamma encoded values exactly as configured in the profile (colour space,
  gamma, auto brightness).
- The result is always 16 bit and full size; `sixteenBitsImage` in the settings is not needed. Only
  `halfSizeOutput = true` in the profile halves the size.
- All other settings (white balance, demosaicing, noise reduction, highlights, ...) apply as usual.

To show linear data, apply the sRGB curve and reduce to 8 bit. A minimal (not optimised) version:

```java
WritableImage img = new WritableImage(w, h);
PixelWriter pw = img.getPixelWriter();
for (int y = 0; y < h; y++) {
    for (int x = 0; x < w; x++) {
        int i = (y * w + x) * 3;
        pw.setArgb(x, y, 0xFF000000 | toSrgb8(rgb[i]) << 16 | toSrgb8(rgb[i + 1]) << 8 | toSrgb8(rgb[i + 2]));
    }
}

static int toSrgb8(short v) {
    double l = (v & 0xFFFF) / 65535.0;
    double e = l <= 0.0031308 ? 12.92 * l : 1.055 * Math.pow(l, 1 / 2.4) - 0.055;
    return (int) Math.round(e * 255);
}
```

In a real application use a lookup table with 65536 entries instead of `Math.pow` per pixel, and add
dithering if you see banding in smooth gradients.

The 8 bit methods (`readPixelDataFromStream`, `readPixelData`, the JavaFX and ImageIO loaders) always
deliver 8 bit, independent of `sixteenBitsImage`.

### Settings for a single decode

`setSettingsOverride` uses the given settings for this instance only; the shared map is not
touched. Together with `copy()` this is handy for a settings dialog with a live preview:

```java
RawDecoderSettings trial = RAWImageLoaderFactory.getDecoderSettings().get("Default").copy();
trial.setUseUserMultipliers(true);
trial.setUserMultipliers(2.1f, 1.0f, 1.6f, 1.0f);     // R, G1, B, G2 (disables camera and auto WB)

LibrawImage raw = new LibrawImage(file.toString(), new ConcurrentHashMap<>());
raw.setSettingsOverride(trial);
RawPixels16 preview = raw.readPixelData16(bytes, true);
```

To make the values permanent, put a new, fully configured object into the map (see thread safety
above).

### Is it a RAW file? Errors

Many RAW formats are TIFF files internally. `RawFormatDetector` decides from the first bytes of a
file (`HEADER_SIZE` = 64 KB is enough) whether LibRaw should decode it:

```java
byte[] header = new byte[RawFormatDetector.HEADER_SIZE];
int n;
try (InputStream in = Files.newInputStream(file)) {
    n = in.readNBytes(header, 0, header.length);
}
boolean raw = RawFormatDetector.isRaw(Arrays.copyOf(header, n));
```

The loaders use it to hand non-RAW files (TIFF, PSD, WebP) to the next ImageIO reader. Check with
it before decoding: not every non-RAW file is reported as unsupported by LibRaw, some (e.g. plain
text) give a read error instead. Decoding errors are thrown as `LibrawException` (a subclass of
`IOException`):

```java
try {
    RawPixels16 px = raw.readPixelData16(bytes, true);
} catch (LibrawException ex) {
    if (ex.isUnsupportedFormat()) {
        // LibRaw does not support this format: load it with ImageIO or JavaFX instead
    } else {
        showError(ex.getUserMessage());               // readable text, without the LibRaw code
        int code = ex.getCode();                      // raw LibRaw error code for logging
    }
}
```

### Fast previews (half_size)

If a size is requested that is at most about half of the image (thumbnails, previews, `new Image(url,
200, 200, true, true)`), LibRaw decodes with `half_size`. That is several times faster and looks the
same at that size. You can also request a size directly:

```java
byte[] rgb8 = raw.readPixelDataFromStream(bytes, 400, 300);   // RGB, 3 bytes per pixel
int w = raw.getImageWidth();                                  // real size of the result
int h = raw.getImageHeight();
```

`readPixelDataFromStream` returns the decoded size (e.g. half of the image), not the requested
one. The ImageIO reader scales to exactly the requested size if the caller sets `sourceRenderSize`
(JavaFX does). To switch the speed-up off: per profile with `setHalfSizeForPreviews(false)`, globally with
`LibrawImage.setPreviewHalfSizeOverride(Boolean.FALSE)` or `-Dlibrawfx.halfSizeForPreviews=false`.

### Logging

Set the logger `org.librawjava` to `FINE` to see the time of each decode phase, the white balance
multipliers reported by LibRaw (`cam_mul`, `pre_mul`) and the half_size decision. The `pre_mul`
values are a good start for `setUserMultipliers(...)` when a camera has no usable white balance in
its files (e.g. a green cast with some Sigma X3F files).


- and add the following lines to your java config on JDK 18:
```
--add-modules jdk.incubator.foreign --enable-native-access=org.librawfx  
--add-exports=javafx.graphics/com.sun.javafx.iio=org.librawfx 
--add-exports=javafx.graphics/com.sun.javafx.iio.common=org.librawfx
```

- and add the following lines to your java config on JDK 25 if you use Javafx otherwise only the "enable-native" line is necessary:
```
--enable-native-access=org.librawfx  
--add-exports=javafx.graphics/com.sun.javafx.iio=org.librawfx 
--add-exports=javafx.graphics/com.sun.javafx.iio.common=org.librawfx
```


- **Metadata**
Just create an instance of class LibrawImage with the file to get the metadata and print the return values
```
HashMap<String, String> metaData = new LibrawImage(initialFile.getAbsolutePath(),
        RAWImageLoaderFactory.getDecoderSettings()).getMetaData();
VBox vb = new VBox();
metaData.entrySet().forEach((entry) -> {
   Label l = new Label(entry.getKey() + " " + entry.getValue());
   vb.getChildren().add(l);
});
```

- **Module name: org.librawfx**

You can have a look into the class TestAPP.java, TestAppSwing.java, TestAppImageIO to see how to use it, but generally just create an Image with the URL/stream and add it to the image view:

```
  Image img=new Image(initialFile.toURI().toURL().toString(), false);  
  ImageView view = new ImageView();  
  view.setFitHeight(200);  
  view.setFitWidth(200);  
  view.setPreserveRatio(true);  
  stack.getChildren().add(view);  
  view.setImage(img);
```  

You can also use the lib without adding the file handler. What I mean is that you can also forget the "...install" line and just load a file URL with the lib (see the `TestApp.java` to see how it works).

## Steps to create your own build:
- OpenJDK/Adoptium 25 or newer
- JavaFX 25 or newer
- SET JAVA_HOME variable
- Execute `mvn clean compile package -f LibRawFX/pom.xml`
- To run the example execute `mvn javafx:run@cli-default`
- IDE Integration: execute as a maven goal `javafx:run@ide-debug` or `javafx:run@ide-profile`

# Notes:
- The lib is using unoffical JavaFX interfaces
- The lib is using heavily the foreign memory API from Java 22 including functions from the project Panama and therefore the tool `JExtract` (Must be build separately)
- All classes inside of package `org.libraw.nativ` are generated by jextract but tuned for operating system specifica
- Classes inside `org.librawdecodersettings` are for decodersettings including the default values
- Classes inside `org.librawfx` are the JavaFX loader classes (unofficial API)
- Classes inside `org.librawjava` have no JavaFX dependency: `LibrawImage`, `RawPixels16`, `RawFormatDetector`, `LibrawException`
- Classes inside `org.librawimageio` are ImageIO Classes for Swing and JavaFX
- Inside of these packages above I exposed also the raw interface to libraw
- The main class to interact with the native lib is the class `LibrawImage.java` in package `org.librawjava`
     
[![JFXCentral](https://img.shields.io/badge/Find_me_on-JFXCentral-blue?logo=googlechrome&logoColor=white)](https://www.jfx-central.com/libraries/librawfx)
