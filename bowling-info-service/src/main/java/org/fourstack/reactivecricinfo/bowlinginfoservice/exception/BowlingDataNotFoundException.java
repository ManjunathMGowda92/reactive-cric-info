package org.fourstack.reactivecricinfo.bowlinginfoservice.exception;

public class BowlingDataNotFoundException extends RuntimeException {

    public BowlingDataNotFoundException() {
    }

    public BowlingDataNotFoundException(String message) {
        super(message);
    }

    public BowlingDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BowlingDataNotFoundException(Throwable cause) {
        super(cause);
    }
}
