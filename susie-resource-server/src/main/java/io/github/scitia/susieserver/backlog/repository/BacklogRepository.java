package io.github.scitia.susieserver.backlog.repository;

import io.github.scitia.susieserver.backlog.domain.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Integer> {
}
