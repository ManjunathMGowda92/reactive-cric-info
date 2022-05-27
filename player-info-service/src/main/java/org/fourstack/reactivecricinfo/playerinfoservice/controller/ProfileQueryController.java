package org.fourstack.reactivecricinfo.playerinfoservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.playerinfoservice.dao.PlayerProfileRepository;
import org.fourstack.reactivecricinfo.playerinfoservice.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.playerinfoservice.service.PlayerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * RestController class which acts for the Query (GET).
 * It exposes the method which are responsible for the query of data.
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ProfileQueryController {

    @Autowired
    PlayerProfileRepository repository;

    @Autowired
    private PlayerProfileService playerService;

    @GetMapping("/player-profile/{id}")
    public Mono<PlayerInfoDTO> getProfileByPlayerId(@PathVariable("id") String playerId) {
        log.info("Player Id ::: " + playerId);
        return playerService.getPlayerById(playerId);
    }

    @GetMapping("/player-profile/by-country-name/{country-name}")
    public Flux<PlayerInfoDTO> getPlayerProfilesByCountry(@PathVariable("country-name") String country) {
        return playerService.getPlayersByCountry(country);
    }

    @GetMapping("/player-profile/by-gender/{gender}")
    public Flux<PlayerInfoDTO> getPlayersByGender(@PathVariable("gender") String gender) {
        return playerService.getPlayersByGender(gender);
    }

    @GetMapping("/player-profile/batting-style/{style}")
    public Flux<PlayerInfoDTO> getPlayersByBattingStyle(@PathVariable("style") String battingStyle) {
        return playerService.getPlayersByBattingStyle(battingStyle);
    }

    @GetMapping("/player-profile/bowling-style/{style}")
    public Flux<PlayerInfoDTO> getPlayersByBowlingStyle(@PathVariable("style") String bowlingStyle) {
        return playerService.getPlayersByBowlingStyle(bowlingStyle);
    }
}
