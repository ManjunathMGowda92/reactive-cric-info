package org.fourstack.reactivecricinfo.bowlinginfoservice.handler;

import org.fourstack.reactivecricinfo.bowlinginfoservice.codetype.CricketFormat;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dao.BowlingInfoDao;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.exception.BowlingDataNotFoundException;
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
        String playerId = request.pathVariable("player-id");
        return repository.findByPlayerId(playerId)
                .switchIfEmpty(Mono.error(new BowlingDataNotFoundException("No BowlingInfo found for the PlayerId: "+playerId)))
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.ok() :: bodyValue);
    }

    @Override
    public Mono<ServerResponse> fetchBowlingInfoById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BowlingDataNotFoundException("No BowlingInfo found for the Id: "+id)))
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
}
