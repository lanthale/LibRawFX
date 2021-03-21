# LibRawFX
Integration of LibRaw library for Javafx. All raw formats can be loaded with the Image class and manipulated by Pixelwriter/Pixelreader. Limitation is that the image class only supports 8-bit color deph but converts all 16bit image format to 8bit automatically.

# Limitation
Actually only the following raw formats are enabled:
- Nikon NEF
- Canon CRW/CR2
- SIMGA Merrill/Quattro X3F
- Fuji X-Trans RAF

The lib is using allot of memory (always double the memory of the image). This is under investigation to improve it further. 

# Usage
Point to the maven coordinates:
<dependency>
    <groupId>org.librawfx</groupId>
    <artifactId>LibRawFX</artifactId>
    <version>1.0</version>
</dependency>

- In the Class where the start method is add as one of the first lines:
     "RAWImageLoaderFactory.install();"
- Add the following lines to your java config:
     "--add-modules jdk.incubator.foreign -Dforeign.restricted=permit"
     "--add-exports=javafx.graphics/com.sun.javafx.iio=org.librawfx"
     "--add-exports=javafx.graphics/com.sun.javafx.iio.common=org.librawfx"
- Module name: org.librawfx


You can have a look into the class TestAPP.java to see how to use it, but generally just create an Image with the URL/stream and add it to the image view:
  Image img=new Image(initialFile.toURI().toURL().toString(), false);
  ImageView view = new ImageView();
  view.setFitHeight(200);
  view.setFitWidth(200);
  view.setPreserveRatio(true);
  stack.getChildren().add(view);
  view.setImage(img);
You can also use it without adding as an image format support. What I mean you can also forget the ".install" line and just load a file URL with the lib (see the TestApp.java to see how it works).

# Compile yourself:
- OpenJDKA/Adoptopenjdk 16 or newer
- JavaFX 11 or newer (15 is recommended)
- SET JAVA_HOME variable
- Execute "maven clean install"
- To run the example execute "maven javafx:run@cli-default"
- IDE Integration: execute as a maven goal "javafx:run@ide-debug" or "javafx:run@ide-profile" 

# Notes:
- The lib is using unoffical JavaFX interfaces
     
