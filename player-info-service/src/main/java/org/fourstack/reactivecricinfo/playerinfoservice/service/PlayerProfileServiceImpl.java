package org.fourstack.reactivecricinfo.playerinfoservice.service;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.PlayerInfoNotFoundException;
import org.fourstack.reactivecricinfo.playerinfoservice.exception.PlayerServiceException;
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
    private ConversionService profileToDtoConverter;

    @Autowired
    private ConversionService playerDtoToProfileConverter;

    /**
     * Method to fetch the Player details based on the player-id.
     *
     * @param playerId Unique player-id associated with Player.
     * @return PlayerInformation as {@link PlayerInfoDTO}
     */
    @Override
    public Mono<PlayerInfoDTO> getPlayerById(String playerId) {
        return playerRepository
                .findById(playerId)
                .onErrorResume(err -> {
                            log.error("PlayerProfileService: getPlayerById - {}", err.getMessage());
                            return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                        }
                ).switchIfEmpty(
                        generateError("No Player Details found for the playerId: ", playerId)
                ).map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
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
        return playerRepository
                .findByCountry(country)
                .onErrorResume(err -> {
                    log.error("PlayerProfileService: getPlayersByCountry - {}", err.getMessage());
                    return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                }).switchIfEmpty(
                        generateError("No Player found for the Country :", country)
                ).map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }

    /**
     * Method to fetch Players information by using gender as parameter.
     *
     * @param gender Gender Type : MALE, FEMALE or OTHER
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByGender(String gender) {
        return playerRepository
                .findByGender(gender)
                .onErrorResume(err -> {
                            log.error("PlayerProfileService: getPlayersByGender - {}", err.getMessage());
                            return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                        }
                ).switchIfEmpty(
                        generateError("No Player found for the Gender :", gender)
                ).map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }

    /**
     * Method to fetch Players information by using Batting Style as parameter.
     *
     * @param battingStyle @{@link org.fourstack.reactivecricinfo.playerinfoservice.codetype.BattingStyleType}
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByBattingStyle(String battingStyle) {
        return playerRepository
                .findByBattingStyle(battingStyle)
                .onErrorResume(err -> {
                            log.error("PlayerProfileService: getPlayersByBattingStyle - {}", err.getMessage());
                            return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                        }
                ).switchIfEmpty(generateError("No Player found for the Batting Style :", battingStyle))
                .map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }

    /**
     * Method to fetch Players information by using Bowling Style as parameter.
     *
     * @param bowlingStyle {@link org.fourstack.reactivecricinfo.playerinfoservice.codetype.BowlingStyleType}
     * @return Flux of Players Information.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByBowlingStyle(String bowlingStyle) {
        return playerRepository
                .findByBowlingStyle(bowlingStyle)
                .onErrorResume(err -> {
                            log.error("PlayerProfileService: getPlayersByBowlingStyle - ", err);
                            return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                        }
                ).switchIfEmpty(generateError("No Player found for the Bowling Style :", bowlingStyle))
                .map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }


    @Override
    public Mono<PlayerInfoDTO> createPlayerProfile(PlayerInfoDTO dto) {
        var playerProfile = playerDtoToProfileConverter.convert(dto, PlayerProfile.class);

        return playerRepository.save(playerProfile)
                .onErrorResume(err -> {
                            log.error("PlayerProfileService: createPlayerProfile - {}", err.getMessage());
                            return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                        }
                ).map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }

    @Override
    public Mono<Boolean> isPlayerExist(String playerId) {
        return playerRepository.findById(playerId)
                .onErrorResume(err -> {
                    log.error("PlayerProfileService: isPlayerExist - {}", err.getMessage());
                    return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                })
                .switchIfEmpty(generateError("No Player found for the playerId: ", playerId))
                .map(daoObj -> true);
    }

}
