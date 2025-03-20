package io.github.scitia.susieserver.dictionary.controller;

import io.github.scitia.susieserver.dictionary.service.issue.priority.IssuePriorityService;
import io.github.scitia.susieserver.dictionary.service.issue.status.IssueStatusService;
import io.github.scitia.susieserver.dictionary.service.issue.type.IssueTypeService;
import io.github.scitia.susieserver.dictionary.domain.IssuePriority;
import io.github.scitia.susieserver.dictionary.domain.IssueStatus;
import io.github.scitia.susieserver.dictionary.domain.IssueType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor
public class IssueDictionaryRestController {

    private final IssueTypeService issueTypeService;
    private final IssueStatusService issueStatusService;
    private final IssuePriorityService issuePriorityService;

    @GetMapping("/type")
    public List<IssueType> getAllIssueTypes() {
        return issueTypeService.getAllIssueTypes();
    }

    @GetMapping("/status")
    public List<IssueStatus> getAllIssueStatuses() {
        return issueStatusService.getAllIssueStatuses();
    }

    @GetMapping("/priority")
    public List<IssuePriority> getAllIssuePriorities() {
        return issuePriorityService.getAllIssuePriorities();
    }
}
