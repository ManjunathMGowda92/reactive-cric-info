package org.fourstack.reactivecricinfo.playerinfoservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handling method to handle the {@link PlayerInfoNotFoundException}.
     * Exception will be converted to {@link ErrorResponse} object with suitable
     * error message and other details.
     *
     * @param exception {@link PlayerInfoNotFoundException} object
     * @param request   HttpRequest object.
     * @return ErrorResponse object.
     */
    @ExceptionHandler(PlayerInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlayerNotFoundException(
            PlayerInfoNotFoundException exception, ServerHttpRequest request) {
        log.error("PlayerInfoNotFoundException caught : {}", exception.getMessage());

        ErrorResponse response = createErrorResponse(
                exception.getMessage(),
                NOT_FOUND,
                request.getPath().value()
        );

        return ResponseEntity.status(NOT_FOUND)
                .body(response);
    }

    /**
     * Exception handling method to handle {@link PlayerServiceException}.
     * Exception will be converted to {@link ErrorResponse} object with suitable
     * error message and other details.
     *
     * @param exception {@link PlayerServiceException} object.
     * @param request   HttpRequest Object.
     * @return ErrorResponse object.
     */
    @ExceptionHandler(PlayerServiceException.class)
    public ResponseEntity<ErrorResponse> handlePlayerServiceException(
            PlayerServiceException exception, ServerHttpRequest request) {
        log.error("PlayerServiceException caught : {}", exception.getMessage());

        ErrorResponse response = createErrorResponse(
                exception.getMessage(),
                INTERNAL_SERVER_ERROR,
                request.getPath().value()
        );
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(response);
    }

    /**
     * Exception handling method to handle {@link ConnectException}.
     * Exception will be converted to {@link ErrorResponse} object with suitable
     * error message and other details.
     *
     * @param exception {@link ConnectException} object
     * @param request   HttpRequest object.
     * @return ErrorResponse object.
     */
    public ResponseEntity<ErrorResponse> handleConnectException(
            ConnectException exception, ServerHttpRequest request) {
        log.error("ConnectException caught : {}", exception.getMessage());

        ErrorResponse response = createErrorResponse(
                exception.getMessage(),
                SERVICE_UNAVAILABLE,
                request.getPath().value()
        );
        return ResponseEntity.status(SERVICE_UNAVAILABLE)
                .body(response);
    }

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
