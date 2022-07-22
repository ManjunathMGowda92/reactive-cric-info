package org.fourstack.reactivecricinfo.rankinginfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fourstack.reactivecricinfo.rankinginfoservice.codetype.Format;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank {
    private Format format;
    private int current;
    private int best;
}
