package io.github.scitia.susieserver.dictionary.service.issue.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssueTypeID {

    USER_STORY(1),
    BUG(2),
    TO_DO(3),
    AOA(4);

    private final Integer typeID;
}
