package org.example.facade.inventory;

import org.example.decorator.*;

public class BoughtItems {
    int totalPrice;

    public BoughtItems() {
        this.totalPrice = 0;
    }

    public void addItem() {
        totalPrice += 3150;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void reset() {
        totalPrice = 0;
    }

    public void buildItems(String firstItem, String secondItem, String thirdItem, String fourthItem) {
        CustomClass first;
        CustomClass second;
        CustomClass third;
        CustomClass fourth;


        switch (firstItem) {
            case "1" -> first = new AttackDamage(new CustomClassImpl());
            case "2" -> first = new AbilityPower(new CustomClassImpl());
            case "3" -> first = new Armour(new CustomClassImpl());
            default -> first = new CustomClassImpl();
        }

        switch (secondItem) {
            case "1" -> second = new Armour(first);
            case "2" -> second = new MagicResist(first);
            case "3" -> second = new AttackDamage(first);
            case "4" -> second = new AbilityPower(first);
            default -> second = first;
        }

        switch (thirdItem) {
            case "1" -> third = new Armour(second);
            case "2" -> third = new MagicResist(second);
            case "3" -> third = new AttackDamage(second);
            case "4" -> third = new AbilityPower(second);
            default -> third = second;
        }

        switch (fourthItem) {
            case "1" -> fourth = new Armour(third);
            case "2" -> fourth = new MagicResist(third);
            case "3" -> fourth = new AttackDamage(third);
            case "4" -> fourth = new AbilityPower(third);
            default -> fourth = third;
        }

        System.out.println("Your built champion has " + fourth.buildChampion());
    }
}
