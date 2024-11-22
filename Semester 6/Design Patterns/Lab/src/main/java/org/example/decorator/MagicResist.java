package org.example.decorator;

public class MagicResist extends ChampionDecorator{
    public MagicResist(CustomClass customClass) {
        super(customClass);
    }

    public String buildChampion() {
        return super.buildChampion() + "with Magic Resist ";
    }
}
