package org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.*;

/**
 * Exception Handler class, which handles exceptions which occur in
 * ranking-info-service and then converts the exceptions to readable
 * message.
 *
 * @author manjunath
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception Handling method responsible to handle the {@link RankingInfoNotFoundException}
     * Exception will be converted into {@link ErrorResponse} object
     * with suitable error details.
     *
     * @param exception {@link RankingInfoNotFoundException} object.
     * @param request   WebRequest object.
     * @return Response Entity having the details of Error.
     */
    @ExceptionHandler(RankingInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRankingNotFoundException(
            RankingInfoNotFoundException exception, ServerHttpRequest request) {
        log.info("GlobalExceptionHandler: RankingInfoNotFoundException caught : {}", exception.getMessage());
        ErrorResponse response = createErrorResponse(
                exception.getMessage(),
                NOT_FOUND,
                request.getPath().value()
        );
        return ResponseEntity.status(NOT_FOUND)
                .body(response);
    }

    /**
     * Exception handling method responsible to handle the {@link RankingServiceException}.
     * Exception will be converted into {@link ErrorResponse} object
     * with suitable error details.
     *
     * @param exception {@link RankingServiceException} object.
     * @param request   WebRequest object.
     * @return Response Entity having the details of Error.
     */
    @ExceptionHandler(RankingServiceException.class)
    public ResponseEntity<ErrorResponse> handleRankingServiceException(
            RankingServiceException exception, ServerHttpRequest request) {
        log.info("GlobalExceptionHandler: RankingServiceException caught : {}", exception.getMessage());
        ErrorResponse response = createErrorResponse(
                exception.getMessage(),
                INTERNAL_SERVER_ERROR,
                request.getPath().value()
        );
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(response);
    }

    /**
     * Method which creates {@link ErrorResponse} object with the help
     * of error message, HttpStatus and url details.
     *
     * @param errorMsg Error Message value
     * @param status   HttpStatus
     * @param url      URL details
     * @return ErrorResponse object.
     */
    private ErrorResponse createErrorResponse(String errorMsg, HttpStatus status, String url) {
        return ErrorResponse.builder()
                .errorMsg(errorMsg)
                .errorCode(status.value())
                .status(status)
                .urlDetails(url)
                .timeStamp(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }
}
