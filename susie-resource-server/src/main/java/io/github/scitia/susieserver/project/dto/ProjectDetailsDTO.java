package io.github.scitia.susieserver.project.dto;

import io.github.scitia.susieserver.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailsDTO extends ProjectDTO {

    private UserDTO owner;
    private List<UserDTO> members;
}
