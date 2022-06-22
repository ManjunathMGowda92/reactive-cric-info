package org.fourstack.reactivecricinfo.rankinginfoservice.dao;

import org.fourstack.reactivecricinfo.rankinginfoservice.model.IccRanking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RankingInfoDao extends ReactiveMongoRepository<IccRanking, String> {

    Mono<IccRanking> findByPlayerId(String playerId);
}
