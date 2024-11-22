package org.example.decorator;

public class AbilityPower extends ChampionDecorator {
    public AbilityPower(CustomClass customClass) {
        super(customClass);
    }

    public String buildChampion() {
        return super.buildChampion() + "with AP ";
    }
}
