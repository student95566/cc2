package io.github.scitia.susieserver.dictionary.service.issue.type;

import io.github.scitia.susieserver.dictionary.repository.IssueTypeRepository;
import io.github.scitia.susieserver.dictionary.domain.IssueType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueTypeServiceImpl implements IssueTypeService {

    private final IssueTypeRepository issueTypeRepository;

    @Override
    public List<IssueType> getAllIssueTypes() {
        return issueTypeRepository.findAll();
    }
}
