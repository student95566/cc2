package io.github.scitia.susieserver.exception.issue;

import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.ISSUE_DOES_NOT_EXISTS;

public class IssueNotFound extends IllegalArgumentException {
    public IssueNotFound() {
        super(ISSUE_DOES_NOT_EXISTS);
    }
}
