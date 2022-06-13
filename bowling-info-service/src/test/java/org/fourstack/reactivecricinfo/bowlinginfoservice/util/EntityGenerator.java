package org.fourstack.reactivecricinfo.bowlinginfoservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;

@Slf4j
public class EntityGenerator {
    private static String SINGLE_BOWLING_DTO_PATH = "json-files/bowling-info-dto.json";
    private static String SINGLE_BOWLING_DAO_PATH = "json-files/bowling-info-dao.json";

    private static ObjectMapper objectMapper;
    private static EntityGenerator obj;

    static {
        objectMapper = new ObjectMapper();
        obj = new EntityGenerator();
    }

    public static BowlingInfoDTO bowlingInfoDTO() {
        BowlingInfoDTO bowlingInfoDTO = null;
        try {
            bowlingInfoDTO = objectMapper.readValue(
                    TestUtils.getFileContentUsingResource(
                            SINGLE_BOWLING_DTO_PATH, obj.getClass().getClassLoader()),
                    BowlingInfoDTO.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while " +
                    "creating BowlingInfoDTO object", e);
        }
        return bowlingInfoDTO;
    }

    public static BowlingInfo bowlingInfoDAO() {
        BowlingInfo bowlingInfo = null;
        try {
            bowlingInfo =objectMapper.readValue(
                    TestUtils.getFileContentUsingResource(
                            SINGLE_BOWLING_DAO_PATH, obj.getClass().getClassLoader()),
                    BowlingInfo.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while " +
                    "creating BowlingInfo Object.", e);
        }
        return bowlingInfo;
    }
}
