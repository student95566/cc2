package io.github.scitia.susieserver.builder;

import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.sprint.domain.Sprint;
import io.github.scitia.susieserver.sprint.dto.SprintDTO;
import io.github.scitia.susieserver.sprint.dto.SprintDTOBuilder;

import java.time.ZonedDateTime;
import java.util.HashSet;

import static java.lang.Boolean.FALSE;

public class SprintBuilder {

    public static final String SPRINT_NAME = "Test sprint name";
    public static final ZonedDateTime SPRINT_START_TIME = ZonedDateTime.now();
    public static final String SPRINT_GOAL = "Very important goal";

    public static SprintDTO createSprint(Integer projectID) {
        return SprintDTOBuilder.builder()
                .name(SPRINT_NAME)
                .startTime(SPRINT_START_TIME)
                .projectID(projectID)
                .build();
    }

    public static Sprint createSprintEntity(Project project, Boolean active) {
        return Sprint.builder()
                .name(SPRINT_NAME)
                .startDate(SPRINT_START_TIME)
                .isDone(FALSE)
                .sprintGoal(SPRINT_GOAL)
                .project(project)
                .sprintIssues(new HashSet<>())
                .active(active)
                .build();
    }
}
