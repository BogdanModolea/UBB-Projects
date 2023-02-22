package model.ADT;

import model.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CustomList<T> implements ICustomList<T> {

    private List<T> list;

    public CustomList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public void remove(T element) throws ADTException {
        try {
            this.list.remove(element);
        } catch (NoSuchElementException ex) {
            throw new ADTException("Not existing element");
        }
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public T get(int index) throws ADTException {
        try {
            return this.list.get(index);
        } catch (IndexOutOfBoundsException ex) {
            throw new ADTException(ex.getMessage());
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (T element : this.list)
            string.append(element).append(" ");

        return string.toString();
    }
}
