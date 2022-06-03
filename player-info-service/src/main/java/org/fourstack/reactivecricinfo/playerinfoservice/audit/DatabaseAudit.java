package org.fourstack.reactivecricinfo.playerinfoservice.audit;

import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * DatabaseAudit class, which is used for auditing the insertion and update
 * of each record in the MongoDB. For Reactive Web {@link ReactiveAuditorAware}
 * interface is used for implementation.
 * <br/>
 * <p> Database model (Entity) class will have the @CreatedDate
 * , @LastModifiedDate, @LastModifiedBy and @CreatedBy annotations,
 * which are auto updated by the framework itself.</p>
 * This class helps in providing the username responsible for creation and update.
 * Can be advanced to fetch from the Authentication details.
 */
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
