package org.fourstack.reactivecricinfo.playerinfoservice.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String errorMsg;
    private int errorCode;
    private HttpStatus status;
    private String urlDetails;
    private LocalDateTime timeStamp;
}
