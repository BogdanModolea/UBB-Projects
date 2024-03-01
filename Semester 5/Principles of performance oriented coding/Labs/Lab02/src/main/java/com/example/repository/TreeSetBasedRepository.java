package com.example.repository;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetBasedRepository<T extends Comparable<T>> implements InMemoryRepository<T>{
    private final Set<T> set;

    public TreeSetBasedRepository() {
        this.set = new TreeSet<>();
    }

    @Override
    public void add(T element) {
        this.set.add(element);
    }

    @Override
    public boolean contains(T element) {
        return this.set.contains(element);
    }

    @Override
    public void remove(T element) {
        this.set.remove(element);
    }
}
