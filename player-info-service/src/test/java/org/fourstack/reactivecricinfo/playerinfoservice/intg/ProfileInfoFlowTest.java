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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @DisplayName("ProfileInfoFlowTest: Get Player by playerId.")
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
    @DisplayName("ProfileInfoFlowTest: getProfileByPlayerId[404_NOT_FOUND]")
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
    @DisplayName("ProfileInfoFlowTest: Get Player by Country")
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
    @DisplayName("ProfileInfoFlowTest: getPlayerProfilesByCountry[404_NOT_FOUND]")
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
    @DisplayName("ProfileInfoFlowTest: Get Players by Gender")
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
    @DisplayName("ProfileInfoFlowTest: getPlayersByGender[404_NOT_FOUND]")
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
    @DisplayName("ProfileInfoFlowTest: Get Players by BowlingStyle.")
    public void testGetPlayersByBowlingStyle() {
        String bowlingStyle = "LEFT_ARM_ORTHODOX";
        webTestClient.get()
                .uri("/api/v1/player/bowling-style/{style}", bowlingStyle)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("ProfileInfoFlowTest: getPlayersByBowlingStyle[404_NOT_FOUND]")
    public void testGetPlayersByBowlingStyleNotFound() {
        String bowlingStyle = "ARM_ORTHODOX";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/player/bowling-style/{style}")
                        .build(bowlingStyle)
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("No Player found for the Bowling Style :ARM_ORTHODOX", response.getErrorMsg());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                    assertEquals(404, response.getErrorCode());
                });
    }

    @Test
    @DisplayName("ProfileInfoFlowTest: Get Players by Batting Style")
    public void testGetPlayersByBattingStyle() {
        String battingStyle = "RIGHT_HANDED_BATSMAN";
        webTestClient.get()
                .uri("/api/v1/player/batting-style/{style}", battingStyle)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(3);
    }

    @Test
    @DisplayName("ProfileInfoFlowTest: getPlayersByBattingStyle[404_NOT_FOUND]")
    public void testGetPlayersByBattingStyleNotFound() {
        String battingStyle = "NO_HANDED_BATSMAN";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/player/batting-style/{style}")
                        .build(battingStyle)
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(result -> {
                    var response = result.getResponseBody();
                    assert response != null;
                    assertEquals("No Player found for the Batting Style :NO_HANDED_BATSMAN", response.getErrorMsg());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                    assertEquals(404, response.getErrorCode());
                });
    }

    @Test
    @DisplayName("ProfileInfoFlowTest: Create Player Profile")
    public void testCreatePlayerProfile() {
        PlayerInfoDTO playerInfoDTO = EntityGenerator.getPlayerInfoDTO();
        playerInfoDTO.setPlayerId(null);

        webTestClient.post()
                .uri("/api/v1/player")
                .bodyValue(playerInfoDTO)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PlayerInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;

                    assertNotNull(response.getPlayerId());
                });
    }
}
