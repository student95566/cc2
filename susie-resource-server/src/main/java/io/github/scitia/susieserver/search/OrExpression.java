package io.github.scitia.susieserver.search;

public class OrExpression implements SearchExpression {
    private final SearchExpression left;
    private final SearchExpression right;

    public OrExpression(SearchExpression left, SearchExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(SearchContext context) {
        return left.interpret(context) || right.interpret(context);
    }
}