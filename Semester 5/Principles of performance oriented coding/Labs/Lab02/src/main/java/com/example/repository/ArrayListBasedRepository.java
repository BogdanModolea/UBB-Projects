package com.example.repository;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository <T>{
    private final List<T> list;

    public ArrayListBasedRepository() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public boolean contains(T element) {
        return this.list.contains(element);
    }

    @Override
    public void remove(T element) {
        this.list.remove(element);
    }
}
