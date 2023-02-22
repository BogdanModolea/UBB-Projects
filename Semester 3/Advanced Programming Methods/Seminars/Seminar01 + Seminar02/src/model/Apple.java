package model;

public class Apple extends Item {
    public String getBreed() {
        return breed;
    }

    public Apple(float weight, String breed) {
        super(weight);
        this.breed = breed;
    }

//    public Apple(){
//        super(0.0f);
//        this.breed = "";
//    }

    public Apple() {
        this(0.0f, "");
    }


    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String toString(){
        return super.toString()+"breed"+this.breed;
    }

    private String breed;

}