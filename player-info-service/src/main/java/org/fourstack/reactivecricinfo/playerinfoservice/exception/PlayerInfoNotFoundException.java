package org.fourstack.reactivecricinfo.playerinfoservice.exception;

public class PlayerInfoNotFoundException extends RuntimeException {

    public PlayerInfoNotFoundException() {
    }

    public PlayerInfoNotFoundException(String message) {
        super(message);
    }

    public PlayerInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerInfoNotFoundException(Throwable cause) {
        super(cause);
    }
}
