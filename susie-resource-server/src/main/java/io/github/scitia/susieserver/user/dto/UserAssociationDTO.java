package io.github.scitia.susieserver.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAssociationDTO {

    private String email;
    private Integer projectID;
}
