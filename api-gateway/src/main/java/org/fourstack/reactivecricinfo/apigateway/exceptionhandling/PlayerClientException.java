package org.fourstack.reactivecricinfo.apigateway.exceptionhandling;

import lombok.Getter;
import lombok.Setter;
import org.fourstack.reactivecricinfo.apigateway.dto.ErrorResponse;

@Getter
@Setter
public class PlayerClientException extends RuntimeException{
    private ErrorResponse errResp;

    public PlayerClientException(ErrorResponse errResp) {
        this.errResp = errResp;
    }

    public PlayerClientException(String message) {
        super(message);
    }

    public PlayerClientException(String message, ErrorResponse errResp) {
        super(message);
        this.errResp = errResp;
    }

    public PlayerClientException(String message, Throwable cause, ErrorResponse errResp) {
        super(message, cause);
        this.errResp = errResp;
    }

    public PlayerClientException(Throwable cause, ErrorResponse errResp) {
        super(cause);
        this.errResp = errResp;
    }
}
