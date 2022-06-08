package org.fourstack.reactivecricinfo.battinginfoservice.exception;

public class BattingInfoNotFoundException extends RuntimeException {
    public  static final long serialVersionUID = 1811616344798716407L;


    public BattingInfoNotFoundException() {
    }

    public BattingInfoNotFoundException(String message) {
        super(message);
    }

    public BattingInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BattingInfoNotFoundException(Throwable cause) {
        super(cause);
    }
}
