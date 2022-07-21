package org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling;

/**
 * Custom Exception class which is responsible to handle the service
 * and generic exceptions to ranking-info-service.
 * <br/>
 * If {@link RankingServiceException} is caught then the ErrorResponse
 * will have the HttpStatus of 5XX.
 *
 * @author manjunath
 */
public class RankingServiceException extends RuntimeException {

    public static final long serialVersionUID = -923117405160599165L;

    public RankingServiceException() {
    }

    public RankingServiceException(String message) {
        super(message);
    }

    public RankingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RankingServiceException(Throwable cause) {
        super(cause);
    }
}
