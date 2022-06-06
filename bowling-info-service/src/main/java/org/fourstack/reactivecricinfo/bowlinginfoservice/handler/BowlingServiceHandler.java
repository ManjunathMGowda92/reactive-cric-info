package org.fourstack.reactivecricinfo.bowlinginfoservice.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BowlingServiceHandler {
    Mono<ServerResponse> fetchBowlingInfo(ServerRequest request);

    Mono<ServerResponse> createBowlingInfo(ServerRequest request);
}
