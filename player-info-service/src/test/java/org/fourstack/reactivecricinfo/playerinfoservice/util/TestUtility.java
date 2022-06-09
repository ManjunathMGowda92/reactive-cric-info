package org.fourstack.reactivecricinfo.playerinfoservice.util;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class TestUtility {

    public static String getFileContent(String fileNameWithPath) {
        String content = "";
        try {
            File file = ResourceUtils.getFile("classpath:" + fileNameWithPath);
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException: Specified file not found : {}", fileNameWithPath, e);
        } catch (IOException e) {
            log.error("IOException occurred while processing the file : {}", fileNameWithPath, e);
        }
        return content;
    }
}
