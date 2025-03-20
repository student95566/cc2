package io.github.scitia.security.service;

import dev.mccue.guava.base.Joiner;
import io.github.scitia.security.builder.UserRepresentationBuilder;
import io.github.scitia.security.dictionary.ClientRole;
import io.github.scitia.security.dictionary.KeycloakDictionary;
import io.github.scitia.security.shared.identifier.Email;
import io.github.scitia.security.shared.identifier.UUID;
import io.github.scitia.security.shared.token.AccessTokenExtendedResponse;
import io.github.scitia.security.shared.user.AccountDeletionResult;
import io.github.scitia.security.shared.realm.RealmDetails;
import io.github.scitia.security.shared.token.RefreshTokenResponse;
import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.SignUpResult;
import io.github.scitia.security.shared.role.SimpleRoleRepresentation;
import io.github.scitia.security.shared.user.UserCredentials;
import io.github.scitia.security.shared.user.UserDetails;
import io.github.scitia.security.utils.HttpCustomClient;
import jakarta.ws.rs.core.Response;
import lombok.Builder;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static io.github.scitia.security.dictionary.KeycloakDictionary.ACCESS_TOKEN_EXPIRES_IN_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.ACCESS_TOKEN_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.CLIENT_ID_REQUEST_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.CLIENT_SECRET_REQUEST_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.GRANT_TYPE_REQUEST_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_PRODUCT_OWNER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.KEYCLOAK_CLIENT_ROLE_SCRUM_MASTER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.KEYCLOAK_USER_ROLE_ALREADY_ASSIGNED;
import static io.github.scitia.security.dictionary.KeycloakDictionary.PO_PERMISSION_ALREADY_EXISTS;
import static io.github.scitia.security.dictionary.KeycloakDictionary.REFRESH_TOKEN_EXPIRES_IN_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.REFRESH_TOKEN_REQUEST_PARAMETER;
import static io.github.scitia.security.dictionary.KeycloakDictionary.SM_PERMISSION_ADDED_WARNING;
import static io.github.scitia.security.dictionary.KeycloakDictionary.USER_DELETION_INFO;
import static io.github.scitia.security.dictionary.KeycloakDictionary.USER_SEARCH_FAILED_INFO;

@Builder
public class AuthInterfaceImpl implements AuthInterface {

    private final Keycloak keycloakInstance;
    private final Configuration configuration;
    private final RealmDetails realmDetails;

    public AuthInterfaceImpl(Keycloak keycloakInstance, Configuration configuration, RealmDetails realmDetails) {
        this.keycloakInstance = keycloakInstance;
        this.configuration = configuration;
        this.realmDetails = realmDetails;
    }

    @Override
    public SignUpResponse signUp(UserDetails userDetails) {
        UserRepresentation userRepresentation = UserRepresentationBuilder.builder()
                .username(userDetails.getUsername())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .password(userDetails.getPassword())
                .email(userDetails.getUsername())
                .emailVerified(Boolean.TRUE)
                .enabled(Boolean.TRUE)
                .build();

        Response response = keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .create(userRepresentation);

        SignUpResult info = SignUpResult.builder().result(response.getStatusInfo().getReasonPhrase()).success(Boolean.FALSE).build();
        String userUUID = getUserUUIDFromResponse(response.getLocation().toString());

        if (response.getStatus() == HttpStatus.SC_CREATED) {
            info.setSuccess(Boolean.TRUE);
            ClientRole role = userDetails.getExtendedPrivileges() ? ClientRole.SM : ClientRole.DEV;
            assignRoleToUser(userUUID, role);
        }

        return new SignUpResponse(response.getStatus(), info);
    }

    @Override
    public AccessTokenExtendedResponse signIn(UserCredentials credentials) {
        AccessTokenResponse tokenInfo = AuthzClient
                .create(configuration)
                .obtainAccessToken(credentials.getUsername(), credentials.getPassword());

        AccessTokenExtendedResponse response = new AccessTokenExtendedResponse(tokenInfo);

        UserRepresentation userRepresentation = keycloakInstance
                .realm(realmDetails.getRealmName()).users()
                .searchByUsername(credentials.getUsername(), Boolean.TRUE)
                .get(0);

        List<SimpleRoleRepresentation> userRoles = keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .get(userRepresentation.getId())
                .roles().clientLevel(getIDForClientID(realmDetails.getClientId()))
                .listAll().stream().map(role -> SimpleRoleRepresentation.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build())
                .toList();

        response.setUserRoles(userRoles);
        return response;
    }

