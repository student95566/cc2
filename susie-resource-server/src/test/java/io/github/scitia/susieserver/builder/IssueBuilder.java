package io.github.scitia.susieserver.builder;

import io.github.scitia.susieserver.backlog.domain.Backlog;
import io.github.scitia.susieserver.issue.domain.Issue;
import io.github.scitia.susieserver.dictionary.domain.IssueStatus;
import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.sprint.domain.Sprint;
import io.github.scitia.susieserver.issue.dto.IssueMRO;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static io.github.scitia.susieserver.dictionary.service.issue.priority.IssuePriorityID.CRITICAL;
import static io.github.scitia.susieserver.dictionary.service.issue.type.IssueTypeID.BUG;

public class IssueBuilder {

    private static final String DEFAULT_ISSUE_NAME = "Issue name";
    public static final String OTHER_ISSUE_NAME = "Other issue name";
    private static final String DEFAULT_ISSUE_DESCRIPTION = "Issue description";
    public static final String OTHER_ISSUE_DESCRIPTION = "Other issue description";
    private static final Integer DEFAULT_ISSUE_ESTIMATION = 13;
    public static final Integer OTHER_ISSUE_ESTIMATION = 14;
    private static final Integer FAKE_ISSUE_TYPE_ID = -10;
    private static final Integer FAKE_ISSUE_PRIORITY_ID = -10;
    private static final Integer DEFAULT_PROJECT_ID = 1;
    private static final  String DEFAULT_PROJECT_NAME = "Project name";

    public static IssueMRO createDefaultIssue(Integer projectID) {
        return IssueMRO.builder()
                .name(DEFAULT_ISSUE_NAME)
                .description(DEFAULT_ISSUE_DESCRIPTION)
                .estimation(DEFAULT_ISSUE_ESTIMATION)
                .issuePriorityID(CRITICAL.getPriorityID())
                .issueTypeID(BUG.getTypeID())
                .projectID(projectID)
                .build();
    }

    public static IssueMRO createIssueWithFakeIssueTypeID(Integer projectID) {
        IssueMRO issueWithFakeIssueTypeID = createDefaultIssue(projectID);
        issueWithFakeIssueTypeID.setIssueTypeID(FAKE_ISSUE_TYPE_ID);
        return issueWithFakeIssueTypeID;
    }

    public static IssueMRO createIssueWithFakeIssuePriorityID(Integer projectID) {
        IssueMRO issueWithFakeIssuePriorityID = createDefaultIssue(projectID);
        issueWithFakeIssuePriorityID.setIssuePriorityID(FAKE_ISSUE_PRIORITY_ID);
        return issueWithFakeIssuePriorityID;
    }

    public static List<IssueMRO> createManyIssues(Integer n, Integer projectID) {
        int nameLength = 10;
        List<IssueMRO> nIssues =  new ArrayList<>(Collections.nCopies(n, createDefaultIssue(projectID)));
        nIssues.forEach(issue -> issue.setName(RandomStringUtils.random(nameLength)));
        return nIssues;
    }

    public static Project createDefaultProject() {
        return Project.builder()
                .name(DEFAULT_ISSUE_NAME)
                .backlog(new Backlog())
                .build();
    }

    public static Issue createIssueEntity() {
        return Issue.builder()
                .name(DEFAULT_ISSUE_NAME)
                .description(DEFAULT_ISSUE_DESCRIPTION)
                .estimation(DEFAULT_ISSUE_ESTIMATION)
                .comments(new HashSet<>())
                .build();
    }

    public static Issue createIssueWithProject(Integer projectID) {
        Issue issue = createIssueEntity();
        issue.setProjectID(projectID);
        return issue;
    }

    public static Issue createIssueEntityWithIssueStatus(IssueStatus status) {
        Issue issue = createIssueEntity();
        issue.setIssueStatus(status);
        return issue;
    }

    public static Issue createIssueEntityWithSprint(Sprint sprint) {
        return Issue.builder()
                .name(DEFAULT_ISSUE_NAME)
                .description(DEFAULT_ISSUE_DESCRIPTION)
                .sprint(sprint)
                .build();
    }

    public static Issue createIssueEntityWithAssignee(String uuid) {
        return Issue.builder()
                .name(DEFAULT_ISSUE_NAME)
                .description(DEFAULT_ISSUE_DESCRIPTION)
                .estimation(DEFAULT_ISSUE_ESTIMATION)
                .assigneeID(uuid)
                .build();
    }
}
