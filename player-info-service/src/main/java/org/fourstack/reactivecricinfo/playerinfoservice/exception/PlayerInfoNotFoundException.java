package org.fourstack.reactivecricinfo.playerinfoservice.exception;

public class PlayerInfoNotFoundException extends RuntimeException {
    public  static final long serialVersionUID = -3706635215166812372L;


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
