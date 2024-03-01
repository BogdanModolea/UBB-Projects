package com.example.repository;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Sets;

public class EclipseCollectionRepository<T> implements InMemoryRepository<T> {
    private final MutableSet<T> set;

    public EclipseCollectionRepository() {
        this.set = Sets.mutable.empty();
    }

    @Override
    public void add(T element) {
        set.add(element);
    }

    @Override
    public boolean contains(T element) {
        return set.contains(element);
    }

    @Override
    public void remove(T element) {
        set.remove(element);
    }
}
