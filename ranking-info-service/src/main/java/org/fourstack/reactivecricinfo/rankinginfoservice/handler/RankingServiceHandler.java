package org.fourstack.reactivecricinfo.rankinginfoservice.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface RankingServiceHandler {

    Mono<ServerResponse> fetchRankingInfoById(ServerRequest request);

    Mono<ServerResponse> fetchRankingInfoByPlayerId(ServerRequest request);

    Mono<ServerResponse> createRankingInfo(ServerRequest request);
}
