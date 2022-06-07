package org.fourstack.reactivecricinfo.battinginfoservice.service;

import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;
import reactor.core.publisher.Mono;

public interface BattingInfoService {

    Mono<BattingInfoDTO> getBattingInfoByPlayerId(String playerId);

    Mono<BattingInfoDTO> getBattingInfoById(String id);

    Mono<BattingInfoDTO> createBattingInfo(BattingInfoDTO dto);
}
