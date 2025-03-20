package io.github.scitia.susieserver.exception.definition;

import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.NULL_IDENTIFIER;

public class NullIdentifierException extends IllegalArgumentException {

    public NullIdentifierException() {
        super(NULL_IDENTIFIER);
    }
}
