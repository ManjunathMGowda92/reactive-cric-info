package org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Exception Handler class, which handles exceptions which occur in
 * ranking-info-service and then converts the exceptions to readable
 * message.
 *
 * @author manjunath
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

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

    private ErrorResponse createError(Throwable ex, String path) {
        ErrorResponse response;
        if (ex instanceof RankingInfoNotFoundException) {
            response = createErrorResponse(ex.getMessage(), NOT_FOUND, path);
        } else if (ex instanceof RankingServiceException) {
            response = createErrorResponse(ex.getMessage(), INTERNAL_SERVER_ERROR, path);
        } else {
            response = createErrorResponse("Internal Exception: " + ex.getMessage(), INTERNAL_SERVER_ERROR, path);
        }
        return response;
    }

    /**
     * Exception handling method for functional programming. Method catches
     * all the exceptions and errors, then based on handling code errors
     * will be converted into different API ErrorResponse.
     *
     * @param exchange ServerWebExchange object
     * @param ex       Throwable object instance
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        String path = exchange.getRequest().getPath().value();
        ErrorResponse response = createError(ex, path);
        log.info("Error Response : {}", response);

        // Create DataBuffer and add Error data to DataBuffer.
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        DataBuffer dataBuffer = dataBufferFactory.wrap(getObjectAsByteArray(response));

        // Set StatusCode and MediaType
        exchange.getResponse().setStatusCode(response.getStatus());
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private byte[] getObjectAsByteArray(ErrorResponse response) {
        try {
            return objectMapper.writeValueAsBytes(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
