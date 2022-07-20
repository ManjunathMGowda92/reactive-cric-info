package org.fourstack.reactivecricinfo.apigateway.client;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
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

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.*;

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
        return getPlayerInfoDTOFlux(uriProperties.getPlayerByLastNameURL(), lastName);
    }

    /**
     * It is a common method which fetches the Flux of Players using url and corresponding
     * uri variable.
     *
     * @param url         Target URL
     * @param uriVariable Path variable required for the URL
     * @return Flux of PlayerInfoDTO objects.
     */
    private Flux<PlayerInfoDTO> getPlayerInfoDTOFlux(String url, String uriVariable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(url)
                        .build(uriVariable))
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

    /**
     * Method which retrieves the Players Info using the WebClient and firstname
     * variable. This is an external API call which makes connection with
     * player-info-service to fetch Flux of PlayerInfo objects.
     * <br/>
     * To fetch Flux of PlayerInfo objects, WebClient uses URL from yml file
     * with property name: <b><i>{app.player-service.url.player-by-firstname}</i></b>
     *
     * @param firstname Firstname property value
     * @return Flux of PlayerInfoDTO objects.
     */
    public Flux<PlayerInfoDTO> retrievePlayersByFirstname(String firstname) {
        return getPlayerInfoDTOFlux(uriProperties.getPlayerByFirstNameURL(), firstname);
    }

    /**
     * Method which retrieves the Players Info using the WebClient and country
     * variable. This is an external API call which makes connection with
     * player-info-service to fetch Flux of PlayerInfo objects.
     * <br/>
     * To fetch Flux of PlayerInfo objects, WebClient uses URL from yml file
     * with property name: <b><i>{app.player-service.url.player-by-country}</i></b>
     *
     * @param country Country name property value.
     * @return Flux of PlayerInfoDTO objects.
     */
    public Flux<PlayerInfoDTO> retrievePlayersByCountry(String country) {
        return getPlayerInfoDTOFlux(uriProperties.getPlayerByCountryURL(), country);
    }

    /**
     * Method which retrieves the Players Info using the WebClient and Gender
     * variable. This is an external API call which makes connection with
     * player-info-service to fetch Flux of PlayerInfo objects.
     * <br/>
     * To fetch Flux of PlayerInfo objects, WebClient uses URL from yml file
     * with property name: <b><i>{app.player-service.url.player-by-gender}</i></b>
     *
     * @param gender Gender property value.
     * @return Flux of PlayerInfoDTO objects.
     */
    public Flux<PlayerInfoDTO> retrievePlayersByGender(String gender) {
        return getPlayerInfoDTOFlux(uriProperties.getPlayerByGenderURL(), gender);
    }

    /**
     * Method which retrieves the Players Info using the WebClient and Batting Style
     * variable. This is an external API call which makes connection with
     * player-info-service to fetch Flux of PlayerInfo objects.
     * <br/>
     * To fetch Flux of PlayerInfo objects, WebClient uses URL from yml file
     * with property name: <b><i>{app.player-service.url.player-by-batting-style}</i></b>
     *
     * @param battingStyle Batting Style property value.
     * @return Flux of PlayerInfoDTO objects.
     */
    public Flux<PlayerInfoDTO> retrievePlayersByBattingStyle(String battingStyle) {
        return getPlayerInfoDTOFlux(uriProperties.getPlayerByBattingStyleURL(), battingStyle);
    }

    /**
     * Method which retrieves the Players Info using the WebClient and Bowling Style
     * variable. This is an external API call which makes connection with
     * player-info-service to fetch Flux of PlayerInfo objects.
     * <br/>
     * To fetch Flux of PlayerInfo objects, WebClient uses URL from yml file with
     * property name: <b><i>{app.player-service.url.player-by-bowling-style}</i></b>
     *
     * @param bowlingStyle Bowling Style property value.
     * @return Flux of PlayerInfoDTO objects.
     */
    public Flux<PlayerInfoDTO> retrievePlayersByBowlingStyle(String bowlingStyle) {
        return getPlayerInfoDTOFlux(uriProperties.getPlayerByBowlingStyleURL(), bowlingStyle);
    }

    /**
     * Method which is used to create PlayerInfo using the WebClient and {@link PlayerInfoDTO}
     * object. This is an external API call which makes connection with
     * player-info-service to create the PlayerInfo object.
     * <br/>
     * To create PlayerInfo object, WebClient uses URL from yml file with
     * property name: <b><i>{app.player-service.url.create-player-info}</i></b>
     *
     * @param dto {@link PlayerInfoDTO} object.
     * @return PlayerInfoDTO object with playerId.
     */
    public Mono<PlayerInfoDTO> createPlayerInfo(PlayerInfoDTO dto) {
        return webClient.post()
                .uri(uriProperties.getCreatePlayerURL())
                .header(CONTENT_TYPE.toString(), APPLICATION_JSON.toString())
                .body(Mono.just(dto), PlayerInfoDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .map(PlayerClientException::new)
                ).onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .map(PlayerServiceException::new)
                ).bodyToMono(PlayerInfoDTO.class);
    }
}
