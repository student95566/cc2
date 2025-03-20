package io.github.scitia.security.shared.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResult {

    private String result;
    private Boolean success;
}
