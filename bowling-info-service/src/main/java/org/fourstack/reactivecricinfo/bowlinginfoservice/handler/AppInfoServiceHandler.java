package org.fourstack.reactivecricinfo.bowlinginfoservice.handler;

import org.fourstack.reactivecricinfo.bowlinginfoservice.codetype.MethodType;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.AppInfo;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.EndPointsDesc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AppInfoServiceHandler {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.description}")
    private String appDescription;

    public Mono<ServerResponse> getAppInfo(ServerRequest request) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(appName);
        appInfo.setAppVersion(appVersion);
        appInfo.setAppDescription(appDescription);

        appInfo.setEndpoints(getEndpointsDetails());
        return ServerResponse.ok()
                .bodyValue(appInfo);
    }

    private List<EndPointsDesc> getEndpointsDetails() {
        return List.of(
                new EndPointsDesc(MethodType.GET, "/api/v1/info", "Basic Information about the Application"),
                new EndPointsDesc(MethodType.GET, "/api/v1/bowling-statistics/{player-id}", "API to fetch Bowling statistics by Player Id")
        );
    }
}
