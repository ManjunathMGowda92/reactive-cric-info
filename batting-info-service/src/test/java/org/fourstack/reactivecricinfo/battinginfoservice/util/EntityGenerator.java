package org.fourstack.reactivecricinfo.battinginfoservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;

import static org.fourstack.reactivecricinfo.battinginfoservice.util.CommonUtil.*;

@Slf4j
public class EntityGenerator {
    private static String SINGLE_BATTING_DTO_PATH = "json-files/batting-info-dto.json";
    private static String SINGLE_BATTING_DAO_PATH = "json-files/batting-info-dao.json";

    private static ObjectMapper objectMapper;
    private static EntityGenerator obj;

    static {
        objectMapper = new ObjectMapper();
        obj = new EntityGenerator();
    }

    public static BattingInfoDTO battingInfoDTO() {
        BattingInfoDTO dto = null;
        try {
            dto = objectMapper.readValue(
                    getFileContentUsingResourceStream(SINGLE_BATTING_DTO_PATH, obj.getClass().getClassLoader()),
                    BattingInfoDTO.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while creating DTO obj", e);
        }

        return dto;
    }

    public static BattingInfo battingInfoDAO() {
        BattingInfo daoObj = null;
        try {
            daoObj = objectMapper.readValue(
                    getFileContentUsingResourceStream(SINGLE_BATTING_DAO_PATH, obj.getClass().getClassLoader()),
                    BattingInfo.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while creating DAO obj", e);
        }
        return daoObj;
    }
}
