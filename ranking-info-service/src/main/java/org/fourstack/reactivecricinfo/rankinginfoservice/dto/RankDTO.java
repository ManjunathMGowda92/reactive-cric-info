package org.fourstack.reactivecricinfo.rankinginfoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankDTO {
    private String format;
    private int currentRank;
    private int bestRank;
}
