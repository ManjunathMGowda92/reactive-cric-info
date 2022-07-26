package org.fourstack.reactivecricinfo.battinginfoservice.service;

import org.fourstack.reactivecricinfo.battinginfoservice.dao.BattingInfoDao;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.exception.BattingInfoNotFoundException;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BattingInfoServiceImpl implements BattingInfoService {

    @Autowired
    private BattingInfoDao repository;

    @Autowired
    private ConversionService battingDaoToDtoConverter;

    @Autowired
    private ConversionService battingDtoToDaoConverter;

    @Override
    public Mono<BattingInfoDTO> getBattingInfoByPlayerId(String playerId) {
        return repository
                .findByPlayerId(playerId)
                .switchIfEmpty(
                        createBattingInfoNotFoundException(
                                "BattingInfo data not found for the playerId: ",
                                playerId
                        )
                ).map(daoModel ->
                        battingDaoToDtoConverter.convert(daoModel, BattingInfoDTO.class)
                );
    }

    private Mono<BattingInfo> createBattingInfoNotFoundException(String message, String concatStr) {
        return Mono.error(
                new BattingInfoNotFoundException(message + concatStr));
    }

    @Override
    public Mono<BattingInfoDTO> getBattingInfoById(String id) {
        return repository
                .findById(id)
                .switchIfEmpty(
                        createBattingInfoNotFoundException(
                                "BattingInfo data not found for the Id:",
                                id
                        )
                ).map(daoModel -> battingDaoToDtoConverter.convert(daoModel, BattingInfoDTO.class));
    }

    @Override
    public Mono<BattingInfoDTO> createBattingInfo(BattingInfoDTO dto) {
        BattingInfo battingInfo = battingDtoToDaoConverter.convert(dto, BattingInfo.class);

        return repository
                .save(battingInfo)
                .map(daoModel -> battingDaoToDtoConverter.convert(daoModel, BattingInfoDTO.class));
    }
}
