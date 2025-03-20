package io.github.scitia.susieserver.security.config;

import io.github.scitia.security.dictionary.ClientRole;
import io.github.scitia.security.dictionary.KeycloakDictionary;
import io.github.scitia.security.service.AuthInterface;
import io.github.scitia.security.service.AuthInterfaceImpl;
import io.github.scitia.security.shared.identifier.Email;
import io.github.scitia.security.shared.identifier.UUID;
import io.github.scitia.security.shared.role.ExtendPermissionRequest;
import io.github.scitia.security.shared.token.AccessTokenExtendedResponse;
import io.github.scitia.security.shared.token.RefreshTokenResponse;
import io.github.scitia.security.shared.user.AccountDeletionResult;
import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.UserCredentials;
import io.github.scitia.security.shared.user.UserDetails;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class KeycloakConnector {
    private final AuthInterface authInterface;

    public KeycloakConnector(KeycloakConfig configuration) {
        this.authInterface = AuthInterfaceImpl.builder()
                .keycloakInstance(configuration.getInstance())
                .configuration(configuration.getConfiguration())
                .realmDetails(configuration.realmDetails())
                .build();
    }

    public SignUpResponse signUp(UserDetails userDetails) {
        return authInterface.signUp(userDetails);
    }

    public AccessTokenExtendedResponse signIn(UserCredentials credentials) {
        return authInterface.signIn(credentials);
    }

    public AccountDeletionResult deleteAccount(UUID uuid) {
        return authInterface.deleteAccount(uuid);
    }

    public RefreshTokenResponse refreshToken(String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException {
        return authInterface.refreshToken(refreshToken);
    }

    public UserRepresentation getUserRepresentation(Email email) {
        return authInterface.getUserRepresentation(email);
    }

    public UserRepresentation getUserRepresentation(UUID uuid) {
        return authInterface.getUserRepresentation(uuid);
    }

    public boolean isUserExist(UUID uuid) {
        return authInterface.isUserExists(uuid);
    }

    public void grantPermission(ExtendPermissionRequest request, Set<String> usersIDs) {
        UUID uuid = new UUID(request.getUserUUID());
        uuid.setValue(request.getUserUUID());
        ClientRole role = getClientRoleByName(request.getRoleName());
        Set<UUID> usersUUIDs = new HashSet<>();
        usersIDs.forEach(id -> usersUUIDs.add(new UUID(id)));
        authInterface.grantPermission(uuid, role, usersUUIDs);
    }

    public void revokePermission(ExtendPermissionRequest request) {
        UUID uuid = new UUID(request.getUserUUID());
        ClientRole role = getClientRoleByName(request.getRoleName());
        authInterface.revokePermission(uuid, role);
    }

    private ClientRole getClientRoleByName(String roleName) {
        return switch (roleName) {
            case KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_DEVELOPER -> ClientRole.DEV;
            case KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_PRODUCT_OWNER -> ClientRole.PO;
            case KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_SCRUM_MASTER -> ClientRole.SM;
            default -> null;
        };
    }
}
