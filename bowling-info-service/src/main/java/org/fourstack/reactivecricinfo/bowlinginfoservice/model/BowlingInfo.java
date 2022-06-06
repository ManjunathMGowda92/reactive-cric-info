package org.fourstack.reactivecricinfo.bowlinginfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bowling-info-data")
public class BowlingInfo {

    @Id
    private String bowlingId;

    // TODO : Handle the unique columns in MongoDB
    private String playerId;

    // To handle all formats statistics
    private List<BowlingStatistics> statistics;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime updatedOn;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

}
