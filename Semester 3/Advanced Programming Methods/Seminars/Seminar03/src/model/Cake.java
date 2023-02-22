package model;

public class Cake extends Item{

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String toString(){
        return super.toString()+"flavour"+this.flavour;
    }

    private String flavour;

    public Cake(float weight, String flavour) {
        super(weight);
        this.flavour = flavour;
    }
}
