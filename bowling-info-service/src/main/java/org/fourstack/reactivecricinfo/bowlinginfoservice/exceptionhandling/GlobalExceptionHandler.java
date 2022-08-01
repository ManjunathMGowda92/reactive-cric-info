package org.fourstack.reactivecricinfo.bowlinginfoservice.exceptionhandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fourstack.reactivecricinfo.bowlinginfoservice.exception.BowlingDataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.valueOf;
import static org.fourstack.reactivecricinfo.bowlinginfoservice.constants.BowlingServiceConstants.*;
import static org.springframework.http.HttpStatus.*;

@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        Map<String, Object> attributes = new HashMap<>();
        var path = exchange.getRequest().getPath().value();
        attributes.put(URI_PATH, path);
        if (ex instanceof BowlingDataNotFoundException) {
            // Code to handle BowlingDataNotFoundException.
            attributes.put(ERROR_CODE, valueOf(NOT_FOUND.value()));
            attributes.put(STATUS, NOT_FOUND);
            attributes.put(ERROR_MSG, ex.getMessage());
        } else if (ex instanceof ConnectException) {
            // Code to handle ConnectException (when the target service is unavailable).
            attributes.put(ERROR_CODE, valueOf(SERVICE_UNAVAILABLE.value()));
            attributes.put(STATUS, SERVICE_UNAVAILABLE);
            attributes.put(ERROR_MSG, ex.getMessage());
        }else {
            // Code to handle unknown Exceptions.
            attributes.put(ERROR_CODE, valueOf(INTERNAL_SERVER_ERROR.value()));
            attributes.put(STATUS, INTERNAL_SERVER_ERROR);
            attributes.put(ERROR_MSG, "Internal Exception : "+ex.getMessage());
        }

        // Create ErrorResponse with the attributes Map.
        ErrorResponse errorResponse = createErrorResponseWithAttributes(attributes);

        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        // Create DataBuffer using the ErrorResponse.
        DataBuffer dataBuffer = dataBufferFactory.wrap(getObjectAsByteArray(errorResponse));

        // Set StatusCode and MediaType
        exchange.getResponse().setStatusCode(errorResponse.getStatus());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private ErrorResponse createErrorResponseWithAttributes(Map<String, Object> attributes) {
        Object errorCode = attributes.get(ERROR_CODE);
        Object errorMsg = attributes.get(ERROR_MSG);
        Object status = attributes.get(STATUS);
        Object uriPath = attributes.get(URI_PATH);
        ErrorResponse response = ErrorResponse.builder()
                .errorCode((errorCode != null) ? valueOf(errorCode.toString()) : 0)
                .errorMsg((errorMsg != null) ? errorMsg.toString() : null)
                .status((status != null) ? (HttpStatus) status : null)
                .urlDetails((uriPath != null) ? uriPath.toString() : null)
                .timeStamp(LocalDateTime.now(ZoneId.of("UTC")).toString())
                .build();

        return response;
    }

    private <T> byte[] getObjectAsByteArray(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
