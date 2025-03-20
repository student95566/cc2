// Lab 1 // Factory Method

package io.github.scitia.susieserver.user.factory;

import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.UserDetails;
import io.github.scitia.susieserver.security.config.KeycloakConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakUserFactory implements UserFactory {

    private final KeycloakConnector keycloakConnector;

    @Override
    public SignUpResponse createUser(UserDetails userDetails) {
        return keycloakConnector.signUp(userDetails);
    }

    public SignUpResponse createDevUser(UserDetails userDetails) {
        userDetails.setExtendedPrivileges(false);
        return createUser(userDetails);
    }

    public SignUpResponse createScrumMasterUser(UserDetails userDetails) {
        userDetails.setExtendedPrivileges(true);
        return createUser(userDetails);
    }
}