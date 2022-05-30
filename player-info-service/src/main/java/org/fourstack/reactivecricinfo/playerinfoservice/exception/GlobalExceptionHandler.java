package org.fourstack.reactivecricinfo.playerinfoservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlayerNotFoundException(
            PlayerInfoNotFoundException exception, ServerHttpRequest request) {
        log.error("PlayerInfoNotFoundException caught : {}", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorMsg(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .urlDetails(request.getPath().value())
                .timeStamp(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}
