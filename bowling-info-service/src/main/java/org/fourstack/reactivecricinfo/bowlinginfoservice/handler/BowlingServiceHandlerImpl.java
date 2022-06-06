package org.fourstack.reactivecricinfo.bowlinginfoservice.handler;

import org.fourstack.reactivecricinfo.bowlinginfoservice.codetype.CricketFormat;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dao.BowlingInfoDao;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingStatistics;
import org.fourstack.reactivecricinfo.bowlinginfoservice.util.IdGenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BowlingServiceHandlerImpl implements BowlingServiceHandler {

    @Autowired
    private ConversionService dtoToDaoConverter;

    @Autowired
    private ConversionService daoToDtoConverter;

    @Autowired
    private BowlingInfoDao repository;


    @Override
    public Mono<ServerResponse> fetchBowlingInfoByPlayerId(ServerRequest request) {
        System.out.println("***********************************************************************************");
        String playerId = request.pathVariable("player-id");
        System.out.println(playerId);

        return repository.findByPlayerId(playerId)
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.ok() :: bodyValue);
    }

    @Override
    public Mono<ServerResponse> fetchBowlingInfoById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.ok() :: bodyValue);
    }

    @Override
    public Mono<ServerResponse> createBowlingInfo(ServerRequest request) {
        Mono<BowlingInfoDTO> dtoMono = request.bodyToMono(BowlingInfoDTO.class);

        return dtoMono
                .map(dto -> dtoToDaoConverter.convert(dto, BowlingInfo.class))
                .flatMap(repository::save)
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
    }

    private BowlingInfo bowlingInfo(String playerId) {
        BowlingInfo info = new BowlingInfo();
        info.setBowlingId(IdGenerationUtil.generateBowlingInfoId(playerId));

        List<BowlingStatistics> statistics = new ArrayList<>();
        BowlingStatistics stat1 = new BowlingStatistics();
        stat1.setFormat(CricketFormat.ODI.name());
        stat1.setMatches(234);
        stat1.setInnings(233);
        stat1.setBalls(3121);
        stat1.setRuns(3212);
        stat1.setMaidens(23);
        stat1.setWickets(258);
        stat1.setAverage(25.43);
        stat1.setEconomy(7.87);
        stat1.setStrikeRate(87.56);
        stat1.setBestBowlingInMatch("4/25");
        stat1.setBestBowlingInInnings("4/25");
        stat1.setFourWicketHaul("1");
        statistics.add(stat1);

        info.setStatistics(statistics);
        info.setPlayerId(playerId);
        return info;
    }
}
