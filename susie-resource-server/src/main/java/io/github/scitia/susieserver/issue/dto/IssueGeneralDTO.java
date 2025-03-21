package io.github.scitia.susieserver.issue.dto;

import io.github.scitia.susieserver.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueGeneralDTO {

    private Integer id;
    private String name;
    private UserDTO assignee;
    private Integer issueStatusID;
    private Integer issueTypeID;
    private Integer issuePriorityID;
    private Integer projectID;
    private Integer sprintID;
}
