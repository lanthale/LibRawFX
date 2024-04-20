# LibRawFX
Integration of LibRaw (https://www.libraw.org) library for JavaFX for all major operating systems (Linux, Windows, OSX). 
All raw formats can be loaded with the Image class and manipulated by Pixelwriter/Pixelreader. Limitation is that the image class only supports 8-bit color deph but converts all 16bit image format to 8bit automatically.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?hosted_button_id=CXWX6CAQ5MMV4)

**JDK 18 is required for v1.8.0** because of the foreign linker API usage and the big changes for threading happenend in Panama in JDK18

**JDK 22 and JavaFX 22 is required for v1.8.6** because of the foreign linker API usage, changes in JavaFX 21 and the big changes for threading happenend in Panama in JDK22. Major rewrite without separate Win/Linux code anymore.

## Status
Now the lib is in production ready status. That means it is tested on all operating systems (OSX, Linux, Win10) and under different threading scenarious.

Supported OS:
- Linux (min. glibc 2.27 which means Ubuntu 18 or later)
- OSX up to 12.5 including Apple M1
- Windows 10/11

I have integrated LibRaw SNAPSHOT version 0.21.2 (https://www.libraw.org/news/libraw-0-21-2-release)

Actually only the following raw formats are enabled (see class `RAWDescriptor.java`):
- Adobe DNG
- Nikon NEF
- Canon CRW/CR2
- SIMGA Merrill/Quattro X3F
- Fuji X-Trans RAF
- Sony
- Leica

The lib now resized the image in memory before sending it back to the image class (the same as the Javafx is doing for PNG/JPG).
You can now specify for each camera model different settings for the raw decoder (sample below).

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
**JDK22**
```
<dependency>  
    <groupId>org.librawfx</groupId>    
    <artifactId>LibRawFX</artifactId>  
    <version>1.8.7</version>  
</dependency>  
```

- In the Class where the start method is add as one of the first lines the following code to install the file handler:

     `RAWImageLoaderFactory.install();
     HashMap<String, RawDecoderSettings> decoderSettings = RAWImageLoaderFactory.getDecoderSettings(); //.setEnableExposureCorrection(false);
     decoderSettings.put("Sigma DP2 Merrill", new RawDecoderSettings());
     decoderSettings.get("Sigma DP2 Merrill").setEnableExposureCorrection(true);
     decoderSettings.get("Sigma DP2 Merrill").setExposureCorrection(1);
     decoderSettings.get("Sigma DP2 Merrill").setExpoCorrectionShift(2.0f);
     decoderSettings.get("Sigma DP2 Merrill").setBrightNess(6.0f);
     //use the setters provided. They settings will take effect on the next call. The camera maker can be found out by method call getCameraMaker() or extract from the Metadata in form "maker model"
      
       

- and add the following lines to your java config on JDK 18:
```
--add-modules jdk.incubator.foreign --enable-native-access=org.librawfx  
--add-exports=javafx.graphics/com.sun.javafx.iio=org.librawfx 
--add-exports=javafx.graphics/com.sun.javafx.iio.common=org.librawfx
```

- and add the following lines to your java config on JDK 22:
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

You can have a look into the class TestAPP.java to see how to use it, but generally just create an Image with the URL/stream and add it to the image view:

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
- OpenJDK/Adoptium 22 or newer
- JavaFX 20 or newer (22 is recommended)
- SET JAVA_HOME variable
- Execute `mvn clean compile package -f LibRawFX/pom.xml`
- To run the example execute `mvn javafx:run@cli-default`
- IDE Integration: execute as a maven goal `javafx:run@ide-debug` or `javafx:run@ide-profile`

# Notes:
- The lib is using unoffical JavaFX interfaces
- The lib is using heavily the foreign memory API from Java 22 including functions from the project Panama and therefore the tool `JExtract` (Must be build separately)
- All classes inside of package `org.librawnativ` are generated by jextract but tuned for operating system specifica
- Inside of these packages above I exposed also the raw interface to libraw
- The main class to interact with the native lib is the class `LibrawImage.java` in package `org.librawfx`
     
