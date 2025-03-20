package io.github.scitia.susieserver.project.repository;

import io.github.scitia.susieserver.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByNameAndProjectOwner(String projectName, String projectOwner);
    List<Project> findAllByUserIDsContains(String email);
    boolean existsByProjectOwner(String uuid);
}
