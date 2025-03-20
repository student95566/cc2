package io.github.scitia.susieserver.dictionary.service.issue.status;

import io.github.scitia.susieserver.dictionary.repository.IssueStatusRepository;
import io.github.scitia.susieserver.dictionary.domain.IssueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueStatusServiceImpl implements IssueStatusService {

    private final IssueStatusRepository issueStatusRepository;

    @Override
    public List<IssueStatus> getAllIssueStatuses() {
        return issueStatusRepository.findAll();
    }
}
