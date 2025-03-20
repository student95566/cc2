package io.github.scitia.susieserver.search;

import io.github.scitia.susieserver.project.domain.Project;

public class PriorityExpression implements SearchExpression {
    private final String priority;

    public PriorityExpression(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean interpret(SearchContext context) {
        Project project = context.getItem();
        return project.getPriority().equals(priority);
    }
}