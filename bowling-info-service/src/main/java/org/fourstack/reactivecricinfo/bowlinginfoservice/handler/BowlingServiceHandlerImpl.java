package org.fourstack.reactivecricinfo.bowlinginfoservice.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class BowlingServiceHandlerImpl implements BowlingServiceHandler{
    @Override
    public Mono<ServerResponse> fetchBowlingInfo(ServerRequest request) {
        return null;
    }
}
