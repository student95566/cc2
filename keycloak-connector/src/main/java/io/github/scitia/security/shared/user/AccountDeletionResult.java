package io.github.scitia.security.shared.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDeletionResult {

    private String result;
    private Integer internalStatus;
    private String reasonPhrase;
    private Boolean success;
}
