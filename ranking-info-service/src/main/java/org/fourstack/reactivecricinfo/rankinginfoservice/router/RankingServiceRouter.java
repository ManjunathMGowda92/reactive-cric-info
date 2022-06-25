package org.fourstack.reactivecricinfo.rankinginfoservice.router;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.handler.RankingServiceHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RankingServiceRouter {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/ranking-info/{id}",
                    operation = @Operation(
                            operationId = "fetchRankingInfoById",
                            method = "GET",
                            tags = "Ranking-service Router APIs",
                            summary = "API To fetch Ranking Info By Id.",
                            parameters = {
                                    @Parameter(
                                            name = "id", required = true,
                                            description = "ranking-info-id",
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved Ranking-Info",
                                            content = @Content(
                                                    schema = @Schema(implementation = IccRankDTO.class),
                                                    mediaType = "application/json"
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/ranking-info/by-player-id/{player-id}",
                    operation = @Operation(
                            operationId = "fetchRankingInfoByPlayerId",
                            method = "GET",
                            tags = "Ranking-service Router APIs",
                            summary = "API to fetch Ranking-Info by playerId.",
                            parameters = {
                                    @Parameter(
                                            name = "player-id", required = true,
                                            description = "player-id to fetch the ranking-info",
                                            in = ParameterIn.PATH
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully fetched the Ranking Info",
                                            content = @Content(
                                                    schema = @Schema(implementation = IccRankDTO.class),
                                                    mediaType = "application/json"
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/ranking-info",
                    operation = @Operation(
                            operationId = "createRankingInfo",
                            method = "POST",
                            tags = "Ranking-service Router APIs",
                            summary = "API to create RankingInfo",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Ranking DTO Object",
                                    content = @Content(
                                            schema = @Schema(implementation = IccRankDTO.class),
                                            mediaType = "application/json"
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Successful creation of ICC Ranking Info",
                                            content = @Content(
                                                    schema = @Schema(implementation = IccRankDTO.class),
                                                    mediaType = "application/json"
                                            )
                                    )
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> serviceRouterApis(RankingServiceHandler handler) {
        return RouterFunctions.route()
                .nest(
                        RequestPredicates.path("/api/v1/ranking-info"),
                        builder -> {
                            builder.GET("/{id}", handler::fetchRankingInfoById)
                                    .GET("/by-player-id/{player-id}", handler::fetchRankingInfoByPlayerId)
                                    .POST("", handler::createRankingInfo);
                        }
                ).build();

    }
}
