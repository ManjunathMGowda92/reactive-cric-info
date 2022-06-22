package org.fourstack.reactivecricinfo.rankinginfoservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.rankinginfoservice.dao.RankingInfoDao;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.IccRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RankingServiceHandlerImpl implements RankingServiceHandler{

    @Autowired
    private RankingInfoDao repository;

    @Autowired
    private ConversionService rankDaoToDtoConverter;

    @Autowired
    private ConversionService rankDtoToDaoConverter;

    @Override
    public Mono<ServerResponse> fetchRankingInfoById(ServerRequest request) {
        String id = request.pathVariable("id");

        return repository.findById(id)
                .map(daoObj -> rankDaoToDtoConverter.convert(daoObj, IccRankDTO.class))
                .flatMap(ServerResponse.ok() :: bodyValue);
    }

    @Override
    public Mono<ServerResponse> fetchRankingInfoByPlayerId(ServerRequest request) {
        var playerId = request.pathVariable("player-id");
        return repository.findByPlayerId(playerId)
                .map(daoObj -> rankDaoToDtoConverter.convert(daoObj, IccRankDTO.class))
                .flatMap(ServerResponse.ok() :: bodyValue);
    }

    @Override
    public Mono<ServerResponse> createRankingInfo(ServerRequest request) {
        Mono<IccRankDTO> dtoMono = request.bodyToMono(IccRankDTO.class);

        return dtoMono
                .map(dto -> rankDtoToDaoConverter.convert(dto, IccRanking.class))
                .flatMap(repository:: save)
                .map(savedObj -> rankDaoToDtoConverter.convert(savedObj, IccRankDTO.class))
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
    }
}
