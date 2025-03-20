package io.github.scitia.susieserver.issue.mapper;

import io.github.scitia.susieserver.issue.dto.IssueGeneralDTO;
import io.github.scitia.susieserver.issue.domain.Issue;
import io.github.scitia.susieserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class IssueGeneralDTOMapper {

    private final UserService userService;

    public IssueGeneralDTO map(Issue from) {
        return IssueGeneralDTO.builder()
                .id(from.getId())
                .name(from.getName())
                .assignee(
                        isNull(from.getAssigneeID()) ? null : userService.getUserByUUID(from.getAssigneeID())
                )
                .issueStatusID(
                        isNull(from.getIssueStatus()) ? null : from.getIssueStatus().getId()
                )
                .issueTypeID(
                        isNull(from.getIssueType()) ? null : from.getIssueType().getId()
                )
                .issuePriorityID(
                        isNull(from.getIssuePriority()) ? null : from.getIssuePriority().getId()
                )
                .projectID(from.getProjectID())
                .sprintID(
                        isNull(from.getSprint()) ? null : from.getSprint().getId()
                )
                .build();
    }
}
