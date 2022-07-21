package org.fourstack.reactivecricinfo.rankinginfoservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.rankinginfoservice.dao.RankingInfoDao;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling.RankingInfoNotFoundException;
import org.fourstack.reactivecricinfo.rankinginfoservice.exceptionhandling.RankingServiceException;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.IccRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class RankingServiceHandlerImpl implements RankingServiceHandler {

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
                .onErrorResume(
                        err -> createError(err.getMessage(), "", err, "fetchRankingInfoById()")
                ).switchIfEmpty(
                        createError(
                                "No RankingInfo found for the RankingId: ", id,
                                null, "fetchRankingInfoById()"
                        )
                ).map(daoObj -> rankDaoToDtoConverter.convert(daoObj, IccRankDTO.class))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<IccRanking> createError(String errorMsg, String concatStr, Throwable exception, String method) {
        log.error("RankingServiceHandlerImpl: Exception in method -> {} >> {}", method, errorMsg);
        if (Objects.nonNull(exception) && exception instanceof RankingServiceException) {
            return Mono.error(new RankingServiceException(exception.getMessage(), exception.getCause()));
        }
        return Mono.error(new RankingInfoNotFoundException(errorMsg.concat(concatStr)));
    }

    @Override
    public Mono<ServerResponse> fetchRankingInfoByPlayerId(ServerRequest request) {
        var playerId = request.pathVariable("player-id");
        return repository.findByPlayerId(playerId)
                .onErrorResume(
                        err -> createError(err.getMessage(), "", err, "fetchRankingInfoByPlayerId()")
                ).switchIfEmpty(
                        createError(
                                "No RankingInfo found for PlayerId: ", playerId,
                                null, "fetchRankingInfoByPlayerId()"
                        )
                ).map(daoObj -> rankDaoToDtoConverter.convert(daoObj, IccRankDTO.class))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Override
    public Mono<ServerResponse> createRankingInfo(ServerRequest request) {
        Mono<IccRankDTO> dtoMono = request.bodyToMono(IccRankDTO.class);
        return dtoMono
                .map(dto -> rankDtoToDaoConverter.convert(dto, IccRanking.class))
                .flatMap(repository::save)
                .map(savedObj -> rankDaoToDtoConverter.convert(savedObj, IccRankDTO.class))
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue)
                .onErrorResume(
                        err -> Mono.error(new RankingServiceException("Exception while creating the RankingInfo", err))
                );
    }
}
