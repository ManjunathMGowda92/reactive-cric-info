package org.fourstack.reactivecricinfo.bowlinginfoservice.exception;

public class BowlingServiceException extends RuntimeException {
    public BowlingServiceException(String message, Throwable cause) {
    }

    public BowlingServiceException(Throwable cause) {
        super(cause);
    }

    public BowlingServiceException() {
    }

    public BowlingServiceException(String message) {
        super(message);
    }
}
