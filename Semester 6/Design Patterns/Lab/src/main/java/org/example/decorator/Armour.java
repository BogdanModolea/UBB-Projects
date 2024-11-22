package org.example.decorator;

public class Armour extends ChampionDecorator {
    public Armour(CustomClass customClass) {
        super(customClass);
    }

    public String buildChampion() {
        return super.buildChampion() + "with Armour ";
    }
}
