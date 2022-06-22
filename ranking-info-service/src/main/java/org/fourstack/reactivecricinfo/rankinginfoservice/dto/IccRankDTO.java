package org.fourstack.reactivecricinfo.rankinginfoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IccRankDTO {
    private String rankId;
    private String playerId;
    private List<RankDTO> rankings;
}
