package org.fourstack.reactivecricinfo.playerinfoservice.exception;

public class PlayerServiceException extends RuntimeException {

    public PlayerServiceException() {
    }

    public PlayerServiceException(String message) {
        super(message);
    }

    public PlayerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerServiceException(Throwable cause) {
        super(cause);
    }
}
