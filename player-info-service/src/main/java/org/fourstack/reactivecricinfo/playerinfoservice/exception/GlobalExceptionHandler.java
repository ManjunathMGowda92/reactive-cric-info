package org.fourstack.reactivecricinfo.playerinfoservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(NOT_FOUND.value())
                .errorMsg(exception.getMessage())
                .status(NOT_FOUND)
                .urlDetails(request.getPath().value())
                .timeStamp(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

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

        ErrorResponse response = ErrorResponse.builder()
                .errorMsg(exception.getMessage())
                .errorCode(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR)
                .urlDetails(request.getPath().value())
                .timeStamp(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
