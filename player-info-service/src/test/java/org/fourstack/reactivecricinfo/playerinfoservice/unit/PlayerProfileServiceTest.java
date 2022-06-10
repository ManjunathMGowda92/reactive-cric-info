package org.fourstack.reactivecricinfo.playerinfoservice.unit;

import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType;
import org.fourstack.reactivecricinfo.playerinfoservice.codetype.GenderType;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.PlayerInfoNotFoundException;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.PlayerServiceException;
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
    @DisplayName("PlayerProfileServiceTest: PlayerServiceException for getPlayerById")
    public void testGetPlayerByIdPlayerServiceException() {
        String playerId = "VIR2022-6Y8-5P14-48SA-257223300";
        Mockito.when(playerRepository.findById(playerId))
                .thenReturn(Mono.error(new RuntimeException("DAOException: Fetch by playerId")));
        var playerMono = service.getPlayerById(playerId);
        StepVerifier.create(playerMono)
                .expectError(PlayerServiceException.class)
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
    @DisplayName("PlayerProfileServiceTest: PlayerServiceException for getPlayersByCountry")
    public void testGetPlayersByCountryPlayerServiceException() {
        String country = "Nameless Country";
        Mockito.when(playerRepository.findByCountry(country))
                .thenReturn(Flux.error(new Throwable("Exception while fetch using country name")));
        var playersFlux = service.getPlayersByCountry(country);
        StepVerifier.create(playersFlux)
                .expectErrorMessage("Exception while fetch using country name")
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

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerServiceException for getPlayersByGender")
    public void testGetPlayersByGenderPlayerServiceException() {
        String gender = "MALE";
        Mockito.when(playerRepository.findByGender(gender))
                .thenReturn(Flux.error(new Exception("Exception occurred while fetching data")));
        var playersFlux = service.getPlayersByGender(gender);
        StepVerifier.create(playersFlux)
                .expectErrorMatches(throwable -> throwable instanceof  PlayerServiceException)
                .verify();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: Get players by Batting Style")
    public void testGetPlayersByBattingStyle() {
        String battingStyle = "RIGHT_HANDED_BATSMAN";
        List<PlayerProfile> playerProfiles1 = playerProfiles.stream()
                .filter(obj -> BattingStyleType.RIGHT_HANDED_BATSMAN.equals(obj.getBattingStyle()))
                .collect(Collectors.toList());

        Mockito.when(playerRepository.findByBattingStyle(battingStyle))
                .thenReturn(Flux.fromIterable(playerProfiles1));
        Mockito.when(profileToDtoConverter.convert(Mockito.any(PlayerProfile.class), Mockito.any()))
                .thenReturn(playerInfoDTO);

        var playerFlux = service.getPlayersByBattingStyle(battingStyle);
        StepVerifier.create(playerFlux)
                .expectNextCount(playerProfiles1.size())
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerNotFoundException for getPlayersByBattingStyle")
    public void testGetPlayersByBattingStyleNotFound() {
        String battingStyle = "RIGHT_HANDED_BATSMAN";

        Mockito.when(playerRepository.findByBattingStyle(battingStyle))
                .thenReturn(Flux.empty());

        var playersFlux = service.getPlayersByBattingStyle(battingStyle);
        StepVerifier.create(playersFlux)
                .expectError(PlayerInfoNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerServiceException for getPlayersByBattingStyle")
    public void testGetPlayersByBattingStyleForPlayerServiceException(){
        String battingStyle = "RIGHT_HANDED_BATSMAN";
        Mockito.when(playerRepository.findByBattingStyle(battingStyle))
                .thenReturn(Flux.error(new Exception("Exception from DAO layer")));

        var playersFlux = service.getPlayersByBattingStyle(battingStyle);
        StepVerifier.create(playersFlux)
                .expectError(PlayerServiceException.class)
                .verify();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: Get players by Bowling Style")
    public void testGetPlayersByBowlingStyle() {
        String bowlingStyle = "RIGHT_ARM_LEGBREAK";
        List<PlayerProfile> playerProfiles1 = playerProfiles.stream()
                .filter(obj -> BowlingStyleType.RIGHT_ARM_LEGBREAK.equals(obj.getBowlingStyle()))
                .collect(Collectors.toList());

        Mockito.when(playerRepository.findByBowlingStyle(bowlingStyle))
                .thenReturn(Flux.fromIterable(playerProfiles1));
        Mockito.when(profileToDtoConverter.convert(Mockito.any(PlayerProfile.class), Mockito.any()))
                .thenReturn(playerInfoDTO);

        var playersFlux = service.getPlayersByBowlingStyle(bowlingStyle);
        StepVerifier.create(playersFlux)
                .expectNextCount(playerProfiles1.size())
                .verifyComplete();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerNotFoundException for getPlayersByBowlingStyle")
    public void testGetPlayersByBowlingStyleNotFound() {
        String bowlingStyle = "RIGHT_ARM_LEGBREAK";
        Mockito.when(playerRepository.findByBowlingStyle(bowlingStyle))
                .thenReturn(Flux.empty());

        var playersFlux = service.getPlayersByBowlingStyle(bowlingStyle);
        StepVerifier.create(playersFlux)
                .expectErrorMatches(throwable -> throwable instanceof PlayerInfoNotFoundException)
                .verify();
    }

    @Test
    @DisplayName("PlayerProfileServiceTest: PlayerServiceException for getPlayersByBowlingStyle")
    public void testGetPlayersByBowlingStyleForPlayerServiceException() {
        String bowlingStyle = "RIGHT_ARM_LEGBREAK";
        Mockito.when(playerRepository.findByBowlingStyle(bowlingStyle))
                .thenReturn(Flux.error(new RuntimeException("Unknown Exception occurred.")));

        var playersFlux = service.getPlayersByBowlingStyle(bowlingStyle);
        StepVerifier.create(playersFlux)
                .expectError(PlayerServiceException.class)
                .verify();
    }
}
