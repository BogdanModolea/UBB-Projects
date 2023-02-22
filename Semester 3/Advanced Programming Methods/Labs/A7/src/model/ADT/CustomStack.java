package model.ADT;

import model.exceptions.ADTException;

import java.util.*;

public class CustomStack<T> implements ICustomStack<T> {

    private Stack<T> stack;

    public CustomStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws ADTException {
        if (this.stack.size() == 0)
            throw new ADTException("Empty Stack");

        return this.stack.pop();
    }

    @Override
    public List<T> toList(){
        List<T> list = new ArrayList<>(stack);
        Collections.reverse(list);
        return list;
    }

    @Override
    public void push(T value) {
        this.stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (T element : this.stack)
            string.append(element).append(" ");

        return string.toString();
    }
}
