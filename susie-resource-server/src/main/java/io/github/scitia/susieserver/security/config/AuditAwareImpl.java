package io.github.scitia.susieserver.security.config;

import io.github.scitia.susieserver.user.service.UserService;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Zanim klasa zyskałą charakter wzorca Singleton
 * było to zrealizowane poprzez zastosowanie adnotacji
 * @Component z biblioteki frameworka Spring, dodatkowo
 * adnotacja @RequiredArgsConstructor zapewniała odpowiedni
 * konstruktor dla serwisu userService
 */

//@Comment
//@RequiredArgsConstructor
public final class AuditAwareImpl implements AuditorAware<String> {

    private static AuditAwareImpl instance;

    private final UserService userService;


    private AuditAwareImpl(UserService userService) {
        this.userService = userService;
    }

    public static AuditAwareImpl getInstance(UserService userService) {
        if (instance == null) {
            instance = new AuditAwareImpl(userService);
        }
        return instance;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(userService.getCurrentLoggedUser().getUuid());
    }
}
