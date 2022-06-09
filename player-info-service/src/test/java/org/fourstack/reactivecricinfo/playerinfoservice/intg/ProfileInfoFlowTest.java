package org.fourstack.reactivecricinfo.playerinfoservice.intg;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.model.ErrorResponse;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                    assertEquals("Virat", dtoObj.getBasicInfo().getFirstName());
                    assertEquals(BattingStyleType.RIGHT_HANDED_BATSMAN, dtoObj.getBattingStyle());
                    assertEquals(BowlingStyleType.RIGHT_ARM_MEDIUM, dtoObj.getBowlingStyle());
                    assertEquals("India", dtoObj.getBasicInfo().getCountry());
                });
    }

    @Test
    public void testGetProfileByPlayerIdNotFound() {
        String playerId = "1213-123-UYT-12";
        webTestClient.get()
                .uri("/api/v1/player/{id}", playerId)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("No Player Details found for the playerId: 1213-123-UYT-12", response.getErrorMsg());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                    assertEquals(404, response.getErrorCode());
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
                    assertEquals(3, list.size());
                });
    }

    @Test
    public void testGetPlayerProfilesByCountryNotFound() {
        String country = "North Atlantic";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/player/by-country-name/{country-name}")
                        .build(country)
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("No Player found for the Country :North Atlantic", response.getErrorMsg());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                    assertEquals(404, response.getErrorCode());
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
                    assertEquals(4, dtoList.size());
                });
    }

    @Test
    public void testGetPlayersByGenderNotFound() {
        String gender = "ABCD";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/player/by-gender/{gender}")
                        .build(gender)
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("No Player found for the Gender :ABCD", response.getErrorMsg());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                    assertEquals(404, response.getErrorCode());
                });
    }

    @Test
    public void testGetPlayersByBowlingStyle() {
        String bowlingStyle = "LEFT_ARM_ORTHODOX";
        webTestClient.get()
                .uri("/api/v1/player/bowling-style/{style}", bowlingStyle)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(1);
    }

}
