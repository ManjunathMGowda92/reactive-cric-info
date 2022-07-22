package org.fourstack.reactivecricinfo.rankinginfoservice.intg;

import org.fourstack.reactivecricinfo.rankinginfoservice.dao.RankingInfoDao;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling.ErrorResponse;
import org.fourstack.reactivecricinfo.rankinginfoservice.testUtils.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
@AutoConfigureWebTestClient
public class RankingInfoFlowTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RankingInfoDao dao;

    @BeforeEach
    public void doInitialization() {
        dao.save(EntityGenerator.iccRanking())
                .block();
    }

    @AfterEach
    public void eraseData() {
        dao.deleteAll().block();
    }

    @Test
    @DisplayName("RankingInfoFlowTest: RankingInfoNotFoundException - RankingId.")
    public void testFetchRankingByIdNotFound() {
        String path = "/api/v1/ranking-info/{id}";
        String rankingId = "RKSAC2022";
        webTestClient.get()
                .uri(path, rankingId)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(404, response.getErrorCode());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                });

    }

    @Test
    @DisplayName("RankingInfoFlowTest: Fetch RankingInfo By rankingId")
    public void testFetchRankingInfoById() {
        String rankingId = "RKSAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/{id}";
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        webTestClient.get()
                .uri(path, rankingId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(IccRankDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(playerId, response.getPlayerId());
                    assertEquals(3, response.getRankings().size());
                });
    }

    @Test
    @DisplayName("RankingInfoFlowTest: Fetch RankingInfo by player id.")
    public void testFetchRankingByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/by-player-id/{player-id}";
        String rankingId = "RKSAC2022-6T8-15L23-9NPZ-50234200";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .build(playerId)
                ).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(IccRankDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(rankingId, response.getRankId());
                    assertEquals(3, response.getRankings().size());
                });
    }

    @Test
    @DisplayName("RankingInfoFlowTest: RankingInfoNotFoundException - playerId")
    public void testFetchRankingByPlayerIdNotFound() {
        String path = "/api/v1/ranking-info/by-player-id/{player-id}";
        String playerId = "TEST-PLAYER";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .build(playerId)
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(404, response.getErrorCode());
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                });
    }

    @Test
    @DisplayName("RankingInfoFlowTest: Create RankingInfo.")
    public void testCreateRankingInfo() {
        String path = "/api/v1/ranking-info";
        IccRankDTO dto = EntityGenerator.iccRankDTO();
        dto.setRankId(null);
        String playerId = "SAC2022-6T8-15L23-9NPZ";
        dto.setPlayerId(playerId);

        webTestClient.post()
                .uri(path)
                .bodyValue(dto)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(IccRankDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(playerId, response.getPlayerId());
                    assertEquals("RK".concat(playerId), response.getRankId());
                    assertEquals(3, response.getRankings().size());
                });
    }
}
