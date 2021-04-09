/*
 * Class NativeUtils is published under the The MIT License:
 *
 * Copyright (c) 2012 Adam Heinrich <adam@adamh.cz>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.librawfx;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple library class which helps with loading dynamic libraries stored in
 * the JAR archive. These libraries usually contain implementation of some
 * methods in native code (using JNI - Java Native Interface).
 *
 * @see
 * <a href="http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar">http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar</a>
 * @see
 * <a href="https://github.com/adamheinrich/native-utils">https://github.com/adamheinrich/native-utils</a>
 *
 */
public class NativeUtils {

    /**
     * The minimum length a prefix for a file has to have according to
     * {@link File#createTempFile(String, String)}}.
     */
    private static final int MIN_PREFIX_LENGTH = 3;
    public static final String NATIVE_FOLDER_PATH_PREFIX = "nativeutils";

    /**
     * Temporary directory which will contain the DLLs.
     */
    private static File temporaryDir;

    /**
     * Private constructor - this class will never be instanced
     */
    private NativeUtils() {
    }

    /**
     * Loads library from current JAR archive
     *
     * The file from JAR is copied into system temporary directory and then
     * loaded.The temporary file is deleted after exiting. Method uses String as
     * filename because the pathname is "abstract", not system-dependent.
     *
     * @param tempDir Directory where to place the lib files. On OSX it is no
     * longer allowed to use the temp directory by the system. Instat you have
     * to specify the user directory or on linux the LD_LIBRARAY_PATH directory
     * @param path The path of file inside JAR as absolute path (beginning with
     * '/'), e.g. /package/File.ext
     * @throws IOException If temporary file creation or read/write operation
     * fails
     * @throws IllegalArgumentException If source file (param path) does not
     * exist
     * @throws IllegalArgumentException If the path is not absolute or if the
     * filename is shorter than three characters (restriction of
     * {@link File#createTempFile(java.lang.String, java.lang.String)}).
     * @throws FileNotFoundException If the file could not be found inside the
     * JAR.
     */
    public static String[] loadLibraryFromJar(String tempDir, String... path) throws IOException {
        Logger.getLogger(NativeUtils.class.getName()).log(Level.FINEST, null, "Extracting native lib to " + tempDir + "...");
        if (null == path || !path[0].startsWith("/")) {
            throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }

        // Obtain filename from path
        String[] parts = path[0].split("/");
        String filename = (parts.length > 1) ? parts[parts.length - 1] : null;

        // Check if the filename is okay
        if (filename == null || filename.length() < MIN_PREFIX_LENGTH) {
            throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
        }

        if (tempDir == null) {
            // Prepare temporary file
            if (temporaryDir == null) {
                temporaryDir = createTempDirectory(NATIVE_FOLDER_PATH_PREFIX);
                //temporaryDir.deleteOnExit();
            }
        } else {
            temporaryDir = new File(tempDir);
            if (temporaryDir.exists() == false) {
                temporaryDir.mkdirs();
                //temporaryDir.deleteOnExit();
            }
        }

        String[] nativeLibs = new String[path.length];
        for (int i = 0; i < path.length; i++) {
            String part = path[i];
            String filenm = new File(part).getName();
            File temp = new File(temporaryDir, filenm);
            try (InputStream is = NativeUtils.class.getResourceAsStream(part)) {
                Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                nativeLibs[i] = temp.toString();
            } catch (IOException e) {
                temp.delete();
                throw e;
            } catch (NullPointerException e) {
                temp.delete();
                throw new FileNotFoundException("File " + path + " was not found inside JAR.");
            }
        }
        Logger.getLogger(NativeUtils.class.getName()).log(Level.FINEST, null, "Extracting native lib to " + tempDir + "...finished");
        return nativeLibs;
    }

    private static boolean isPosixCompliant() {
        try {
            return FileSystems.getDefault()
                    .supportedFileAttributeViews()
                    .contains("posix");
        } catch (FileSystemNotFoundException
                | ProviderNotFoundException
                | SecurityException e) {
            return false;
        }
    }

    private static File createTempDirectory(String prefix) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        File generatedDir = new File(tempDir, prefix + System.nanoTime());

        if (!generatedDir.mkdir()) {
            throw new IOException("Failed to create temp directory " + generatedDir.getName());
        }

        return generatedDir;
    }
}
