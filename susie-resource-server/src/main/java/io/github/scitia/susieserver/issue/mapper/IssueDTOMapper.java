package io.github.scitia.susieserver.issue.mapper;

import io.github.scitia.susieserver.comment.service.CommentService;
import io.github.scitia.susieserver.issue.domain.Issue;
import io.github.scitia.susieserver.issue.dto.IssueDTO;
import io.github.scitia.susieserver.issue.dto.IssueDTOBuilder;
import io.github.scitia.susieserver.user.service.UserService;
import io.github.scitia.susieserver.utils.CollectionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class IssueDTOMapper {

    private final UserService userService;
    private final CommentService commentService;

    public IssueDTO map(Issue from) {
        return IssueDTOBuilder.builder()
                .issueID(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .estimation(from.getEstimation())
                .reporter(userService.getUserSafely(from.getReporterID()))
                .assignee(userService.getUserSafely(from.getAssigneeID()))
                .projectID(from.getProjectID())
                .sprintID(
                        isNull(from.getSprint()) ? null : from.getSprint().getId()
                )
                .issueTypeID(
                        isNull(from.getIssueType()) ? null : from.getIssueType().getId()
                )
                .issuePriorityID(
                        isNull(from.getIssuePriority()) ? null : from.getIssuePriority().getId()
                )
                .issueStatusID(
                        isNull(from.getIssueStatus()) ? null : from.getIssueStatus().getId()
                )
                .comments(
                        CollectionsUtils.isNullOrEmpty(from.getComments()) ? new ArrayList<>() : commentService.getAllCommentsForIssueID(from.getId())
                )
                .build();
    }
}
