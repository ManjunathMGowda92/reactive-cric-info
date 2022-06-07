package org.fourstack.reactivecricinfo.battinginfoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;

@Configuration
public class BattingServiceConfig {

    @Bean
    public ReactiveAuditorAware<String> currentUser() {
        /*Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String user = "system";
        if (authentication != null) {
            uname = authentication
                    .getName();
        }*/
        String user = "SYSTEM";
        return () -> Mono.just(user);
    }
}
