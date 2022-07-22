package org.fourstack.reactivecricinfo.rankinginfoservice.intg;

import org.fourstack.reactivecricinfo.rankinginfoservice.dao.RankingInfoDao;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling.ErrorResponse;
import org.fourstack.reactivecricinfo.rankinginfoservice.testUtils.EntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

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
                    Assertions.assertEquals(404,response.getErrorCode());
                    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
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
                    Assertions.assertEquals(404, response.getErrorCode());
                    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                });
    }
}
