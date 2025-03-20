package io.github.scitia.security.shared.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetails extends UserCredentials {

    private String firstName;
    private String lastName;
    private Boolean extendedPrivileges;
}
