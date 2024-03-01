package com.example.repository;

public interface InMemoryRepository<T> {
    void add(T element);

    boolean contains(T element);

    void remove(T element);
}
