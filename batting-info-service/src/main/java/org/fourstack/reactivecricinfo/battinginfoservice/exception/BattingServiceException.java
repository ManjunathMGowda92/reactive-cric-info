package org.fourstack.reactivecricinfo.battinginfoservice.exception;

public class BattingServiceException extends RuntimeException {

    public static final long  serialVersionUID = -7471947746170133197L;

    public BattingServiceException() {
    }

    public BattingServiceException(String message) {
        super(message);
    }

    public BattingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BattingServiceException(Throwable cause) {
        super(cause);
    }
}
