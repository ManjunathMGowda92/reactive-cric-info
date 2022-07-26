package org.fourstack.reactivecricinfo.battinginfoservice.intg;

import org.fourstack.reactivecricinfo.battinginfoservice.dao.BattingInfoDao;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.exception.model.ErrorResponse;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;
import org.fourstack.reactivecricinfo.battinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class BattingInfoFlowTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BattingInfoDao dao;

    @BeforeEach
    public void setUpData() {
        BattingInfo entity = EntityGenerator.battingInfoDAO();
        dao.save(entity).block();
    }

    @AfterEach
    public void eraseData() {
        dao.deleteAll()
                .block();
    }

    @Test
    @DisplayName("BattingInfoFlowTest: Find BattingInfo by Id.")
    public void testGetBattingInfoById() {
        String battingId = "BAT-SAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/batting-info/{id}";
        webTestClient.get()
                .uri(path, battingId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(BattingInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;

                });
    }

    @Test
    @DisplayName("BattingInfoFlowTest: BattingInfoNotFoundException for Id.")
    public void testGetBattingInfoByIdNotFoundException() {
        String id = "test";

        webTestClient.get()
                .uri("/api/v1/batting-info/{id}", id)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    System.out.println(response);
                });
    }

    @Test
    @DisplayName("BattingInfoFlowTest: Find Batting Info By PlayerId")
    public void testGetBattingInfoByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        String path = "/api/v1/batting-info/by-player-id/{player-id}";
        webTestClient.get()
                .uri(path, playerId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(BattingInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    Assertions.assertEquals(3, response.getStatistics().size());
                });
    }
}
