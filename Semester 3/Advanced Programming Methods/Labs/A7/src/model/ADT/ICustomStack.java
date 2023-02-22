package model.ADT;

import model.exceptions.ADTException;

import java.util.List;

public interface ICustomStack<T> {
    public T pop() throws ADTException;

    public void push(T value);

    boolean isEmpty();

    public List<T> toList();
}
