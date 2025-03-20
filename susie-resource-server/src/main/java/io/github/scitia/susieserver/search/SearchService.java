package io.github.scitia.susieserver.search;

import io.github.scitia.susieserver.project.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    
    public List<Project> searchProjects(List<Project> projects, SearchExpression expression) {
        return projects.stream()
                .filter(project -> expression.interpret(new SearchContext(project)))
                .collect(Collectors.toList());
    }
}