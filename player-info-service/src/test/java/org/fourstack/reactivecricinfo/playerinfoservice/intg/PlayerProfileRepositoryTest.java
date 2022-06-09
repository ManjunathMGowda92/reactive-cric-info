package org.fourstack.reactivecricinfo.playerinfoservice.intg;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
public class PlayerProfileRepositoryTest {

    @Autowired
    PlayerProfileRepository repository;

    @BeforeEach
    public void setUpData() {
        List<PlayerProfile> playerProfileList = EntityGenerator.getPlayerProfileList();
        repository.saveAll(playerProfileList).blockLast();
    }

    @AfterEach
    public void eraseData() {
        repository.deleteAll().block();
    }

    @Test
    public void testFindAll() {
        var daoObjFlux = repository.findAll();
        StepVerifier.create(daoObjFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void testFindById() {
        String id = "SAC2022-6T8-15L23-9NPZ-50234200";
        var playerDao = repository.findById(id);
        StepVerifier.create(playerDao)
                .assertNext(profile -> {
                    assert profile != null;
                    Assertions.assertEquals("Sachin", profile.getFirstName());
                    Assertions.assertEquals("Tendulkar", profile.getLastName());
                    Assertions.assertEquals("India", profile.getCountry());
                    Assertions.assertEquals(BowlingStyleType.RIGHT_ARM_LEGBREAK, profile.getBowlingStyle());
                });
    }

    @Test
    public void testFindPlayersByCountry() {
        String country = "India";
        var daoFlux = repository.findByCountry(country);
        StepVerifier.create(daoFlux)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    public void testFindByGender() {
        String gender = "MALE";
        var daoFlux = repository.findByGender(gender);
        StepVerifier.create(daoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void testFindByBattingStyle() {
        String battingStyle = "RIGHT_HANDED_BATSMAN";
        var daoFlux = repository.findByBattingStyle(battingStyle);
        StepVerifier.create(daoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void testFindByBowlingStyle() {
        String bowlingStyle = "LEFT_ARM_ORTHODOX";
        var daoFlux = repository.findByBowlingStyle(bowlingStyle);
        StepVerifier.create(daoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void testSave() {
        PlayerProfile playerProfile = EntityGenerator.getPlayerProfile();
        var daoPlayerProfile = repository.save(playerProfile);
        StepVerifier.create(daoPlayerProfile)
                .assertNext(profile -> {
                    assert profile != null;
                    Assertions.assertNotNull(profile.getPlayerId());
                });
    }
}
