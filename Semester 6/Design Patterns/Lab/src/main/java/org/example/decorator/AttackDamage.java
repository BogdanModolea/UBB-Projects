package org.example.decorator;

public class AttackDamage extends ChampionDecorator{
    public AttackDamage(CustomClass customClass) {
        super(customClass);
    }

    public String buildChampion() {
        return super.buildChampion() + "with AD ";
    }
}
