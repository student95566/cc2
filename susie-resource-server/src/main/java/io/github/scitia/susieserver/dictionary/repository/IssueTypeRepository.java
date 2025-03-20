package io.github.scitia.susieserver.dictionary.repository;

import io.github.scitia.susieserver.dictionary.domain.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {
}
