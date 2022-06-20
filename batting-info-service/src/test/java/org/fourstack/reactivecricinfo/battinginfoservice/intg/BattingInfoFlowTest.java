package org.fourstack.reactivecricinfo.battinginfoservice.intg;

import org.fourstack.reactivecricinfo.battinginfoservice.dao.BattingInfoDao;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.exception.model.ErrorResponse;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;
import org.fourstack.reactivecricinfo.battinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class BattingInfoFlowTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BattingInfoDao dao;

    @BeforeEach
    public void setUpData() {
        BattingInfo entity = EntityGenerator.battingInfoDAO();
        dao.saveAll(List.of(entity))
                .blockLast();
    }

    @AfterEach
    public void eraseData() {
        dao.deleteAll()
                .block();
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
}
