package io.github.scitia.susieserver.security.config;

import io.github.scitia.security.shared.realm.RealmDetails;
import lombok.Getter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static io.github.scitia.security.dictionary.KeycloakDictionary.KEYCLOAK_URI_SECRET_PARAMETER;

@Getter
@Component
@PropertySource("classpath:application.properties")
public class KeycloakConfig {

    @Value("${auth-server.client.secret}")
    private String clientSecret;
    @Value("${auth-server.client.server-url}")
    private String serverUrl;
    @Value("${auth-server.client.realm-name}")
    private String realm;
    @Value("${auth-server.client.clientId}")
    private String clientId;
    @Value("${auth-server.token-endpoint}")
    private String tokenEndpoint;

    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(ResteasyClientBuilder.newClient())
                .build();
    }

    public Configuration getConfiguration() {
        HashMap<String, Object> credentials = HashMap.newHashMap(1);
        credentials.put(KEYCLOAK_URI_SECRET_PARAMETER, clientSecret);
        return new Configuration(serverUrl, realm, clientId, credentials, null);
    }

    public RealmDetails realmDetails() {
        return RealmDetails.builder()
                .serverUrl(serverUrl)
                .realmName(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tokenEndpoint(tokenEndpoint)
                .build();
    }
}
