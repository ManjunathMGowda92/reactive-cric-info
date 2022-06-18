package org.fourstack.reactivecricinfo.bowlinginfoservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dao.BowlingInfoDao;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.exception.BowlingDataNotFoundException;
import org.fourstack.reactivecricinfo.bowlinginfoservice.exception.BowlingServiceException;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
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
                .onErrorResume(err -> {
                            log.error("Error in BowlingServiceHandlerImpl.fetchBowlingInfoByPlayerId() - {}",
                                    err.getMessage(), err);
                            return Mono.error(new BowlingServiceException(err.getMessage(), err.getCause()));
                        }
                ).switchIfEmpty(generateError("No BowlingInfo found for the PlayerId: ", playerId))
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<BowlingInfo> generateError(String message, String concatStr) {
        log.info("Creating BowlingDataNotFoundException .......");
        return Mono.error(new BowlingDataNotFoundException(message + concatStr));
    }

    @Override
    public Mono<ServerResponse> fetchBowlingInfoById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .onErrorResume(err -> {
                            log.error("Error in BowlingServiceHandlerImpl.fetchBowlingInfoById() - {}",
                                    err.getMessage(), err);
                            return Mono.error(new BowlingServiceException(err.getMessage(), err.getCause()));
                        }
                ).switchIfEmpty(generateError("No BowlingInfo found for the Id: ", id))
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    @Override
    public Mono<ServerResponse> createBowlingInfo(ServerRequest request) {
        Mono<BowlingInfoDTO> dtoMono = request.bodyToMono(BowlingInfoDTO.class);

        return dtoMono
                .map(dto -> dtoToDaoConverter.convert(dto, BowlingInfo.class))
                .flatMap(repository::save)
                .map(dao -> daoToDtoConverter.convert(dao, BowlingInfoDTO.class))
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue)
                .onErrorResume(err -> {
                    log.error("Error in BowlingServiceHandlerImpl.createBowlingInfo() - {}",
                            err.getMessage(), err);
                    return Mono.error(new BowlingServiceException(err.getMessage(), err.getCause()));
                });
    }
}
