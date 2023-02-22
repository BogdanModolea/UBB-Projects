package model.ADT;

import model.exceptions.ADTException;

public interface ICustomStack<T> {
    public T pop() throws ADTException;

    public void push(T value);

    boolean isEmpty();
}
