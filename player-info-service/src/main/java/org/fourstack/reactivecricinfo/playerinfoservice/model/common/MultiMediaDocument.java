package org.fourstack.reactivecricinfo.playerinfoservice.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiMediaDocument {
    private String fileName;
    private byte[] fileContent;
    private String fileType;
}
