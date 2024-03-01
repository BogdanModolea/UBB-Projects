package com.example.repository;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class FastUtilCollectionRepository<T> implements InMemoryRepository<T> {
    private final ObjectArrayList<T> list;

    public FastUtilCollectionRepository() {
        this.list = new ObjectArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }
}
