package org.fourstack.reactivecricinfo.playerinfoservice.unit;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.PlayerInfoNotFoundException;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileServiceImpl;
import org.fourstack.reactivecricinfo.playerinfoservice.util.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlayerProfileServiceTest {

    PlayerProfile playerProfile = EntityGenerator.getPlayerProfile();
    PlayerInfoDTO playerInfoDTO = EntityGenerator.getPlayerInfoDTO();
    List<PlayerProfile> playerProfiles = EntityGenerator.getPlayerProfileList();

    @Mock
    private PlayerProfileRepository playerRepository;

    @Mock
    private ConversionService profileToDtoConverter;

    @InjectMocks
    private PlayerProfileServiceImpl service;

    @Test
    @DisplayName("PlayerProfileServiceTest: Get Player By Id.")
    public void testGetPlayerById() {
        String playerId = "VIR2022-6Y8-5P14-48SA-257223300";

        Mockito.when(playerRepository.findById(playerId))
                .thenReturn(Mono.just(playerProfile));
        Mockito.when(profileToDtoConverter.convert(playerProfile, PlayerInfoDTO.class))
                .thenReturn(playerInfoDTO);

        Mono<PlayerInfoDTO> playerDTO = service.getPlayerById(playerId);

        StepVerifier.create(playerDTO)
                .assertNext(dto -> {
                            assert dto != null;
                            assertEquals(BattingStyleType.RIGHT_HANDED_BATSMAN, dto.getBattingStyle());
                            assertEquals(BowlingStyleType.RIGHT_ARM_LEGBREAK, dto.getBowlingStyle());
                            assertEquals("India", dto.getBasicInfo().getCountry());
                        }
                ).verifyComplete();

        //verify the method calls
        Mockito.verify(playerRepository, Mockito.times(1))
                .findById(playerId);
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerNotFoundException for getPlayerById")
    public void testGetPlayerByIdNotFound() {
        String playerId = "VIR2022-6Y8-5P14-48SA-257223300";

        Mockito.when(playerRepository.findById(playerId)).thenReturn(Mono.empty());
        var playerDTO = service.getPlayerById(playerId);
        StepVerifier.create(playerDTO)
                .expectError(PlayerInfoNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: Get Players by country")
    public void testGetPlayersByCountry() {
        String country = "India";
        List<PlayerProfile> playerProfiles1 = playerProfiles.stream()
                .filter(profile -> profile.getCountry().equals(country))
                .collect(Collectors.toList());

        Mockito.when(playerRepository.findByCountry(country))
                .thenReturn(Flux.fromIterable(playerProfiles1));
        Mockito.when(profileToDtoConverter.convert(Mockito.any(PlayerProfile.class), Mockito.any()))
                .thenReturn(playerInfoDTO);
        var playerFlux = service.getPlayersByCountry(country);

        StepVerifier.create(playerFlux)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerNotFoundException for getPlayersByCountry")
    public void testGetPlayersByCountryNotFound() {
        String country = "North Atlantic";

        Mockito.when(playerRepository.findByCountry(country))
                .thenReturn(Flux.empty());

        var playerFlux = service.getPlayersByCountry(country);
        StepVerifier.create(playerFlux)
                .expectError(PlayerInfoNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: Get players by Gender.")
    public void testGetPlayersByGender() {
        String gender = "MALE";
        List<PlayerProfile> playerProfiles1 = playerProfiles.stream()
                .filter(obj -> GenderType.valueOf(gender).equals(obj.getGender()))
                .collect(Collectors.toList());

        Mockito.when(playerRepository.findByGender(gender))
                .thenReturn(Flux.fromIterable(playerProfiles1));
        Mockito.when(profileToDtoConverter.convert(Mockito.any(PlayerProfile.class), Mockito.any()))
                .thenReturn(playerInfoDTO);

        var playerFlux = service.getPlayersByGender(gender);
        StepVerifier.create(playerFlux)
                .expectNextCount(playerProfiles1.size())
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerNotFoundException for getPlayersByGender")
    public void testGetPlayersByGenderNotFound() {
        String gender = "MALE";

        Mockito.when(playerRepository.findByGender(gender))
                .thenReturn(Flux.empty());

        var playerFlux = service.getPlayersByGender(gender);
        StepVerifier.create(playerFlux)
                .expectErrorMatches(throwable -> throwable instanceof PlayerInfoNotFoundException)
                .verify();
    }
}