    @Override
    public RefreshTokenResponse refreshToken(String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException {
        return getShortRefreshAccessTokenResponse(refreshToken);
    }

    @Override
    public AccountDeletionResult deleteAccount(UUID uuid) {

        Response response = keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .delete(uuid.getValue());

        return AccountDeletionResult.builder()
                .result(USER_DELETION_INFO)
                .internalStatus(response.getStatus())
                .reasonPhrase(response.getStatusInfo().getReasonPhrase())
                .success(Boolean.TRUE)
                .build();
    }

    @Override
    public UserRepresentation getUserRepresentation(Email email) {

        List<UserRepresentation> resultSet = keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .searchByUsername(email.getValue(), Boolean.TRUE);

        if (resultSet.isEmpty()) {
            return null;
        } else if (Integer.valueOf(1).equals(resultSet.size())) {
            return resultSet.get(0);
        } else {
            throw new RuntimeException(USER_SEARCH_FAILED_INFO);
        }
    }

    @Override
    public UserRepresentation getUserRepresentation(UUID uuid) {

        return keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .get(uuid.getValue())
                .toRepresentation();
    }

    @Override
    public boolean isUserExists(UUID uuid) {

        if (Objects.isNull(uuid)) {
            throw new RuntimeException();
        }

        return keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .list()
                .stream()
                .anyMatch(account -> uuid.getValue().equals(account.getId()));
    }

    @Override
    public void grantPermission(UUID uuid, ClientRole clientRole, Set<UUID> usersUUIDs) {

        if (KEYCLOAK_CLIENT_ROLE_SCRUM_MASTER.equals(clientRole.getClientRoleName())) {
            throw new RuntimeException(SM_PERMISSION_ADDED_WARNING);
        }

        if (KEYCLOAK_CLIENT_ROLE_PRODUCT_OWNER.equals(clientRole.getClientRoleName()) && isExistAnyProductOwnerInProject(usersUUIDs)) {
            throw new RuntimeException(PO_PERMISSION_ALREADY_EXISTS);
        }

        List<SimpleRoleRepresentation> alreadyAssignedUserRoles = getUserRolesByUserUUID(uuid);

        alreadyAssignedUserRoles.forEach(role -> {
            if (role.getName().equals(clientRole.getClientRoleName())) {
                throw new RuntimeException(KEYCLOAK_USER_ROLE_ALREADY_ASSIGNED + clientRole);
            }
        });

        assignRoleToUser(uuid.getValue(), clientRole);
    }

    @Override
    public void revokePermission(UUID uuid, ClientRole clientRole) {
        keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .get(uuid.getValue())
                .roles()
                .clientLevel(getIDForClientID(realmDetails.getClientId()))
                .remove(getClientRolesByName(List.of(clientRole)));
    }

    private Map<String, String> getRefreshAccessTokenResponse(String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException {
        Map<String, String> httpParameters = new HashMap<>();
        HttpCustomClient client = new HttpCustomClient();
        httpParameters.put(GRANT_TYPE_REQUEST_PARAMETER, REFRESH_TOKEN_REQUEST_PARAMETER);
        httpParameters.put(CLIENT_SECRET_REQUEST_PARAMETER, realmDetails.getClientSecret());
        httpParameters.put(REFRESH_TOKEN_REQUEST_PARAMETER, refreshToken);
        httpParameters.put(CLIENT_ID_REQUEST_PARAMETER, realmDetails.getClientId());
        String URL = realmDetails.getTokenEndpoint();
        return client.getResponse(URL, httpParameters);
    }

    private RefreshTokenResponse getShortRefreshAccessTokenResponse(String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException {

        Map<String, String> fullResponse = getRefreshAccessTokenResponse(refreshToken);

        return RefreshTokenResponse.builder()
                .token(fullResponse.get(ACCESS_TOKEN_PARAMETER))
                .expiresIn(Integer.valueOf(fullResponse.get(ACCESS_TOKEN_EXPIRES_IN_PARAMETER)).longValue())
                .refreshToken(fullResponse.get(REFRESH_TOKEN_REQUEST_PARAMETER))
                .refreshExpiresIn(Integer.valueOf(fullResponse.get(REFRESH_TOKEN_EXPIRES_IN_PARAMETER)).longValue())
                .build();
    }

    private void assignRoleToUser(String userUUID, ClientRole role) {
        this.keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .get(userUUID)
                .roles()
                .clientLevel(getIDForClientID(realmDetails.getClientId()))
                .add(getClientRolesByName(List.of(role)));
    }

    private boolean isExistAnyProductOwnerInProject(Set<UUID> userUUIDs) {

        return userUUIDs.stream()
                .map(this::getUserRolesByUserUUID)
                .flatMap(Collection::stream)
                .anyMatch(srr -> KEYCLOAK_CLIENT_ROLE_PRODUCT_OWNER.equals(srr.getName()));
    }

    private List<SimpleRoleRepresentation> getUserRolesByUserUUID(UUID uuid) {
        return keycloakInstance
                .realm(realmDetails.getRealmName())
                .users()
                .get(uuid.getValue())
                .roles().clientLevel(getIDForClientID(realmDetails.getClientId()))
                .listAll().stream().map(role -> SimpleRoleRepresentation.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build())
                .toList();
    }

    private List<RoleRepresentation> getClientRolesByName(List<ClientRole> clientRoles) {
        String entityID = getIDForClientID(realmDetails.getClientId());
        RealmResource realmResource = keycloakInstance.realm(realmDetails.getRealmName());
        List<RoleRepresentation> roles = new ArrayList<>();
        clientRoles.forEach(
                role -> roles.add(Optional.of(realmResource.clients().get(entityID).roles().get(role.getClientRoleName()).toRepresentation())
                .orElseThrow(RuntimeException::new)));
        return roles;
    }

    private String getIDForClientID(String clientID) {
        return Optional.of(
                keycloakInstance
                        .realm(realmDetails.getRealmName())
                        .clients().findByClientId(clientID)
                        .get(0)
                        .getId()
        ).orElseThrow(RuntimeException::new);
    }

    private String getUserUUIDFromResponse(String location) {
        return location.replaceAll(buildLocationURI(), KeycloakDictionary.EMPTY_WORD);
    }

    private String buildLocationURI() {
        return Joiner
                .on(KeycloakDictionary.URI_SEPARATOR)
                .skipNulls()
                .join(realmDetails.getServerUrl(),
                        KeycloakDictionary.KEYCLOAK_URI_ADMIN_PARAMETER,
                        KeycloakDictionary.KEYCLOAK_URI_REALMS_PARAMETER,
                        realmDetails.getRealmName(),
                        KeycloakDictionary.KEYCLOAK_URI_USERS_PARAMETER)
                .concat(KeycloakDictionary.URI_SEPARATOR);
    }
}
