package org.fourstack.reactivecricinfo.bowlinginfoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;

@Configuration
public class AppConfiguration {

    @Bean
    public ReactiveAuditorAware<String> currentAuditor() {
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
