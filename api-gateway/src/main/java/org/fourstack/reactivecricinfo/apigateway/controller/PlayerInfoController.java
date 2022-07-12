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
    public Flux<PlayerInfoDTO> retrievePlayersInfoByLastName(@PathVariable("lastname") String lastname) {
        return playerInfoApiHelper.retrievePlayersByLastname(lastname);
    }
}
