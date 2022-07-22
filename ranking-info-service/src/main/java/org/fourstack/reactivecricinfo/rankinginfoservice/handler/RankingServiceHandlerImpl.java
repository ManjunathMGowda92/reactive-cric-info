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

/**
 * Handler class which is responsible to handle the different services.
 * Router class will use the Handler class methods to fulfill the
 * API request calls.
 *
 * @author manjunath
 */
@Service
@Slf4j
public class RankingServiceHandlerImpl implements RankingServiceHandler {

    @Autowired
    private RankingInfoDao repository;

    @Autowired
    private ConversionService rankDaoToDtoConverter;

    @Autowired
    private ConversionService rankDtoToDaoConverter;

    /**
     * Method which is responsible to fetch the {@link IccRankDTO} using the
     * RankingId.
     *
     * @param request ServerRequest object with rankingId as path variable.
     * @return ServerResponse object wrapped with IccRankDTO object.
     */
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

    /**
     * Method responsible to create Error. Accepts different arguments and converts it into Error.
     *
     * @param errorMsg  Error Message value.
     * @param concatStr Extract String if needed for errorMsg concatenation.
     * @param exception Throwable object instance.
     * @param method    Name of the method from where the method is called.
     * @return
     */
    private Mono<IccRanking> createError(String errorMsg, String concatStr, Throwable exception, String method) {
        log.error("RankingServiceHandlerImpl: Exception in method -> {} >> {}", method, errorMsg);
        if (Objects.nonNull(exception) && exception instanceof RankingServiceException) {
            return Mono.error(new RankingServiceException(exception.getMessage(), exception.getCause()));
        }
        return Mono.error(new RankingInfoNotFoundException(errorMsg.concat(concatStr)));
    }

    /**
     * Method responsible to fetch the {@link IccRankDTO} object using the
     * playerId.
     *
     * @param request ServerRequest object with playerId path variable.
     * @return ServerResponse wrapped with IccRankDTO object.
     */
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

    /**
     * Method responsible to create RankingInfo object. Accepts {@link IccRankDTO} object
     * which is wrapped in ServerRequest object. Then converts it into
     * {@link IccRanking} object and saves it to Database. Once after
     * saving again converts back to {@link IccRankDTO} and sends back
     * to the calling API method.
     *
     * @param request ServerRequest object which wraps {@link IccRankDTO} object.
     * @return ServerResponse object wrapped with IccRankDTO object.
     */
    @Override
    public Mono<ServerResponse> createRankingInfo(ServerRequest request) {
        Mono<IccRankDTO> dtoMono = request.bodyToMono(IccRankDTO.class);

        return dtoMono
                .map(dto -> rankDtoToDaoConverter.convert(dto, IccRanking.class))
                .flatMap(repository::save)
                .map(savedObj -> rankDaoToDtoConverter.convert(savedObj, IccRankDTO.class))
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue)
                /*.onErrorResume(
                        err -> {
                            log.error("RankingServiceHandler: Exception -> {}", err.getMessage());
                            return Mono.error(new RankingServiceException(
                                    "Exception while creating the RankingInfo :".concat(err.getMessage()), err));
                        }
                )*/;
    }
}
