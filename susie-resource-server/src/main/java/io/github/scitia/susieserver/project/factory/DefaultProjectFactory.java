// Lab1 // Factory Method

package io.github.scitia.susieserver.project.factory;

import io.github.scitia.susieserver.backlog.domain.Backlog;
import io.github.scitia.susieserver.backlog.repository.BacklogRepository;
import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.project.dto.ProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DefaultProjectFactory implements ProjectFactory {

    private final BacklogRepository backlogRepository;

    @Override
    public Project createProject(ProjectDTO projectDTO, String ownerUUID) {
        HashSet<String> usersAssociatedWithProject = new HashSet<>();
        usersAssociatedWithProject.add(ownerUUID);

        Backlog backlog = new Backlog();

        return Project.builder()
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .projectOwner(ownerUUID)
                .projectGoal(projectDTO.getProjectGoal())
                .backlog(backlog)
                .userIDs(usersAssociatedWithProject)
                .sprints(new HashSet<>())
                .dod(new HashSet<>())
                .build();
    }

    @Override
    public Project createScrumProject(ProjectDTO projectDTO, String ownerUUID) {
        Project project = createProject(projectDTO, String.valueOf(ownerUUID));

        project.setName(project.getName() + " (Scrum)");

        return project;
    }

    @Override
    public Project createKanbanProject(ProjectDTO projectDTO, String ownerUUID) {
        Project project = createProject(projectDTO, String.valueOf(ownerUUID));

        project.setName(project.getName() + " (Kanban)");

        return project;
    }


    public Project createTemplateProject(String templateName, String ownerUUID) {
        ProjectDTO templateProjectDTO = new ProjectDTO();

        switch (templateName) {
            case "agile":
                templateProjectDTO.setName("Agile Project");
                templateProjectDTO.setDescription("Szablon projektu Agile");
                templateProjectDTO.setProjectGoal("Wdrożenie metodologii Agile");
                return createScrumProject(templateProjectDTO, ownerUUID);

            case "software":
                templateProjectDTO.setName("Software Development");
                templateProjectDTO.setDescription("Projekt rozwoju oprogramowania");
                templateProjectDTO.setProjectGoal("Dostarczenie wysokiej jakości oprogramowania");
                return createScrumProject(templateProjectDTO, ownerUUID);

            case "service":
                templateProjectDTO.setName("Service Management");
                templateProjectDTO.setDescription("Zarządzanie usługami");
                templateProjectDTO.setProjectGoal("Optymalizacja procesu zarządzania usługami");
                return createKanbanProject(templateProjectDTO, ownerUUID);

            default:
                templateProjectDTO.setName("Nowy projekt");
                templateProjectDTO.setDescription("Opis projektu");
                templateProjectDTO.setProjectGoal("Cel projektu");
                return createProject(templateProjectDTO, ownerUUID);
        }
    }
}