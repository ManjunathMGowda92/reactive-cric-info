package org.fourstack.reactivecricinfo.playerinfoservice.intg;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.*;
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
    @DisplayName("PlayerProfileRepositoryTest: find all players")
    public void testFindAll() {
        var daoObjFlux = repository.findAll();
        StepVerifier.create(daoObjFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: find player by Id")
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
                }).verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: find players by country")
    public void testFindPlayersByCountry() {
        String country = "India";
        var daoFlux = repository.findByCountry(country);
        StepVerifier.create(daoFlux)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: find players by gender")
    public void testFindByGender() {
        String gender = "MALE";
        var daoFlux = repository.findByGender(gender);
        StepVerifier.create(daoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: find players by Batting Style")
    public void testFindByBattingStyle() {
        String battingStyle = "RIGHT_HANDED_BATSMAN";
        var daoFlux = repository.findByBattingStyle(battingStyle);
        StepVerifier.create(daoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: find players by Bowling Style")
    public void testFindByBowlingStyle() {
        String bowlingStyle = "LEFT_ARM_ORTHODOX";
        var daoFlux = repository.findByBowlingStyle(bowlingStyle);
        StepVerifier.create(daoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: Save PlayerProfile to DB")
    public void testSave() {
        PlayerProfile playerProfile = EntityGenerator.getPlayerProfile();
        var daoPlayerProfile = repository.save(playerProfile);
        StepVerifier.create(daoPlayerProfile)
                .assertNext(profile -> {
                    assert profile != null;
                    Assertions.assertNotNull(profile.getPlayerId());
                });
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: Find PlayerProfile by firstname")
    public void testFindByFirstName() {
        String firstname = "Sachin";
        var daoFlux = repository.findByFirstNameIgnoreCase(firstname);
        StepVerifier.create(daoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileRepositoryTest: Find PlayerProfile by lastname")
    public void testFindByLastName() {
        String lastname = "kohli";
        var daoFlux = repository.findByLastNameIgnoreCase(lastname);
        StepVerifier.create(daoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}
