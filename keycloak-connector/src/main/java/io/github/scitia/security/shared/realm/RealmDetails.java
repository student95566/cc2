package io.github.scitia.security.shared.realm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RealmDetails {

    private String clientId;
    private String clientSecret;
    private String serverUrl;
    private String realmName;
    private String tokenEndpoint;
}
