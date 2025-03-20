package io.github.scitia.security.shared.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendPermissionRequest {

    private Integer projectID;
    private String userUUID;
    private String roleName;
}
