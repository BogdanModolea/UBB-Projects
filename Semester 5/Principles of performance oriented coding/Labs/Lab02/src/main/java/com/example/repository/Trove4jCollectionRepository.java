package com.example.repository;

import gnu.trove.set.hash.THashSet;

public class Trove4jCollectionRepository<T> implements InMemoryRepository<T> {
    private final THashSet<T> set;

    public Trove4jCollectionRepository() {
        this.set = new THashSet<>();
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
