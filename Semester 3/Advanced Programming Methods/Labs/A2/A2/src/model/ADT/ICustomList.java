package model.ADT;

import model.exceptions.ADTException;

public interface ICustomList<T> {
    void add(T element);

    void remove(T element) throws ADTException;

    int size();

    T get(int index) throws ADTException;
}
