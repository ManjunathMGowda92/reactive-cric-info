package org.fourstack.reactivecricinfo.rankinginfoservice.testUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.IccRanking;

import static org.fourstack.reactivecricinfo.rankinginfoservice.testUtils.TestUtils.*;

@Slf4j
public class EntityGenerator {
    private static ObjectMapper objectMapper;
    private static EntityGenerator obj;

    private static String RANKING_DAO_FILEPATH = "json-files/ranking-info-dao.json";
    private static String RANKING_DTO_FILEPATH = "json-files/ranking-info-dto.json";

    static {
        obj = new EntityGenerator();
        objectMapper = new ObjectMapper();
    }

    /**
     * Method which returns {@link IccRanking} Object. {@link IccRanking}
     * object will be created using the content of ranking-info-dao.json file.
     *
     * @return IccRanking Object.
     */
    public static IccRanking iccRanking() {
        IccRanking iccRanking = null;
        try {

            iccRanking = objectMapper.readValue(
                    getFileContentUsingResource(RANKING_DAO_FILEPATH, obj.getClass().getClassLoader()),
                    IccRanking.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while creating IccRanking object.", e);
        }
        return iccRanking;
    }

    /**
     * Method which returns {@link IccRankDTO} Object. {@link IccRankDTO}
     * object will be created using the content of ranking-info-dto.json file.
     *
     * @return IccRankDTO object.
     */
    public static IccRankDTO iccRankDTO() {
        IccRankDTO dto = null;
        try {

            dto = objectMapper.readValue(
                    getFileContentUsingResource(RANKING_DTO_FILEPATH, obj.getClass().getClassLoader()),
                    IccRankDTO.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while creating IccRankDTO object.", e);
        }
        return dto;
    }
}
