package org.fourstack.reactivecricinfo.battinginfoservice.dao;

import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BattingInfoDao extends ReactiveMongoRepository<BattingInfo, String> {

    Mono<BattingInfo> findByPlayerId(String playerId);
}
