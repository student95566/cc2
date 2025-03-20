package io.github.scitia.susieserver.dictionary.repository;

import io.github.scitia.susieserver.dictionary.domain.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer> {
}
