package io.github.scitia.susieserver.dictionary.service.issue.priority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IssuePriorityID {

    CRITICAL(1),
    HIGH(2),
    MEDIUM(3),
    LOW(4),
    TRIVIAL(5);

    private final Integer priorityID;
}
