package org.fourstack.reactivecricinfo.bowlinginfoservice.dao;

import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BowlingInfoDao extends ReactiveMongoRepository<BowlingInfo, String> {
}
