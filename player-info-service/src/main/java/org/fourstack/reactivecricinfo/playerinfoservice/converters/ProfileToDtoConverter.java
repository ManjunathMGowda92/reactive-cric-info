package org.fourstack.reactivecricinfo.playerinfoservice.converters;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerBasicInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileToDtoConverter implements Converter<PlayerProfile, PlayerInfoDTO> {

    /**
     * Method which converts the {@link PlayerProfile} DAO Object
     * to {@link PlayerInfoDTO} Object.
     *
     * @param source DAO Object for Conversion : {@link PlayerProfile}
     * @return Converted DTO Object : {@link PlayerInfoDTO}
     */
    @Override
    public PlayerInfoDTO convert(PlayerProfile source) {
        log.info("Conversion of PlayerProfile Object to PlayerInfoDTO Object.");

        PlayerInfoDTO target = new PlayerInfoDTO();
        target.setPlayerId(source.getPlayerId());

        setBasicInfo(source, target);
        setMultiMediaContent(source, target);

        target.setRoles(source.getRoles());
        target.setBattingStyle(source.getBattingStyle());
        target.setBowlingStyle(source.getBowlingStyle());

        return target;
    }

    /**
     * Method to set Multi-media information from DAO Object to DTO Object.
     *
     * @param source DAO Input Source i.e. {@link PlayerProfile}
     * @param target DTO target Source i.e {@link PlayerInfoDTO}
     */
    private void setMultiMediaContent(PlayerProfile source, PlayerInfoDTO target) {

        MultiMediaDocument imageInfo = source.getImageInfo();
        if (imageInfo != null) {
            MultiMediaDocument document = new MultiMediaDocument();
            document.setFileName(imageInfo.getFileName());
            document.setFileContent(imageInfo.getFileContent());
            document.setFileType(imageInfo.getFileType());
            target.setImageInfo(document);
        }
    }

    /**
     * Method to set the Basic Info of the Player
     *
     * @param source DAO Source Object
     * @param target DTO target Object
     */
    private void setBasicInfo(PlayerProfile source, PlayerInfoDTO target) {
        PlayerBasicInfoDTO basicInfo = new PlayerBasicInfoDTO();
        basicInfo.setFirstName(source.getFirstName());
        basicInfo.setLastName(source.getLastName());
        basicInfo.setMiddleName(source.getMiddleName());

        basicInfo.setCountry(source.getCountry());
        basicInfo.setBirthDate(source.getDob());
        basicInfo.setBirthPlace(source.getPlaceOfBirth());

        basicInfo.setNickName(source.getNickName());
        basicInfo.setGender(source.getGender());

        target.setBasicInfo(basicInfo);
    }
}
