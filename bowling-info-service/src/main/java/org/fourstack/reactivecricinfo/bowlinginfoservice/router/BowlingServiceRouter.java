package org.fourstack.reactivecricinfo.bowlinginfoservice.router;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.fourstack.reactivecricinfo.bowlinginfoservice.handler.AppInfoServiceHandler;
import org.fourstack.reactivecricinfo.bowlinginfoservice.handler.BowlingServiceHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RequestPredicates;
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
     * RouterFunction bean supplier method, where application info related routing operation is defined.
     * Also contains the information about the handler function for specific route function.
     * <p>
     * `@RouterOperation` is used to document each routing function for the Swagger API.
     * </p>
     *
     * @param handler HandlerClass which defines specific route function
     * @return RouterFunction object
     */
    @RouterOperation(path = "/api/v1/info", operation = @Operation(operationId = "fetchServiceInfo",
            tags = "Bowling-service Informational API", method = "GET",
            summary = "API to fetch the information about Bowling Service Application.")
    )
    @Bean
    public RouterFunction<ServerResponse> welcomeRouteApi(AppInfoServiceHandler handler) {
        return RouterFunctions.route()
                .GET("/api/v1/info", handler::getAppInfo)
                .build();
    }

    /**
     * RouterFunction bean supplier method, where application related routing functions were defined.
     * Also contains the information about the handler function for specific route function.
     * <p>
     * * `@RouterOperations` is used to document each routing function for the Swagger API.
     * * </p>
     *
     * @param handler Handler class which defines the specific route functions.
     * @return RouterFunction Object.
     */
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/bowling-statistics/{player-id}",
                    operation = @Operation(operationId = "fetchBowlingInfoByPlayerId",
                            method = "GET", tags = "Bowling-service Router APIs",
                            summary = "API to fetch the Bowling Info by Player Id.",

                            parameters = {
                                    @Parameter(name = "player-id", required = true,
                                            description = "player-id", in = ParameterIn.PATH)
                            })
            )
    })
    @Bean
    public RouterFunction<ServerResponse> serviceRouteApis(BowlingServiceHandler handler) {
        return RouterFunctions.route()
                .nest(RequestPredicates.path("/api/v1/bowling-statistics"), builder -> {
                    builder.GET("/{player-id}", handler::fetchBowlingInfo);
                }).build();
    }
}
