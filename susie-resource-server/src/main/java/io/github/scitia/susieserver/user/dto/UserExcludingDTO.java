package io.github.scitia.susieserver.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserExcludingDTO {

    private String userUUID;
    private Integer projectID;
}
