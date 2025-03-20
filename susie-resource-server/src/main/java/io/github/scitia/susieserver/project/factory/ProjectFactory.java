// Lab1 // Factory Method
package io.github.scitia.susieserver.project.factory;

import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.project.dto.ProjectDTO;


public interface ProjectFactory {

    Project createProject(ProjectDTO projectDTO, String ownerUUID);

    Project createScrumProject(ProjectDTO projectDTO, String ownerUUID);

    Project createKanbanProject(ProjectDTO projectDTO, String ownerUUID);
}