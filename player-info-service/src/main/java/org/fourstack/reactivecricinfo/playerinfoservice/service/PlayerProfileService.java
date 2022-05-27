package org.fourstack.reactivecricinfo.playerinfoservice.service;

import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerProfileService {

    Mono<PlayerInfoDTO> getPlayerById(String playerId);

    Flux<PlayerInfoDTO> getPlayersByCountry(String country);

    Flux<PlayerInfoDTO> getPlayersByGender(String gender);

    Flux<PlayerInfoDTO> getPlayersByBattingStyle(String battingStyle);

    Flux<PlayerInfoDTO> getPlayersByBowlingStyle(String bowlingStyle);

    Mono<PlayerInfoDTO> createPlayerProfile(PlayerInfoDTO dto);


}
