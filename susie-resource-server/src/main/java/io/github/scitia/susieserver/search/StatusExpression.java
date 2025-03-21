// Lab5 // Interpreter
package io.github.scitia.susieserver.search;

import io.github.scitia.susieserver.project.domain.Project;

public class StatusExpression implements SearchExpression {
    private final String status;

    public StatusExpression(String status) {
        this.status = status;
    }

    @Override
    public boolean interpret(SearchContext context) {
        Project project = context.getItem();
        return project.getStatus() != null && project.getStatus().equals(status);
    }
}