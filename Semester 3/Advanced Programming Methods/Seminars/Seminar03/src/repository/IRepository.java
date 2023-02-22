package repository;

import model.Item;

public interface IRepository {
    void add(Item element);

    void remove(Item element);

    void update(Item old_element, Item new_element);

    Item[] getItems();

    int size();
}
