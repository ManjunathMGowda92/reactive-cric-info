package org.fourstack.reactivecricinfo.apigateway.exceptionhandling;

import lombok.Getter;
import lombok.Setter;
import org.fourstack.reactivecricinfo.apigateway.dto.ErrorResponse;

@Getter
@Setter
public class PlayerServiceException extends RuntimeException{
    private ErrorResponse response;

    public PlayerServiceException(ErrorResponse response) {
        this.response = response;
    }

    public PlayerServiceException(String message, ErrorResponse response) {
        super(message);
        this.response = response;
    }

    public PlayerServiceException(String message, Throwable cause, ErrorResponse response) {
        super(message, cause);
        this.response = response;
    }

    public PlayerServiceException(Throwable cause, ErrorResponse response) {
        super(cause);
        this.response = response;
    }
}
