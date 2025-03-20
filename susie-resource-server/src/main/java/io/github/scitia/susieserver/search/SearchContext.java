package io.github.scitia.susieserver.search;

import io.github.scitia.susieserver.project.Project;

public class SearchContext {
    private final Project item;

    public SearchContext(Project item) {
        this.item = item;
    }

    public Project getItem() {
        return item;
    }
}