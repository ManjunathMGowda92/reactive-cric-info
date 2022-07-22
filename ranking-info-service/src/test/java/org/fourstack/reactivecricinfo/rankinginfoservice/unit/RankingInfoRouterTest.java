package org.fourstack.reactivecricinfo.rankinginfoservice.unit;

import org.fourstack.reactivecricinfo.rankinginfoservice.converters.RankDaoToDtoConverter;
import org.fourstack.reactivecricinfo.rankinginfoservice.dao.RankingInfoDao;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling.ErrorResponse;
import org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling.GlobalExceptionHandler;
import org.fourstack.reactivecricinfo.rankinginfoservice.handler.RankingServiceHandler;
import org.fourstack.reactivecricinfo.rankinginfoservice.handler.RankingServiceHandlerImpl;
import org.fourstack.reactivecricinfo.rankinginfoservice.router.RankingServiceRouter;
import org.fourstack.reactivecricinfo.rankinginfoservice.testUtils.EntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@WebFluxTest
@ContextConfiguration(
        classes = {
                RankingServiceRouter.class,
                RankingServiceHandler.class,
                RankingServiceHandlerImpl.class,
                GlobalExceptionHandler.class,
                RankDaoToDtoConverter.class,
                RankDaoToDtoConverter.class
        }
)
@AutoConfigureWebTestClient
public class RankingInfoRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RankingInfoDao dao;

    @Test
    @DisplayName("RankingInfoRouterTest: Find RankingInfo by Id.")
    public void testFetchRankingInfoById() {
        String rankingId = "RKSAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/{id}";

        Mockito.when(dao.findById(Mockito.anyString()))
                .thenReturn(Mono.just(EntityGenerator.iccRanking()));

        webTestClient.get()
                .uri(path, rankingId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(IccRankDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(3, response.getRankings().size());
                });
    }

    @Test
    @DisplayName("RankingInfoRouterTest: RankingInfoNotFoundException for fetchRankingInfoById")
    public void testFetchRankingInfoNotFoundException() {
        String rankingId = "RKSAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/{id}";

        Mockito.when(dao.findById(Mockito.anyString()))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .build(rankingId)
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(404, response.getErrorCode());
                    Assertions.assertEquals(NOT_FOUND, response.getStatus());
                });
    }

    @Test
    @DisplayName("RankingInfoRouterTest: ServiceException for fetchRankingInfoById")
    public void testFetchRankingInfoServiceException() {
        String rankingId = "RKSAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/{id}";

        Mockito.when(dao.findById(Mockito.anyString()))
                        .thenThrow(new RuntimeException("Service Exception"));

        webTestClient.get()
                .uri(path, rankingId)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(500, response.getErrorCode());
                    Assertions.assertEquals(INTERNAL_SERVER_ERROR, response.getStatus());
                });
    }

    @Test
    @DisplayName("RankingInfoRouterTest: Find RankingInfo by player id.")
    public void testFetchRankingByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/by-player-id/{player-id}";

        Mockito.when(dao.findByPlayerId(Mockito.anyString()))
                .thenReturn(Mono.just(EntityGenerator.iccRanking()));

        webTestClient.get()
                .uri(path, playerId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(IccRankDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(3, response.getRankings().size());
                    Assertions.assertNotNull(response.getRankId());
                });
    }

    @Test
    @DisplayName("RankingInfoRouterTest: RankingInfoNotFoundException for fetchRankingInfoByPlayerId.")
    public void testFetchRankingByPlayerIdNotFound() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/by-player-id/{player-id}";

        Mockito.when(dao.findByPlayerId(Mockito.anyString()))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri(path, playerId)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(404, response.getErrorCode());
                    Assertions.assertEquals(NOT_FOUND, response.getStatus());
                });
    }

    @Test
    @DisplayName("RankingInfoRouterTest: RankingServiceException for fetchRankingInfoByPlayerId.")
    public void testFetchRankingByPlayerIdServiceException() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/ranking-info/by-player-id/{player-id}";

        Mockito.when(dao.findByPlayerId(Mockito.anyString()))
                        .thenThrow(new RuntimeException("Service Exception"));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .build(playerId)
                ).exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(500, response.getErrorCode());
                    Assertions.assertEquals(INTERNAL_SERVER_ERROR, response.getStatus());
                });
    }
}
