package org.fourstack.reactivecricinfo.playerinfoservice.unit;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.controller.ProfileCommandController;
import org.fourstack.reactivecricinfo.playerinfoservice.controller.ProfileQueryController;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.GlobalExceptionHandler;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileService;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileServiceImpl;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
@ContextConfiguration(classes = {ProfileCommandController.class, ProfileQueryController.class,
        GlobalExceptionHandler.class, PlayerProfileServiceImpl.class, PlayerProfileService.class,
})
@AutoConfigureWebTestClient
public class ProfileControllerUnitTest {
    List<PlayerInfoDTO> playerDTOList = EntityGenerator.getPlayerDTOList();

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

        // filter the dto's using GenderType MALE
        var filteredList = playerDTOList.stream()
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

    @Test
    @DisplayName("ProfileControllerUnitTest: Get Players by BattingStyle")
    public void testGetPlayersByBattingStyle(){
        String battingStyle = BattingStyleType.RIGHT_HANDED_BATSMAN.name();

        // filter the dto's using BattingStyle RIGHT_HANDED_BATSMAN
        var filteredList = playerDTOList.stream()
                .filter(obj -> BattingStyleType.RIGHT_HANDED_BATSMAN.equals(obj.getBattingStyle()))
                .collect(Collectors.toList());

        // Mock the service layer
        Mockito.when(playerService.getPlayersByBattingStyle(battingStyle))
                .thenReturn(Flux.fromIterable(filteredList));

        webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/v1/player/batting-style/{style}")
                                .build(battingStyle)
                ).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(filteredList.size());


    }

    @Test
    @DisplayName("ProfileControllerUnitTest: Get Players by BowlingStyle")
    public void testGetPlayersByBowlingStyle(){
        var bowlingStyle = BowlingStyleType.RIGHT_ARM_MEDIUM.name();

        // get filtered dto's using BowlingStyle RIGHT_ARM_MEDIUM
        var filteredList = playerDTOList.stream()
                .filter(obj -> BowlingStyleType.RIGHT_ARM_MEDIUM.equals(obj.getBowlingStyle()))
                .collect(Collectors.toList());

        // Mock the service layer
        Mockito.when(playerService.getPlayersByBowlingStyle(bowlingStyle))
                .thenReturn(Flux.fromIterable(filteredList));

        webClient.get()
                .uri("/api/v1/player/bowling-style/{style}", bowlingStyle)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PlayerInfoDTO.class)
                .hasSize(filteredList.size());
    }

    @Test
    @DisplayName("ProfileControllerUnitTest: Create Player profile")
    public void testCreatePlayerProfile() {
        var playerDTO = EntityGenerator.getPlayerInfoDTO();

        // Mock the service layer
        Mockito.when(playerService.createPlayerProfile(playerDTO))
                .thenReturn(Mono.just(playerDTO));

        webClient.post()
                .uri("/api/v1/player")
                .bodyValue(playerDTO)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PlayerInfoDTO.class)
                .consumeWith(exchangeResult -> {
                    var response = exchangeResult.getResponseBody();
                    assert response != null;
                    assertEquals("Sachin", response.getBasicInfo().getFirstName());
                    assertEquals("Tendulkar", response.getBasicInfo().getLastName());
                    assertEquals(BattingStyleType.RIGHT_HANDED_BATSMAN, response.getBattingStyle());
                    assertEquals(BowlingStyleType.RIGHT_ARM_LEGBREAK, response.getBowlingStyle());
                });
    }
}
