package org.example.decorator;

public class CriticalStrike extends ChampionDecorator{
    public CriticalStrike(CustomClass customClass) {
        super(customClass);
    }

    public String buildChampion() {
        return super.buildChampion() + "with Critical Strike ";
    }
}
