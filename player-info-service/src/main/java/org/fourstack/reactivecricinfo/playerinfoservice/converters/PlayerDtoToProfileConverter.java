package org.fourstack.reactivecricinfo.playerinfoservice.converters;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerBasicInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;
import org.fourstack.reactivecricinfo.playerinfoservice.util.RandomPlayerIdGenerator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PlayerDtoToProfileConverter implements
        Converter<PlayerInfoDTO, PlayerProfile> {

    /**
     * Method which converts the DTO {@link PlayerInfoDTO} Object
     * to DAO {@link PlayerProfile} Object.
     *
     * @param source DTO Object for conversion : {@link PlayerInfoDTO}
     * @return Converted DAO Object : {@link PlayerProfile}
     */
    @Override
    public PlayerProfile convert(PlayerInfoDTO source) {
        log.info("Converting DTO to PlayerProfile model.");

        PlayerProfile profile = new PlayerProfile();
        String playerId = (source.getPlayerId() == null || source.getPlayerId().isEmpty()) ?
                RandomPlayerIdGenerator.generateRandomId(source.getBasicInfo().getFirstName()) :
                source.getPlayerId();
        profile.setPlayerId(playerId);

        updateFromBasicInfo(source, profile);
        setMultiMediaContent(source, profile);

        profile.setRoles(source.getRoles());
        profile.setBowlingStyle(source.getBowlingStyle());
        profile.setBattingStyle(source.getBattingStyle());

        return profile;
    }

    private void updateFromBasicInfo(PlayerInfoDTO source, PlayerProfile profile) {
        PlayerBasicInfoDTO basicInfo = source.getBasicInfo();

        profile.setFirstName(basicInfo.getFirstName());
        profile.setLastName(basicInfo.getLastName());
        profile.setMiddleName(basicInfo.getMiddleName());

        profile.setCountry(basicInfo.getCountry());
        profile.setDob(basicInfo.getBirthDate());
        profile.setPlaceOfBirth(basicInfo.getBirthPlace());

        profile.setNickName(basicInfo.getNickName());
        profile.setGender(basicInfo.getGender());
    }

    /**
     * Method to set the Multi-media content from DTO to DAO object
     *
     * @param dto     - DTO Object i.e @{@link PlayerInfoDTO}
     * @param profile - DAO Object i.e {@link PlayerProfile}
     */
    private void setMultiMediaContent(PlayerInfoDTO dto, PlayerProfile profile) {
        MultiMediaDocument imageInfo = dto.getImageInfo();
        if (imageInfo != null) {
            MultiMediaDocument document = new MultiMediaDocument();
            document.setFileName(imageInfo.getFileName());
            document.setFileContent(imageInfo.getFileContent());
            document.setFileType(imageInfo.getFileType());
            profile.setImageInfo(document);
        }
    }


}
