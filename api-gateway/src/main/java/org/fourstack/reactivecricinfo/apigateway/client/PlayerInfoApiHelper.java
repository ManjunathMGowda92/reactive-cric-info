package org.fourstack.reactivecricinfo.apigateway.client;

import lombok.extern.slf4j.Slf4j;
import org.fourstack.reactivecricinfo.apigateway.config.UriPropertiesConfig;
import org.fourstack.reactivecricinfo.apigateway.dto.ErrorResponse;
import org.fourstack.reactivecricinfo.apigateway.dto.PlayerInfoDTO;
import org.fourstack.reactivecricinfo.apigateway.exceptionhandling.PlayerClientException;
import org.fourstack.reactivecricinfo.apigateway.exceptionhandling.PlayerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * External API Helper class which makes connection with
 * <b><i>player-info-service</i></b> and fetches results back.
 *
 * @author manjunath
 */
@Component
@Slf4j
public class PlayerInfoApiHelper {

    @Autowired
    private WebClient webClient;

    @Autowired
    private UriPropertiesConfig uriProperties;

    /**
     * Method which retrieves the PlayerInfo using the WebClient. This
     * is an external API call which makes connection with the player-info-service
     * to fetch the PlayerInfo.
     * <br/>
     * To fetch PlayerInfo, WebClient uses URL String from yml file with
     * property name : <b><i>{app.player-service.url.player-by-id}</i></b>
     *
     * @param id Player ID of a Player.
     * @return PlayerInfoDTO with Mono Container.
     */
    public Mono<PlayerInfoDTO> retrievePlayerInfoById(String id) {
        return webClient.get()
                .uri(uriProperties.getPlayerByIdURL(), id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(),
                        clientResponse -> {
                            return clientResponse.bodyToMono(ErrorResponse.class)
                                    .map(PlayerClientException::new);
                        }
                )
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .map(PlayerServiceException::new)
                )
                .bodyToMono(PlayerInfoDTO.class);
        // .retryWhen()
    }

    /**
     * Method which retrieves the Players Info using the WebClient. This
     * is an external API call which makes connection with player-info-service
     * to fetch Flux of PlayerInfo objects.
     * <br/>
     * To fetch Flux of PlayerInfo objects, WebClient uses URL from yml file
     * with property name: <b><i>{app.player-service.url.player-by-lastname}</i></b>
     *
     * @param lastName Lastname property value
     * @return Flux of PlayerInfoDTO objects.
     */
    public Flux<PlayerInfoDTO> retrievePlayersByLastname(String lastName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(uriProperties.getPlayerByLastNameURL())
                        .build(lastName))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .map(PlayerClientException::new)
                )
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .map(PlayerServiceException::new)
                )
                .bodyToFlux(PlayerInfoDTO.class);
    }
}
