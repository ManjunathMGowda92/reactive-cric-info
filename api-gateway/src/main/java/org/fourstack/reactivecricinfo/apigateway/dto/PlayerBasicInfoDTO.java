package org.fourstack.reactivecricinfo.apigateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fourstack.reactivecricinfo.apigateway.codetype.GenderType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerBasicInfoDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String country;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String birthPlace;
    private String nickName;
    private GenderType gender;
}
