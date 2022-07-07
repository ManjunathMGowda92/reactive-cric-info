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
                        generatePlayerNotFoundException("No Player Details found for the playerId: ", playerId)
                ).map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }

    private Mono<PlayerProfile> generatePlayerNotFoundException(String message, String concatStr) {
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
                        generatePlayerNotFoundException("No Player found for the Country :", country)
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
                        generatePlayerNotFoundException("No Player found for the Gender :", gender)
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
                ).switchIfEmpty(generatePlayerNotFoundException("No Player found for the Batting Style :", battingStyle))
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
                ).switchIfEmpty(generatePlayerNotFoundException("No Player found for the Bowling Style :", bowlingStyle))
                .map(profile -> profileToDtoConverter.convert(profile, PlayerInfoDTO.class));
    }

    /**
     * Method to create PlayerInfo Object. Accepts {@link PlayerInfoDTO} object and
     * then converts into {@link PlayerProfile} DAO Object and then save to Database.
     * After saving to database, dao object will be converted to DTO object and then
     * returned.
     *
     * @param dto {@link PlayerInfoDTO} Object
     * @return PlayerInfoDTO object.
     */
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

    /**
     * Method to check if PlayerInfo Exist or not in the Database.
     *
     * @param playerId PlayerId of a Player.
     * @return Boolean value which resembles player exist or not.
     */
    @Override
    public Mono<Boolean> isPlayerExist(String playerId) {
        return playerRepository.findById(playerId)
                .onErrorResume(err -> {
                    log.error("PlayerProfileService: isPlayerExist - {}", err.getMessage());
                    return Mono.error(new PlayerServiceException(err.getMessage(), err.getCause()));
                })
                .switchIfEmpty(generatePlayerNotFoundException("No Player found for the playerId: ", playerId))
                .map(daoObj -> true);
    }

    /**
     * Method to fetch the Flux of {@link PlayerInfoDTO} by firstname.
     *
     * @param firstname parameter firstname
     * @return Flux of PlayerInfoDTO objects.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByFirstName(String firstname) {
        return playerRepository.findByFirstNameIgnoreCase(firstname)
                .onErrorResume(err -> Mono.error(new PlayerServiceException(err.getMessage(), err.getCause())))
                .switchIfEmpty(generatePlayerNotFoundException("No Players found for firstname: ", firstname))
                .map(daoObj -> profileToDtoConverter.convert(daoObj, PlayerInfoDTO.class));
    }

    /**
     * Method to fetch Flux of {@link PlayerInfoDTO} by lastname.
     *
     * @param lastname parameter lastname.
     * @return Flux of PlayerInfoDTO objects.
     */
    @Override
    public Flux<PlayerInfoDTO> getPlayersByLastName(String lastname) {
        return playerRepository.findByLastNameIgnoreCase(lastname)
                .onErrorResume(err -> Mono.error(new PlayerServiceException(err.getMessage(), err.getCause())))
                .switchIfEmpty(generatePlayerNotFoundException("No Players found for lastname: ", lastname))
                .map(daoObj -> profileToDtoConverter.convert(daoObj, PlayerInfoDTO.class));
    }

}
