package io.github.scitia.susieserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
}
