package io.github.scitia.susieserver.issue.dto;

import io.github.scitia.susieserver.comment.dto.CommentDTO;
import io.github.scitia.susieserver.user.dto.UserDTO;

import java.util.List;

public class IssueDTOBuilder {
    private Integer issueID;
    private String name;
    private String description;
    private Integer estimation;
    private UserDTO reporter;
    private UserDTO assignee;
    private Integer issueTypeID;
    private Integer issuePriorityID;
    private Integer issueStatusID;
    private Integer projectID;
    private Integer sprintID;
    private List<CommentDTO> comments;

    IssueDTOBuilder() {
    }

    public static IssueDTOBuilder builder() {
        return new IssueDTOBuilder();
    }

    public IssueDTOBuilder issueID(final Integer issueID) {
        this.issueID = issueID;
        return this;
    }

    public IssueDTOBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public IssueDTOBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public IssueDTOBuilder estimation(final Integer estimation) {
        this.estimation = estimation;
        return this;
    }

    public IssueDTOBuilder reporter(final UserDTO reporter) {
        this.reporter = reporter;
        return this;
    }

    public IssueDTOBuilder assignee(final UserDTO assignee) {
        this.assignee = assignee;
        return this;
    }

    public IssueDTOBuilder issueTypeID(final Integer issueTypeID) {
        this.issueTypeID = issueTypeID;
        return this;
    }

    public IssueDTOBuilder issuePriorityID(final Integer issuePriorityID) {
        this.issuePriorityID = issuePriorityID;
        return this;
    }

    public IssueDTOBuilder issueStatusID(final Integer issueStatusID) {
        this.issueStatusID = issueStatusID;
        return this;
    }

    public IssueDTOBuilder projectID(final Integer projectID) {
        this.projectID = projectID;
        return this;
    }

    public IssueDTOBuilder sprintID(final Integer sprintID) {
        this.sprintID = sprintID;
        return this;
    }

    public IssueDTOBuilder comments(final List<CommentDTO> comments) {
        this.comments = comments;
        return this;
    }

    public IssueDTO build() {
        return new IssueDTO(this.issueID, this.name, this.description, this.estimation, this.reporter, this.assignee, this.issueTypeID, this.issuePriorityID, this.issueStatusID, this.projectID, this.sprintID, this.comments);
    }

    public String toString() {
        Integer var10000 = this.issueID;
        return "IssueDTO.IssueDTOBuilder(issueID=" + var10000 + ", name=" + this.name + ", description=" + this.description + ", estimation=" + this.estimation + ", reporter=" + String.valueOf(this.reporter) + ", assignee=" + String.valueOf(this.assignee) + ", issueTypeID=" + this.issueTypeID + ", issuePriorityID=" + this.issuePriorityID + ", issueStatusID=" + this.issueStatusID + ", projectID=" + this.projectID + ", sprintID=" + this.sprintID + ", comments=" + String.valueOf(this.comments) + ")";
    }
}
