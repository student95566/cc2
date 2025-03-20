package io.github.scitia.susieserver.shared.interfaces;

@FunctionalInterface
public interface Prototype<T> {
    T cloneObject();
}
