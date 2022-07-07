package org.fourstack.reactivecricinfo.playerinfoservice.dao;

import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerProfileRepository extends ReactiveMongoRepository<PlayerProfile, String> {

    Flux<PlayerProfile> findByCountry(String country);

    Flux<PlayerProfile> findByGender(String gender);

    Flux<PlayerProfile> findByBattingStyle(String battingStyle);

    Flux<PlayerProfile> findByBowlingStyle(String bowlingStyle);

    Flux<PlayerProfile> findByFirstNameIgnoreCase(String firstName);

    Flux<PlayerProfile> findByLastNameIgnoreCase(String lastName);
}
