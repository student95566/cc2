// Lab1 // Factory Method
package io.github.scitia.susieserver.service;

import io.github.scitia.susieserver.backlog.domain.Backlog;
import io.github.scitia.susieserver.backlog.repository.BacklogRepository;
import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.project.dto.ProjectDTO;
import io.github.scitia.susieserver.project.factory.DefaultProjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectFactoryTest {

    @Mock
    private BacklogRepository backlogRepository;

    private DefaultProjectFactory projectFactory;

    private final String TEST_OWNER_UUID = "test-uuid-123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectFactory = new DefaultProjectFactory(backlogRepository);
    }

    @Test
    void createProject_shouldReturnValidProject() {
        // given
        String projectName = "Test Project";
        String projectDescription = "Test Description";
        String projectGoal = "Test Goal";

        ProjectDTO projectDTO = ProjectDTO.builder()
                .name(projectName)
                .description(projectDescription)
                .projectGoal(projectGoal)
                .build();

        // when
        Project project = projectFactory.createProject(projectDTO, TEST_OWNER_UUID);

        // then
        assertNotNull(project);
        assertEquals(projectName, project.getName());
        assertEquals(projectDescription, project.getDescription());
        assertEquals(projectGoal, project.getProjectGoal());
        assertEquals(TEST_OWNER_UUID, project.getProjectOwner());
        assertNotNull(project.getBacklog());
        assertNotNull(project.getUserIDs());
        assertTrue(project.getUserIDs().contains(TEST_OWNER_UUID));
        assertEquals(1, project.getUserIDs().size());
    }

    @Test
    void createScrumProject_shouldReturnProjectWithScrumSuffix() {
        // given
        String projectName = "Test Project";
        ProjectDTO projectDTO = ProjectDTO.builder()
                .name(projectName)
                .build();

        // when
        Project project = projectFactory.createScrumProject(projectDTO, TEST_OWNER_UUID);

        // then
        assertNotNull(project);
        assertTrue(project.getName().contains("(Scrum)"));
        assertEquals(projectName + " (Scrum)", project.getName());
    }

    @Test
    void createKanbanProject_shouldReturnProjectWithKanbanSuffix() {
        // given
        String projectName = "Test Project";
        ProjectDTO projectDTO = ProjectDTO.builder()
                .name(projectName)
                .build();

        // when
        Project project = projectFactory.createKanbanProject(projectDTO, TEST_OWNER_UUID);

        // then
        assertNotNull(project);
        assertTrue(project.getName().contains("(Kanban)"));
        assertEquals(projectName + " (Kanban)", project.getName());
    }

    @Test
    void createTemplateProject_agileTemplate_shouldReturnAgileProject() {
        // given
        String templateName = "agile";

        // when
        Project project = projectFactory.createTemplateProject(templateName, TEST_OWNER_UUID);

        // then
        assertNotNull(project);
        assertEquals("Agile Project (Scrum)", project.getName());
        assertEquals("Szablon projektu Agile", project.getDescription());
        assertEquals("Wdrożenie metodologii Agile", project.getProjectGoal());
    }

    @Test
    void createTemplateProject_unknownTemplate_shouldReturnDefaultProject() {
        // given
        String unknownTemplateName = "unknown_template";

        // when
        Project project = projectFactory.createTemplateProject(unknownTemplateName, TEST_OWNER_UUID);

        // then
        assertNotNull(project);
        assertEquals("Nowy projekt", project.getName());
    }
}