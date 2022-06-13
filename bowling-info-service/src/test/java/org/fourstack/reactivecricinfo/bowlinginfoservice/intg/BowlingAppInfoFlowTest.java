package org.fourstack.reactivecricinfo.bowlinginfoservice.intg;

import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.AppInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
@AutoConfigureWebTestClient
public class BowlingAppInfoFlowTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("BowlingAppInfoFlowTest: Get Application Info")
    public void testGetAppInfo() {
        webClient.get()
                .uri("/api/v1/info")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppInfo.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;

                    Assertions.assertEquals("bowling-info-service", response.getAppName());
                    Assertions.assertEquals("1.0.0", response.getAppVersion());
                });
    }
}
