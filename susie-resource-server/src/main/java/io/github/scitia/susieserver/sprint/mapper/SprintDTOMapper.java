package io.github.scitia.susieserver.sprint.mapper;

import io.github.scitia.susieserver.sprint.domain.Sprint;
import io.github.scitia.susieserver.sprint.dto.SprintDTO;
import io.github.scitia.susieserver.sprint.dto.SprintDTOBuilder;
import org.springframework.stereotype.Component;

@Component
public class SprintDTOMapper {

    public SprintDTO map(Sprint from) {
        return SprintDTOBuilder.builder()
                .id(from.getId())
                .name(from.getName())
                .startTime(from.getStartDate())
                .sprintGoal(from.getSprintGoal())
                .active(from.getActive())
                .projectID(from.getProject().getId())
                .build();
    }
}
