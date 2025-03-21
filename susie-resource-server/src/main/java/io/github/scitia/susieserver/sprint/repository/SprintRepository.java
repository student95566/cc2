package io.github.scitia.susieserver.sprint.repository;

import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.sprint.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {

    Optional<List<Sprint>> findAllByActiveAndIsDoneAndProject(Boolean active, Boolean isDone ,Project project);
    Optional<Sprint> findByActiveAndProject(Boolean active, Project project);
    boolean existsByNameAndProject(String name, Project project);
}
