package org.example.facade.inventory;

import org.example.domain.Champion;
import org.example.facade.champions.Champions;
import org.example.facade.champions.MidlaneChampions;

import java.util.ArrayList;
import java.util.List;

public class MidlaneInventory implements Inventory {
    List<Champion> midlaneChampions = new ArrayList<>();

    @Override
    public Champions getChampions() {
        midlaneChampions.add(new Champion("Ahri", "Mid", "Mage"));
        midlaneChampions.add(new Champion("Neeko", "Mid", "Mage"));
        midlaneChampions.add(new Champion("Tristana", "Mid", "Marksman"));

        return new MidlaneChampions(midlaneChampions);
    }
}
