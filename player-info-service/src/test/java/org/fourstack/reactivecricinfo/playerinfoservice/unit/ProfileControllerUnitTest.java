package org.fourstack.reactivecricinfo.playerinfoservice.unit;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.controller.ProfileCommandController;
import org.fourstack.reactivecricinfo.playerinfoservice.controller.ProfileQueryController;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileService;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@WebFluxTest(controllers = {ProfileQueryController.class, ProfileCommandController.class})
@AutoConfigureWebTestClient
public class ProfileControllerUnitTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PlayerProfileService playerService;

    @Test
    @DisplayName("ProfileControllerUnitTest: Get PlayerProfile by playerId")
    public void testGetProfileByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        Mockito.when(playerService.getPlayerById(playerId))
                .thenReturn(Mono.just(EntityGenerator.getPlayerInfoDTO()));

        webClient.get()
                .uri("/api/v1/player/{id}", playerId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PlayerInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("Sachin", response.getBasicInfo().getFirstName());
                    assertEquals("India", response.getBasicInfo().getCountry());
                    assertEquals(BattingStyleType.RIGHT_HANDED_BATSMAN, response.getBattingStyle());
                });

    }

    @Test
    @DisplayName("ProfileControllerUnitTest: Get Players by Country name")
    public void testGetPlayerProfilesByCountry() {
        String country = "India";
        // Get Complete DTO List
        List<PlayerInfoDTO> playerDTOList = EntityGenerator.getPlayerDTOList();

        // Extract dto's only related to country India
        List<PlayerInfoDTO> dtoList = playerDTOList.stream()
                .filter(obj -> obj.getBasicInfo().getCountry().equals(country))
                .collect(Collectors.toList());

        // Mock the service layer
        Mockito.when(playerService.getPlayersByCountry(country))
                .thenReturn(Flux.fromIterable(dtoList));

        webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/v1/player/by-country-name/{country-name}")
                                .build(country)
                ).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(dtoList.size());
    }

    @Test
    @DisplayName("ProfileControllerUnitTest: Get Players by Gender")
    public void testGetPlayersByGender() {
        String gender = "MALE";
        // Get complete DTO List
        var dtoList = EntityGenerator.getPlayerDTOList();

        // filter the dto's using GenderType MALE
        var filteredList = dtoList.stream()
                .filter(obj -> obj.getBasicInfo().getGender().equals(GenderType.MALE))
                .collect(Collectors.toList());

        // Mock the service layer
        Mockito.when(playerService.getPlayersByGender(gender))
                .thenReturn(Flux.fromIterable(filteredList));

        webClient.get()
                .uri("/api/v1/player/by-gender/{gender}", gender)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(filteredList.size());
    }

}
