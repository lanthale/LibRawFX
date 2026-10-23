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

Actually only the following raw formats are enabled (see class `RAWDescriptor.java`):
All formats are now enabled which are support by libraw.
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
HashMap<String, String> metaData = new LibrawImage(initialFile.getAbsolutePath()).getMetaData();
VBox vb = new VBox();
metaData.entrySet().forEach((entry) -> {
   Label l = new Label(entry.getKey() + " " + entry.getValue());
   vb.getChildren().add(l);
});
```

- **RAW Settings**
You can get the default settings for all camera models with the call RAWImageLoaderFactory.getDecoderSettings(). Afterwards use the getters/setters to update the raw settings. There is also a method called getCameraMaker() which extracts the camera model and afterwards you can now build a model based raw setting engine and feed always before loading an image with the right settings for the camera model. 

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
- Classes inside `org.librawjava` are Javafx classes for unofficial API
- Classes inside `org.librawimageio` are ImageIO Classes for Swing and JavaFX
- Inside of these packages above I exposed also the raw interface to libraw
- The main class to interact with the native lib is the class `LibrawImage.java` in package `org.librawfx`
     
[![JFXCentral](https://img.shields.io/badge/Find_me_on-JFXCentral-blue?logo=googlechrome&logoColor=white)](https://www.jfx-central.com/libraries/librawfx)
