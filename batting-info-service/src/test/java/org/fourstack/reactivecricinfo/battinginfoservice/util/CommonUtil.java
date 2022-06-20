package org.fourstack.reactivecricinfo.battinginfoservice.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CommonUtil {

    public static String getFileContentUsingResourceStream(String filenameWithPath, ClassLoader classLoader) {
        log.info("CommonUtil: Start getFileContentUsingResourceStream() method");
        StringBuffer buffer = new StringBuffer();
        try (
                InputStream inputStream = classLoader.getResourceAsStream(filenameWithPath);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            log.error("IOException occurred while processing the file - {}", filenameWithPath, e);
        }
        return buffer.toString();
    }
}
