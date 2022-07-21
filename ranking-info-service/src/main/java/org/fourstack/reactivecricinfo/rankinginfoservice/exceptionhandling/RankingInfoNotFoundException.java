package org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling;

/**
 * Custom Exception class which is responsible when no ranking-info
 * not available for the requested data.
 *
 * @author manjunath
 */
public class RankingInfoNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 115037835128480213L;

    public RankingInfoNotFoundException() {
    }

    public RankingInfoNotFoundException(String message) {
        super(message);
    }

    public RankingInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RankingInfoNotFoundException(Throwable cause) {
        super(cause);
    }
}
