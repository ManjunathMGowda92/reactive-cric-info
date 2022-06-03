package org.fourstack.reactivecricinfo.bowlinginfoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fourstack.reactivecricinfo.bowlinginfoservice.codetype.MethodType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndPointsDesc {
    private MethodType methodType;
    private String endpoint;
    private String summary;
}
