package controller;

import model.Item;
import repository.IRepository;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addItem(Item item){
        this.repository.add(item);
    }

    public int size(){
        return this.repository.size();
    }

    public Item[] filter(float weight) {
        Item[] filteredArray = new Item[this.repository.size()];
        int filteredSize = 0;
        for (int i = 0; i < this.repository.size(); i++) {
            if (this.repository.getItems()[i].getWeight() > weight)
                filteredArray[filteredSize++] = this.repository.getItems()[i];

        }
        return filteredArray;
    }
}
