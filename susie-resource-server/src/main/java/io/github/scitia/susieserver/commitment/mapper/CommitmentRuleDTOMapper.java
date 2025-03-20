package io.github.scitia.susieserver.commitment.mapper;

import io.github.scitia.susieserver.commitment.domain.CommitmentRule;
import io.github.scitia.susieserver.commitment.dto.CommitmentRuleDTO;
import org.springframework.stereotype.Component;

@Component
public class CommitmentRuleDTOMapper {

    public CommitmentRuleDTO map(CommitmentRule from) {
        CommitmentRuleDTO rule = new CommitmentRuleDTO();
        rule.setRuleID(from.getId());
        rule.setRuleName(from.getRule());
        return rule;
    }
}
