package org.fourstack.reactivecricinfo.rankinginfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "icc_rankings")
public class IccRanking {
    @Id
    private String rankingId;
    private String playerId;
    private List<Rank> rankings;
}
