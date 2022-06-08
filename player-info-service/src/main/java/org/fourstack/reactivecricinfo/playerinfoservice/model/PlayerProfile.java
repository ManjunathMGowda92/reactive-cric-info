package org.fourstack.reactivecricinfo.playerinfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.PlayerRoleType;
import org.fourstack.reactivecricinfo.playerinfoservice.model.common.MultiMediaDocument;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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


    /*
     * Below field for Auditing purpose.
     */
    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime updatedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    // version will conflict for updating same document using the unique ID.
    // It will be used in PUT and PATCH mapping, so that we need to render the
    // version form the DB and increment it each time.

    /*@Version
    private Integer version;*/
}
