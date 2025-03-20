package io.github.scitia.susieserver.issue.service;

import io.github.scitia.susieserver.issue.dto.IssueDTO;
import io.github.scitia.susieserver.issue.dto.IssueGeneralDTO;
import io.github.scitia.susieserver.issue.dto.IssueMRO;

import java.util.List;

public interface IssueService {

    IssueDTO createIssue(IssueMRO issue);
    IssueDTO cloneIssue(Integer issueID);
    IssueDTO updateIssue(IssueMRO issue);
    void deleteIssue(Integer issueID);
    IssueDTO getIssueDetails(Integer issueID);
    List<IssueGeneralDTO> getIssuesGeneral(Integer projectID);
    List<IssueGeneralDTO> getProductBacklog(Integer projectID);
    List<IssueGeneralDTO> getBacklogHistory(Integer projectID);
    List<IssueGeneralDTO> getGeneralIssuesInfoByUserID();
    List<IssueGeneralDTO> getGeneralIssuesInfoBySprintID(Integer sprintID);
    void assignCurrentUserToIssue(Integer issueID);
    void deleteUserToIssueAssignment(Integer issueID);
    void changeIssueStatus(Integer issueID, Integer statusID);
}
