package org.fourstack.reactivecricinfo.bowlinginfoservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fourstack.reactivecricinfo.bowlinginfoservice.codetype.CricketFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsDTO {
    private CricketFormat format;
    private int matches;
    private int innings;
    private int balls;
    private int runs;
    private int maidens;
    private int wickets;
    private double average;
    private double economy;
    private double strikeRate;
    private String bestBowlingInInnings;
    private String bestBowlingInMatch;
    private int fourWicketHaul;
    private int fiveWicketHaul;
    private int tenWicketHaul;
}
