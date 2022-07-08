package org.fourstack.reactivecricinfo.apigateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fourstack.reactivecricinfo.apigateway.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.apigateway.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.apigateway.codetype.PlayerRoleType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
