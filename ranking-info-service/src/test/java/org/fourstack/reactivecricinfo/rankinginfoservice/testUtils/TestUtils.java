package org.fourstack.reactivecricinfo.rankinginfoservice.testUtils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@Slf4j
public class TestUtils {

    /**
     * Reads the content of the specified file under the resources folder.
     * Method requires the ClassLoader instance of the class from where it's
     * being called.
     *
     * @param filenameWithPath file path with folder (requires path from resources folder)
     * @param classLoader      ClassLoader instance from where the method is called.
     * @return String content of the File.
     */
    public static String getFileContentUsingResource(String filenameWithPath, ClassLoader classLoader) {
        String content = "";
        try {
            URL resource = classLoader.getResource(filenameWithPath);
            if (resource == null) {
                throw new IllegalArgumentException("Specified file not found!!");
            }

            File file = new File(resource.getFile());
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            log.error("IOException occurred while processing the file :{}", filenameWithPath, e);
        }

        return content;
    }
}
