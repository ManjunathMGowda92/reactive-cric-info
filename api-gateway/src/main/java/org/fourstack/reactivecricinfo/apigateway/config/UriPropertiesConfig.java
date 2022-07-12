package org.fourstack.reactivecricinfo.apigateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UriPropertiesConfig {

    @Value("${app.player-service.url.player-by-id}")
    private String playerByIdURL;

    @Value("${app.player-service.url.player-by-lastname}")
    private String playerByLastNameURL;

    @Value("${app.player-service.url.player-by-firstname}")
    private String playerByFirstNameURL;

    @Value("${app.player-service.url.player-by-bowling-style}")
    private String playerByBowlingStyleURL;

    @Value("${app.player-service.url.player-by-batting-style}")
    private String playerByBattingStyleURL;

    @Value("${app.player-service.url.player-by-gender}")
    private String playerByGenderURL;

    @Value("${app.player-service.url.player-by-country}")
    private String playerByCountryURL;

    @Value("${app.player-service.url.player-exist-or-not}")
    private String playerExistOrNotURL;

    @Value("${app.batting-service.url.create-batting-info}")
    private String createBattingURL;

    @Value("{app.batting-service.url.batting-info-by-id}")
    private String battingInfoByIdURL;

    @Value("{app.batting-service.url.batting-info-by-player-id}")
    private String battingInfoByPlayerIdURL;

    @Value("{app.bowling-service.url.create-bowling-info}")
    private String createBowlingURL;

    @Value("{app.bowling-service.url.bowling-info-by-id}")
    private String bowlingInfoByIdURL;

    @Value("{app.bowling-service.url.bowling-info-by-player-id}")
    private String bowlingInfoByPlayerIdURL;

    @Value("{app.ranking-service.url.create-ranking-info}")
    private String createRankingURL;

    @Value("{app.ranking-service.url.ranking-info-by-id}")
    private String rankingInfoByIdURL;

    @Value("{app.ranking-service.url.ranking-info-by-player-id}")
    private String rankingInfoByPlayerIdURL;

}
