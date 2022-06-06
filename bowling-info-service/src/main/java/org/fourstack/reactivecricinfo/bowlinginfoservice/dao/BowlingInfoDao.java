package org.fourstack.reactivecricinfo.bowlinginfoservice.dao;

import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BowlingInfoDao extends ReactiveMongoRepository<BowlingInfo, String> {
    Mono<BowlingInfo> findByPlayerId(String playerId);
}
