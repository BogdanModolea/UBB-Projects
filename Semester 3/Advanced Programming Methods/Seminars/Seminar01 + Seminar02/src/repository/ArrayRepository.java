package repository;

import model.Item;

public class ArrayRepository implements IRepository{
    private Item array[];
    private int capacity = 10, size;

    public ArrayRepository(){
        size = 0;
        array = new Item[capacity];
    }

    @Override
    public void add(Item element) {
        array[size++] = element;
    }

    @Override
    public void remove(Item element) {

    }

    @Override
    public void update(Item old_element, Item new_element) {

    }

    @Override
    public Item[] getItems() {
        return array;
    }

    @Override
    public int size() {
        return size;
    }
}
