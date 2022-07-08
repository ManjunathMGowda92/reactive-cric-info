package org.fourstack.reactivecricinfo.playerinfoservice.dao;

import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerProfileRepository extends ReactiveMongoRepository<PlayerProfile, String> {

    /**
     * Database query method to find Players by Country.
     *
     * @param country Country name.
     * @return Flux of PlayerProfile objects.
     */
    Flux<PlayerProfile> findByCountry(String country);

    /**
     * Database query method to find the Players by Gender.
     *
     * @param gender Gender value.
     * @return Flux of PlayerProfile objects.
     */
    Flux<PlayerProfile> findByGender(String gender);

    /**
     * Database query method to find the Players by Batting Style.
     *
     * @param battingStyle BattingStyle value
     * @return Flux of PlayerProfile objects.
     */
    Flux<PlayerProfile> findByBattingStyle(String battingStyle);

    /**
     * Database query method to fine the Players by Bowling Style.
     *
     * @param bowlingStyle BowlingStyle value.
     * @return Flux of PlayerProfile objects.
     */
    Flux<PlayerProfile> findByBowlingStyle(String bowlingStyle);

    /**
     * Database query method to find the Players by First name.
     *
     * @param firstName FirstName value.
     * @return Flux of PlayerProfile objects.
     */
    Flux<PlayerProfile> findByFirstNameIgnoreCase(String firstName);

    /**
     * Database query method to find the Players by Last name.
     *
     * @param lastName LastName value.
     * @return Flux of PlayerProfile objects.
     */
    Flux<PlayerProfile> findByLastNameIgnoreCase(String lastName);
}
