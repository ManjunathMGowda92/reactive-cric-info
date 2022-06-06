package org.fourstack.reactivecricinfo.battinginfoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BattingStatistics {
    private String id;
    private String format;
    private int matches;
    private int innings;
    private int runs;
    private int balls;
    private int highest;
    private double average;
    private double strikeRate;
    private int numberOfNotOuts;
    private int fours;
    private int sixes;
    private int ducks;
    private int noOfHalfCenturies;
    private int noOfCenturies;
    private int noOfDoubleCenturies;
    private int noOfTripleCenturies;
    private int noOfQuadrupleCenturies;
}
