package org.fourstack.reactivecricinfo.playerinfoservice.model;

import lombok.Data;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.PlayerRoleType;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "player-profile")
public class PlayerProfile {

    @Id
    private String playerId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String country;
    private LocalDate dob;
    private String placeOfBirth;
    private String nickName;
    private GenderType gender;

    private MultiMediaDocument imageInfo;

    private List<PlayerRoleType> roles;
    private BattingStyleType battingStyle;
    private BowlingStyleType bowlingStyle;


    @CreatedDate
    private LocalDate createdOn;

    @LastModifiedDate
    private LocalDate updatedOn;
}
