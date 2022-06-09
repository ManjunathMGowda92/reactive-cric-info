package org.fourstack.reactivecricinfo.playerinfoservice.intg;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class ProfileInfoFlowTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    PlayerProfileRepository repository;

    @BeforeEach
    public void setUpData() {
        List<PlayerProfile> playerProfileList = EntityGenerator.getPlayerProfileList();
        repository.saveAll(playerProfileList)
                .blockLast();
    }

    @AfterEach
    public void eraseData() {
        repository.deleteAll().block();
    }

    @Test
    public void testGetProfileByPlayerId() {
        String playerId = "VIR2022-6Y8-5P14-48SA-257223300";
        webTestClient.get()
                .uri("/api/v1/player/{id}", playerId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PlayerInfoDTO.class)
                .consumeWith(result -> {
                    var dtoObj = result.getResponseBody();
                    assert dtoObj != null;
                    Assertions.assertEquals("Virat", dtoObj.getBasicInfo().getFirstName());
                    Assertions.assertEquals(BattingStyleType.RIGHT_HANDED_BATSMAN, dtoObj.getBattingStyle());
                    Assertions.assertEquals(BowlingStyleType.RIGHT_ARM_MEDIUM, dtoObj.getBowlingStyle());
                    Assertions.assertEquals("India", dtoObj.getBasicInfo().getCountry());
                });
    }

    @Test
    public void testGetPlayerProfilesByCountry() {
        String country = "India";
        webTestClient.get()
                .uri("/api/v1/player/by-country-name/{country-name}", country)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(3)
                .consumeWith(exchangeResult -> {
                    var list = exchangeResult.getResponseBody();
                    assert list != null;
                    Assertions.assertEquals(3, list.size());
                });
    }

    @Test
    public void testGetPlayersByGender() {
        String gender = "MALE";
        webTestClient.get()
                .uri("/api/v1/player/by-gender/{gender}", gender)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(4)
                .consumeWith(exchangeResult -> {
                    var dtoList = exchangeResult.getResponseBody();
                    assert dtoList != null;
                    Assertions.assertEquals(4, dtoList.size());
                });
    }

}
