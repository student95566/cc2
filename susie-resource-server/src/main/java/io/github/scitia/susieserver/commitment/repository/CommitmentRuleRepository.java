package io.github.scitia.susieserver.commitment.repository;

import io.github.scitia.susieserver.commitment.domain.CommitmentRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitmentRuleRepository extends JpaRepository<CommitmentRule, Integer> {

}
