package io.github.scitia.susieserver.project.service;

import io.github.scitia.susieserver.project.dto.ProjectDTO;
import io.github.scitia.susieserver.project.dto.ProjectDetailsDTO;

import java.util.List;

public interface ProjectService {

    ProjectDetailsDTO getProjectDetails(Integer projectID);
    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO deleteProject(Integer projectID);
    List<ProjectDTO> getAllProjects();
    void associateUserWithProject(String email, Integer projectID);
    void deleteUserFromProject(String uuid, Integer projectID);
}
