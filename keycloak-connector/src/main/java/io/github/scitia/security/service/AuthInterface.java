package io.github.scitia.security.service;

import io.github.scitia.security.dictionary.ClientRole;
import io.github.scitia.security.shared.identifier.Email;
import io.github.scitia.security.shared.identifier.UUID;
import io.github.scitia.security.shared.token.AccessTokenExtendedResponse;
import io.github.scitia.security.shared.token.RefreshTokenResponse;
import io.github.scitia.security.shared.user.AccountDeletionResult;
import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.UserCredentials;
import io.github.scitia.security.shared.user.UserDetails;
import org.keycloak.representations.idm.UserRepresentation;

import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface AuthInterface {

    SignUpResponse signUp(UserDetails userDetails);
    AccessTokenExtendedResponse signIn(UserCredentials credentials);
    AccountDeletionResult deleteAccount(UUID uuid);
    RefreshTokenResponse refreshToken(String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException;
    UserRepresentation getUserRepresentation(Email email);
    UserRepresentation getUserRepresentation(UUID uuid);
    boolean isUserExists(UUID uuid);
    void grantPermission(UUID uuid, ClientRole role, Set<UUID> usersUUIDs);
    void revokePermission(UUID uuid, ClientRole role);
}
