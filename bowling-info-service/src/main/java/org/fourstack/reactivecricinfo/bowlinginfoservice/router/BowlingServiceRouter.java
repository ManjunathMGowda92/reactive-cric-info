package org.fourstack.reactivecricinfo.bowlinginfoservice.router;

import io.swagger.v3.oas.annotations.Operation;
import org.fourstack.reactivecricinfo.bowlinginfoservice.handler.BowlingServiceHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * BowlingServiceRouter is a Router class, which functions as Controller class.
 * Method routeApis() will create a RouterFunction beans, which holds the
 * information of all routing functions.
 */
@Configuration
public class BowlingServiceRouter {

    /**
     * RouterFunction bean supplier method, where all the routing operations were defined.
     * Also contains the information about the handler function for specific route function.
     * <p>
     * `@RouterOperations` is used to document each routing function for the Swagger API.
     * </p>
     *
     * @param handler
     * @return
     */
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/welcome",
                    operation = @Operation(operationId = "welcome",
                            tags = "Bowling-info Router APIs", method = "GET",
                            summary = "API to fetch Welcome message")
            )
    })
    @Bean
    public RouterFunction<ServerResponse> routeApis(BowlingServiceHandler handler) {
        return RouterFunctions.route()
                .GET(
                        "/api/v1/welcome",
                        request -> ServerResponse.status(HttpStatus.OK).bodyValue("Welcome to Reactive Bowling Service")
                ).build();


    }
}
