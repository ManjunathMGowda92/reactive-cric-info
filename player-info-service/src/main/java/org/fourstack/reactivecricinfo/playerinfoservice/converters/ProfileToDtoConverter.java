package org.fourstack.reactivecricinfo.playerinfoservice.converters;

import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;

public class ProfileToDtoConverter {

    /**
     * Method which converts the {@link PlayerProfile} DAO Object
     * to {@link PlayerInfoDTO} Object.
     *
     * @param source DAO Object for Conversion : {@link PlayerProfile}
     * @return Converted DTO Object : {@link PlayerInfoDTO}
     */
    public static PlayerInfoDTO convert(PlayerProfile source) {

        PlayerInfoDTO target = new PlayerInfoDTO();
        target.setPlayerId(source.getPlayerId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setMiddleName(source.getMiddleName());

        target.setCountry(source.getCountry());
        target.setBirthDate(source.getDob());
        target.setBirthPlace(source.getPlaceOfBirth());

        target.setNickName(source.getNickName());
        target.setGender(source.getGender());

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
    private static void setMultiMediaContent(PlayerProfile source, PlayerInfoDTO target) {

        MultiMediaDocument imageInfo = source.getImageInfo();
        if (imageInfo != null) {
            MultiMediaDocument document = new MultiMediaDocument();
            document.setFileName(imageInfo.getFileName());
            document.setFileContent(imageInfo.getFileContent());
            document.setFileType(imageInfo.getFileType());
            target.setImageInfo(document);
        }
    }
}
