package org.fourstack.reactivecricinfo.playerinfoservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class EntityGenerator {
    private static final String PLAYER_DAO_LIST_FILEPATH = "json-files/player-profile-dao.json";
    private static final String PLAYER_DTO_LIST_FILEPATH = "json-files/player-info-dto.json";
    private static final String SINGLE_PLAYER_DAO_FILEPATH = "json-files/player-dao-single.json";
    private static final String SINGLE_PLAYER_DTO_FILEPATH = "json-files/player-dto-single.json";


    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    /**
     * Method returns List of PlayerProfile Objects.
     *
     * @return List of PlayerProfile Objects.
     */
    public static List<PlayerProfile> getPlayerProfileList() {
        PlayerProfile[] playerProfiles = {};
        try {
            playerProfiles = objectMapper.readValue(
                    TestUtility.getFileContent(PLAYER_DAO_LIST_FILEPATH),
                    PlayerProfile[].class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while processing PlayerProfile objects", e);
        }
        return Arrays.asList(playerProfiles);
    }

    /**
     * Method returns List of PlayerInfoDTO Objects.
     *
     * @return List of PlayerInfoDTO Objects.
     */
    public static List<PlayerInfoDTO> getPlayerDTOList() {
        PlayerInfoDTO[] playerDtoList = {};
        try {
            playerDtoList = objectMapper.readValue(
                    TestUtility.getFileContent(PLAYER_DTO_LIST_FILEPATH),
                    PlayerInfoDTO[].class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while processing PlayerInfoDTO objects", e);
        }
        return Arrays.asList(playerDtoList);
    }

    /**
     * Method returns the PlayerProfile DAO Object.
     *
     * @return PlayerProfile Object.
     */
    public static PlayerProfile getPlayerProfile() {
        PlayerProfile profile = null;
        try {
            profile = objectMapper.readValue(
                    TestUtility.getFileContent(SINGLE_PLAYER_DAO_FILEPATH),
                    PlayerProfile.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while processing PlayerProfile object", e);
        }
        return profile;
    }

    /**
     * Method returns PlayerInfoDTO object.
     *
     * @return PlayerInfoDTO Object.
     */
    public static PlayerInfoDTO getPlayerInfoDTO() {
        PlayerInfoDTO playerInfoDTO = null;
        try {
            playerInfoDTO = objectMapper.readValue(
                    TestUtility.getFileContent(SINGLE_PLAYER_DTO_FILEPATH),
                    PlayerInfoDTO.class
            );
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occurred while processing PlayerInfoDTO object", e);
        }

        return playerInfoDTO;
    }

}
