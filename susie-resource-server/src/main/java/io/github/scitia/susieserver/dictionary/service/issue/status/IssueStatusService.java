package io.github.scitia.susieserver.dictionary.service.issue.status;

import io.github.scitia.susieserver.dictionary.domain.IssueStatus;

import java.util.List;

public interface IssueStatusService {

    List<IssueStatus> getAllIssueStatuses();
}
