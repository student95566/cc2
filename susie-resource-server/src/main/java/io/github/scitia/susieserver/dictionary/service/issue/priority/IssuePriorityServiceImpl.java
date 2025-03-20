package io.github.scitia.susieserver.dictionary.service.issue.priority;

import io.github.scitia.susieserver.dictionary.repository.IssuePriorityRepository;
import io.github.scitia.susieserver.dictionary.domain.IssuePriority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuePriorityServiceImpl implements IssuePriorityService {

    private final IssuePriorityRepository issuePriorityRepository;

    @Override
    public List<IssuePriority> getAllIssuePriorities() {
        return issuePriorityRepository.findAll();
    }
}
