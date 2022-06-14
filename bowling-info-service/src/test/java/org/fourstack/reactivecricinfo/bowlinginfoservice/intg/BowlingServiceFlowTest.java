package org.fourstack.reactivecricinfo.bowlinginfoservice.intg;

import org.fourstack.reactivecricinfo.bowlinginfoservice.dao.BowlingInfoDao;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class BowlingServiceFlowTest {

    @Autowired
    private BowlingInfoDao dao;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void setUpData() {
        dao.save(EntityGenerator.bowlingInfoDAO())
                .block();
    }

    @AfterEach
    public void eraseData() {
        dao.deleteAll().block();
    }

    @Test
    @DisplayName("BowlingServiceFlowTest: Get BowlingInfo by PlayerId")
    public void testGetBowlingInfoByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        webClient.get()
                .uri("/api/v1/bowling-info/by-player-id/{player-id}", playerId)
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
    @DisplayName("BowlingServiceFlowTest: Get BowlingInfo by Id")
    public void testGetBowlingInfoById() {
        String id = "BOWLSAC2022-6T8-15L23-9NPZ-50234200";
        webClient.get()
                .uri("/api/v1/bowling-info/{id}", id)
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
    @DisplayName("BowlingServiceFlowTest: Create Bowling Info")
    public void testCreateBowlingInfo() {
        var dto = EntityGenerator.bowlingInfoDTO();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/bowling-info")
                        .build()
                ).bodyValue(dto)
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
}
