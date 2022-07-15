package org.fourstack.reactivecricinfo.apigateway.controller;

import org.fourstack.reactivecricinfo.apigateway.client.PlayerInfoApiHelper;
import org.fourstack.reactivecricinfo.apigateway.dto.PlayerInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerInfoController {

    @Autowired
    private PlayerInfoApiHelper playerInfoApiHelper;

    @GetMapping("/{player-id}")
    public Mono<PlayerInfoDTO> retrievePlayerInfoById(@PathVariable("player-id") String playerId) {
        return playerInfoApiHelper.retrievePlayerInfoById(playerId);
    }

    @GetMapping("/by-lastname/{lastname}")
    public Flux<PlayerInfoDTO> retrievePlayersInfoByLastname(@PathVariable("lastname") String lastname) {
        return playerInfoApiHelper.retrievePlayersByLastname(lastname);
    }

    @GetMapping("/by-firstname/{firstname}")
    public Flux<PlayerInfoDTO> retrievePlayersInfoByFirstname(@PathVariable("firstname") String firstname) {
        return playerInfoApiHelper.retrievePlayersByFirstname(firstname);
    }

    @GetMapping("/by-country/{country}")
    public Flux<PlayerInfoDTO> retrievePlayersInfoByCountry(@PathVariable("country") String country) {
        return playerInfoApiHelper.retrievePlayersByCountry(country);
    }

    @GetMapping("/by-gender/{gender}")
    public Flux<PlayerInfoDTO> retrievePlayersInfoByGender(@PathVariable("gender") String gender) {
        return playerInfoApiHelper.retrievePlayersByGender(gender);
    }

    @GetMapping("/by-batting-style/{batting-style}")
    public Flux<PlayerInfoDTO> retrievePlayersInfoByBattingStyle(@PathVariable("batting-style") String battingStyle) {
        return playerInfoApiHelper.retrievePlayersByBattingStyle(battingStyle);
    }

    @GetMapping("/by-bowling-style/{bowling-style}")
    public Flux<PlayerInfoDTO> retrievePlayersInfoByBowlingStyle(@PathVariable("bowling-style") String bowlingStyle) {
        return playerInfoApiHelper.retrievePlayersByBowlingStyle(bowlingStyle);
    }
}
