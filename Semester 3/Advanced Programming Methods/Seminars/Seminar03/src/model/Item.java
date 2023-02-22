package model;

public abstract class Item {
    public Item(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    protected float weight;

    public String toString() {
        return "weight: " + weight;
    }
}
