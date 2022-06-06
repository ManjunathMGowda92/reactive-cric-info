package org.fourstack.reactivecricinfo.bowlinginfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BowlingStatistics {
    // should be unique for all formats and per player
    private String id;
    private String format;
    private int matches;
    private int innings;
    private int balls;
    private int runs;
    private int maidens;
    private int wickets;
    private double average;
    private double economy;
    private double strikeRate;
    // To hold the highest wickets taken in an innings (7/17).
    private String bestBowlingInInnings;
    // To hold the highest wickets taken in a match.
    private String bestBowlingInMatch;
    private String fourWicketHaul;
    private String fiveWicketHaul;
    private String tenWicketHaul;
}
