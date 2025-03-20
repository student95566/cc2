// Lab5 // Interpreter
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

    // Przykładowa metoda pokazująca, jak można używać wzorca
    public List<Project> findActiveHighPriorityProjects(List<Project> allProjects) {
        SearchExpression activeExpression = new StatusExpression("Active");
        SearchExpression highPriorityExpression = new PriorityExpression("High");
        SearchExpression combinedExpression = new AndExpression(activeExpression, highPriorityExpression);

        return searchProjects(allProjects, combinedExpression);
    }

    // Inna przykładowa metoda
    public List<Project> findScrumOrKanbanProjects(List<Project> allProjects) {
        SearchExpression scrumExpression = new NameExpression("Scrum");
        SearchExpression kanbanExpression = new NameExpression("Kanban");
        SearchExpression combinedExpression = new OrExpression(scrumExpression, kanbanExpression);

        return searchProjects(allProjects, combinedExpression);
    }
}