package com.example.repository;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapBasedRepository<T> implements InMemoryRepository<T>{
    private final ConcurrentHashMap<T, T> list;

    public ConcurrentHashMapBasedRepository() {
        this.list = new ConcurrentHashMap<>();
    }

    @Override
    public void add(T element) {
        this.list.put(element, element);
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
