package org.fourstack.reactivecricinfo.bowlinginfoservice.unit;

import org.fourstack.reactivecricinfo.bowlinginfoservice.converters.BowlingDaoModelToDtoConverter;
import org.fourstack.reactivecricinfo.bowlinginfoservice.converters.BowlingDtoToModelConverter;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dao.BowlingInfoDao;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.exceptionhandling.ErrorResponse;
import org.fourstack.reactivecricinfo.bowlinginfoservice.exceptionhandling.GlobalExceptionHandler;
import org.fourstack.reactivecricinfo.bowlinginfoservice.handler.AppInfoServiceHandler;
import org.fourstack.reactivecricinfo.bowlinginfoservice.handler.BowlingServiceHandler;
import org.fourstack.reactivecricinfo.bowlinginfoservice.handler.BowlingServiceHandlerImpl;
import org.fourstack.reactivecricinfo.bowlinginfoservice.router.BowlingServiceRouter;
import org.fourstack.reactivecricinfo.bowlinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@WebFluxTest
@ContextConfiguration(classes = {BowlingServiceRouter.class, BowlingServiceHandler.class, BowlingServiceHandlerImpl.class,
        GlobalExceptionHandler.class, AppInfoServiceHandler.class, BowlingDaoModelToDtoConverter.class,
        BowlingDtoToModelConverter.class})
@AutoConfigureWebTestClient
public class BowlingServiceUnitTest {

    @MockBean
    private BowlingInfoDao dao;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("BowlingServiceHandlerTest: Fetch BowlingInfo By player id.")
    public void testFetchBowlingInfoByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";

        Mockito.when(dao.findByPlayerId(Mockito.anyString()))
                .thenReturn(Mono.just(EntityGenerator.bowlingInfoDAO()));

        webTestClient.get()
                .uri("/api/v1/bowling-info/by-player-id/{player-id}", playerId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BowlingInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(playerId, response.getPlayerId());
                    assertEquals(3, response.getBowlingStatistics().size());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: BowlingInfoNotFoundException Test for PlayerId")
    public void testFetchBowlingInfoByPlayerIdForNotFoundException() {
        Mockito.when(dao.findByPlayerId(Mockito.anyString()))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/bowling-info/by-player-id/{player-id}")
                        .build("test")
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var errorResponse = exchangeResult.getResponseBody();
                    assert errorResponse != null;
                    assertEquals(404, errorResponse.getErrorCode());
                    assertEquals(NOT_FOUND, errorResponse.getStatus());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: BowlingServiceException Test for PlayerId")
    public void testFetchBowlingInfoByPlayerIdServiceException() {
        Mockito.when(dao.findByPlayerId(Mockito.anyString()))
                .thenThrow(new RuntimeException("Accessing the DAO illegally"));

        var uri = UriComponentsBuilder.fromUriString("/api/v1/bowling-info/by-player-id/{player-id}")
                .build("test-player-id");
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var errorResponse = exchangeResult.getResponseBody();
                    assert errorResponse != null;
                    assertEquals(INTERNAL_SERVER_ERROR, errorResponse.getStatus());
                    assertEquals(INTERNAL_SERVER_ERROR.value(), errorResponse.getErrorCode());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: Fetch BowlingInfo by Id.")
    public void testFetchBowlingInfoById() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        Mockito.when(dao.findById(Mockito.anyString()))
                .thenReturn(Mono.just(EntityGenerator.bowlingInfoDAO()));

        webTestClient.get()
                .uri("/api/v1/bowling-info/{id}", "BOWL-ASGU6757-YU76-76875")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BowlingInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals(playerId, response.getPlayerId());
                    assertEquals(3, response.getBowlingStatistics().size());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: BowlingNoTFoundException for Id")
    public void testFetchBowlingInfoByIdNotFoundException() {
        Mockito.when(dao.findById(Mockito.anyString()))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/bowling-info/{id}")
                        .build("test-id")
                ).exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var errResponse = exchangeResult.getResponseBody();
                    assert errResponse != null;
                    assertEquals(404, errResponse.getErrorCode());
                    assertEquals(NOT_FOUND, errResponse.getStatus());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: BowlingServiceException Test for Id")
    public void testFetchBowlingInfoByIdServiceException() {
        Mockito.when(dao.findById(Mockito.anyString()))
                .thenThrow(new Error("Un Handled Error occurred"));

        var uri = UriComponentsBuilder.fromUriString("/api/v1/bowling-info/{id}")
                .buildAndExpand("test-id")
                .toUri();
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var errResponse = exchangeResult.getResponseBody();
                    assert errResponse != null;
                    assertEquals(INTERNAL_SERVER_ERROR.value(), errResponse.getErrorCode());
                    assertEquals(INTERNAL_SERVER_ERROR, errResponse.getStatus());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: Create Bowling Info")
    public void testCreateBowlingInfo() {
        Mockito.when(dao.save(Mockito.any()))
                .thenReturn(Mono.just(EntityGenerator.bowlingInfoDAO()));

        webTestClient.post()
                .uri("/api/v1/bowling-info")
                .bodyValue(EntityGenerator.bowlingInfoDTO())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(BowlingInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", response.getPlayerId());
                    assertEquals(3, response.getBowlingStatistics().size());
                });
    }

    @Test
    @DisplayName("BowlingServiceHandlerTest: BowlingServiceException Test")
    public void testCreateBowlingInfoException(){
        Mockito.when(dao.save(Mockito.any()))
                .thenThrow(new RuntimeException("Unable to create Entity"));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/bowling-info")
                        .build()
                ).bodyValue(EntityGenerator.bowlingInfoDTO())
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var errResp = exchangeResult.getResponseBody();
                    assert errResp != null;
                    assertEquals(500, errResp.getErrorCode());
                    assertEquals(INTERNAL_SERVER_ERROR, errResp.getStatus());
                });
    }
}
