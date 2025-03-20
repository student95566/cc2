package io.github.scitia.susieserver.search;

public interface SearchExpression {
    boolean interpret(SearchContext context);
}