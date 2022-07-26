package org.fourstack.reactivecricinfo.battinginfoservice.controller;

import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.service.BattingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * RestController class which acts for query methods (GET..)
 * It is responsible to expose only query related methods,
 * which doesn't change state of Object.
 */
@RestController
@RequestMapping("/api/v1/batting-info")
public class BattingInfoQueryController {

    @Autowired
    private BattingInfoService service;

    @GetMapping("/{id}")
    public Mono<BattingInfoDTO> getBattingInfoById(@PathVariable("id") String id) {
        return service.getBattingInfoById(id);
    }

    @GetMapping("/by-player-id/{player-id}")
    public Mono<BattingInfoDTO> getBattingInfoByPLayerId(@PathVariable("player-id") String playerId) {
        return service.getBattingInfoByPlayerId(playerId);
    }
}
