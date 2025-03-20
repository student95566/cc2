package io.github.scitia.susieserver.project.mapper;

import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.project.dto.ProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOMapper {

    public ProjectDTO map(Project from) {
        return ProjectDTO.builder()
                .projectID(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .projectGoal(from.getProjectGoal())
                .build();
    }
}
