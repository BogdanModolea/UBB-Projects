package model;

public class Book extends Item{

    private int pages;

    public Book(float weight, int pages) {
        super(weight);
        this.pages = pages;
    }

    public String toString(){
        return super.toString()+"pages"+this.pages;
    }
}
