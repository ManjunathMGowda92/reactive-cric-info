package org.fourstack.reactivecricinfo.playerinfoservice.audit;

import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DatabaseAudit implements ReactiveAuditorAware<String> {
    @Override
    public Mono<String> getCurrentAuditor() {
        /*Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String user = "system";
        if (authentication != null) {
            uname = authentication
                    .getName();
        }*/

        String user = "SYSTEM";
        return Mono.just(user);
    }
}
