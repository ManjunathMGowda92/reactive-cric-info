package org.fourstack.reactivecricinfo.apigateway.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.apigateway.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception Handling method which is responsible for handling
     * {@link PlayerClientException}.
     *
     * @param exception {@link PlayerClientException} Object.
     * @param request   ServerRequest Object.
     * @return ResponseEntity with ErrorResponse Object.
     */
    @ExceptionHandler(PlayerClientException.class)
    public ResponseEntity<ErrorResponse> handlePlayerClientException(
            PlayerClientException exception, ServerHttpRequest request) {
        log.info("GlobalExceptionHandler: Start handlePlayerClientException() method");
        var errResponse = exception.getErrResp();
        if (errResponse != null) {
            errResponse.setUrlDetails(request.getPath().value());
            return ResponseEntity.status(errResponse.getStatus() != null ? errResponse.getStatus() : NOT_FOUND)
                    .body(errResponse);
        }
        errResponse = createErrorResponse(
                exception.getMessage(),
                NOT_FOUND,
                request.getPath().value()
        );

        return ResponseEntity.status(NOT_FOUND)
                .body(errResponse);
    }

    /**
     * Method to create {@link ErrorResponse} using error message, error code,
     * HttpStatus and url details.
     *
     * @param errorMsg  Error message for ErrorResponse.
     * @param errorCode Error Code value for ErrorResponse.
     * @param status    HttpStatus for ErrorResponse.
     * @param url       URL details.
     * @return ErrorResponse object.
     */
    private ErrorResponse createErrorResponse(String errorMsg, HttpStatus status, String url) {
        return ErrorResponse.builder()
                .errorCode(status.value())
                .errorMsg(errorMsg)
                .status(status)
                .urlDetails(url)
                .timeStamp(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    /**
     * Exception handling method responsible for handling
     * {@link PlayerServiceException}.
     *
     * @param exception {@link PlayerServiceException} Object.
     * @param request   ServerRequest Object.
     * @return ResponseEntity with ErrorResponse Object.
     */
    @ExceptionHandler(PlayerServiceException.class)
    public ResponseEntity<ErrorResponse> handlePlayerServiceException(
            PlayerServiceException exception, ServerHttpRequest request) {
        log.info("GlobalExceptionHandler: Start handlePlayerServiceException() method");
        var errResponse = exception.getResponse();
        if (errResponse != null) {
            errResponse.setUrlDetails(request.getPath().value());
            return ResponseEntity.status(errResponse.getStatus() != null ? errResponse.getStatus() : INTERNAL_SERVER_ERROR)
                    .body(errResponse);
        }

        errResponse = createErrorResponse(
                exception.getMessage(),
                INTERNAL_SERVER_ERROR,
                request.getPath().value()
        );
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(errResponse);
    }

    /**
     * Method to handle the {@link ConnectException}, which occurs when
     * external service is not up and running.
     *
     * @param exception {@link ConnectException} Object.
     * @param request   ServerRequest object.
     * @return ResponseEntity with Error Response object.
     */
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> handleConnectionException(
            ConnectException exception, ServerHttpRequest request) {
        log.info("GlobalExceptionHandler: Start of handleConnectionException() method");
        ErrorResponse response = createErrorResponse(
                exception.getMessage(),
                INTERNAL_SERVER_ERROR,
                request.getPath().value()
        );

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
