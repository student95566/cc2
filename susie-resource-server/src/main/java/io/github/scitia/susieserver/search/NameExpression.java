// Lab5 // Interpreter
package io.github.scitia.susieserver.search;

import io.github.scitia.susieserver.project.domain.Project;

public class NameExpression implements SearchExpression {
    private final String keyword;

    public NameExpression(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean interpret(SearchContext context) {
        Project project = context.getItem();
        return project.getName() != null && project.getName().contains(keyword);
    }
}