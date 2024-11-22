package org.example.decorator;

public class ChampionDecorator implements CustomClass {
    private CustomClass customClass;

    public ChampionDecorator(CustomClass customClass) {
        this.customClass = customClass;
    }

    @Override
    public String buildChampion() {
        return customClass.buildChampion();
    }
}
