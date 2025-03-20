package io.github.scitia.susieserver.dictionary.repository;

import io.github.scitia.susieserver.dictionary.domain.IssuePriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer> {
}
