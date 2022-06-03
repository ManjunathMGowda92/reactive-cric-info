package org.fourstack.reactivecricinfo.playerinfoservice.service;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.PlayerInfoNotFoundException;
import org.fourstack.reactivecricinfo.playerinfoservice.model.PlayerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PlayerProfileServiceImpl implements PlayerProfileService {

    @Autowired
    private PlayerProfileRepository playerRepository;

    @Autowired
    private ConversionService playerProfileToDtoConverter;

    @Autowired
    private ConversionService playerDtoToProfileConverter;

    @Autowired
    private ConversionService multipleProfileToDtoConverter;

    /**
     * Method to fetch the Player details based on the player-id.
     *
     * @param playerId Unique player-id associated with Player.
     * @return PlayerInformation as {@link PlayerInfoDTO}
     */
    @Override
    public Mono<PlayerInfoDTO> getPlayerById(String playerId) {
        Mono<PlayerProfile> playerProfile = playerRepository.findById(playerId)
                .switchIfEmpty(
                        generateError("No Player Details found for the playerId: ", playerId)
                );
        Mono<PlayerInfoDTO> convertedObj =
                playerProfileToDtoConverter.convert(playerProfile, (Mono.class));
        return convertedObj;
    }

    private Mono<PlayerProfile> generateError(String message, String concatStr) {
        return Mono.error(() -> new PlayerInfoNotFoundException(message + concatStr));
    }

    /**
     * Method to fetch Players information by using country as parameter.
     *
     * @param country Country name
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByCountry(String country) {
        Flux<PlayerProfile> playerProfiles = playerRepository.findByCountry(country)
                .switchIfEmpty(
                        generateError("No Player found for the Country :", country)
                );
        return multipleProfileToDtoConverter.convert(playerProfiles, Flux.class);
    }

    /**
     * Method to fetch Players information by using gender as parameter.
     *
     * @param gender Gender Type : MALE, FEMALE or OTHER
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByGender(String gender) {
        Flux<PlayerProfile> playerProfiles = playerRepository.findByGender(gender)
                .switchIfEmpty(
                        generateError("No Player found for the Gender :", gender)
                );
        return multipleProfileToDtoConverter.convert(playerProfiles, Flux.class);
    }

    /**
     * Method to fetch Players information by using Batting Style as parameter.
     *
     * @param battingStyle @{@link org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType}
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByBattingStyle(String battingStyle) {
        Flux<PlayerProfile> playerProfiles = playerRepository.findByBattingStyle(battingStyle)
                .switchIfEmpty(generateError("No Player found for the Batting Style :", battingStyle));
        return multipleProfileToDtoConverter.convert(playerProfiles, Flux.class);
    }

    /**
     * Method to fetch Players information by using Bowling Style as parameter.
     *
     * @param bowlingStyle {@link org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType}
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByBowlingStyle(String bowlingStyle) {
        Flux<PlayerProfile> playerProfiles = playerRepository.findByBowlingStyle(bowlingStyle)
                .switchIfEmpty(generateError("No Player found for the Bowling Style :", bowlingStyle));
        return multipleProfileToDtoConverter.convert(playerProfiles, Flux.class);
    }


    @Override
    public Mono<PlayerInfoDTO> createPlayerProfile(PlayerInfoDTO dto) {
        var playerProfile = playerDtoToProfileConverter.convert(dto, PlayerProfile.class);
        Mono<PlayerProfile> profileMono = playerRepository.save(playerProfile);

        return playerProfileToDtoConverter.convert(profileMono, Mono.class);
    }

}
