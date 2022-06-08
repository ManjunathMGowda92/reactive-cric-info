package org.fourstack.reactivecricinfo.bowlinginfoservice.exception;

public class BowlingDataNotFoundException extends RuntimeException {
    public  static final long serialVersionUID = -2580136155402792152L;

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
