// Lab 1 // Factory Method

package io.github.scitia.susieserver.user.factory;

import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.UserDetails;

public interface UserFactory {
    SignUpResponse createUser(UserDetails userDetails);
}