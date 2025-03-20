package io.github.scitia.susieserver.builder;

import io.github.scitia.susieserver.backlog.domain.Backlog;
import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.project.dto.ProjectDTO;

import java.util.HashSet;
import java.util.List;

import static io.github.scitia.susieserver.builder.UserBuilder.CURRENT_USER_UUID;

public class ProjectBuilder {

    public static final String PROJECT_NAME = "TestName";
    public static final String PROJECT_DESCRIPTION = "Test description";
    public static final String ANOTHER_PROJECT_NAME = "Another project name";
    public static final String ANOTHER_PROJECT_DESCRIPTION = "Another test project description";
    public static final String PROJECT_GOAL = "Default project goal";
    public static final String ANOTHER_PROJECT_GOAL = "Another project goal";

    public static ProjectDTO createProject() {
        return ProjectDTO.builder()
                .name(PROJECT_NAME)
                .description(PROJECT_DESCRIPTION)
                .build();
    }

    public static ProjectDTO createAnotherProject() {
        return ProjectDTO.builder()
                .name(ANOTHER_PROJECT_NAME)
                .description(ANOTHER_PROJECT_DESCRIPTION)
                .build();
    }

    public static Project createProjectEntity() {
        return Project.builder()
                .name(PROJECT_NAME)
                .description(PROJECT_DESCRIPTION)
                .projectOwner(CURRENT_USER_UUID)
                .userIDs(new HashSet<>(List.of(CURRENT_USER_UUID)))
                .backlog(new Backlog())
                .build();
    }
}
