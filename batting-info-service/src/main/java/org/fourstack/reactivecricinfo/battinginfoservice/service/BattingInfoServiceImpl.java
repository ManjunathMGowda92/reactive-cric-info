package org.fourstack.reactivecricinfo.battinginfoservice.service;

import org.fourstack.reactivecricinfo.battinginfoservice.dao.BattingInfoDao;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
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

    private ConversionService battingDtoToDaoConverter;

    @Override
    public Mono<BattingInfoDTO> getBattingInfoByPlayerId(String playerId) {
        return repository
                .findByPlayerId(playerId)
                .map(daoModel -> battingDaoToDtoConverter.convert(daoModel, BattingInfoDTO.class));
    }

    @Override
    public Mono<BattingInfoDTO> getBattingInfoById(String id) {
        return repository
                .findByPlayerId(id)
                .map(daoModel -> battingDaoToDtoConverter.convert(daoModel, BattingInfoDTO.class));
    }

    @Override
    public Mono<BattingInfoDTO> createBattingInfo(BattingInfoDTO dto) {
        BattingInfo battingInfo = battingDtoToDaoConverter.convert(dto, BattingInfo.class);

        return repository
                .save(battingInfo)
                .map(daoModel -> battingDaoToDtoConverter.convert(daoModel, BattingInfoDTO.class));
    }
}
