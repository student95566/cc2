package io.github.scitia.security.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientRole {

    SM("sm"),
    DEV("dev"),
    PO("po"),
    PLAIN("client_user"),
    ADMIN("client_admin");

    private final String clientRoleName;
}
