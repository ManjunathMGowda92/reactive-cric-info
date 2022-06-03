package org.fourstack.reactivecricinfo.bowlinginfoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {

    private String appName;
    private String appVersion;
    private String appDescription;

    private List<EndPointsDesc> endpoints;
}
