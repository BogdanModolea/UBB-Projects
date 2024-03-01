package com.example.repository;

import com.koloboke.collect.set.hash.HashObjSet;
import com.koloboke.collect.set.hash.HashObjSets;

public class KolobokeCollectionRepository<T> implements InMemoryRepository<T> {
    private final HashObjSet<T> set;

    public KolobokeCollectionRepository() {
        this.set = HashObjSets.newMutableSet();
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
