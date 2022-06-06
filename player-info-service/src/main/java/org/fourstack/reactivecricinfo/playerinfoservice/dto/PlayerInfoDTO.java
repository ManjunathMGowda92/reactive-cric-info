package org.fourstack.reactivecricinfo.playerinfoservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.PlayerRoleType;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerInfoDTO {

    private String playerId;
    private PlayerBasicInfoDTO basicInfo;
    private MultiMediaDocument imageInfo;
    private List<PlayerRoleType> roles;
    private BattingStyleType battingStyle;
    private BowlingStyleType bowlingStyle;
}
