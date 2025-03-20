package io.github.scitia.security.shared.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {

    private Integer status;
    private SignUpResult signUpResult;
}
