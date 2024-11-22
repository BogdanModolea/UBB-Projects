package org.example.facade.inventory;

import org.example.domain.Champion;
import org.example.facade.champions.Champions;
import org.example.facade.champions.ToplaneChampions;

import java.util.ArrayList;
import java.util.List;

public class ToplaneInventory implements Inventory{
    List<Champion> toplaneChampions = new ArrayList<>();

    @Override
    public Champions getChampions() {
        toplaneChampions.add(new Champion("Aatrox", "Top", "Fighter"));
        toplaneChampions.add(new Champion("Camille", "Top", "Fighter"));
        toplaneChampions.add(new Champion("Ornn", "Top", "Tank"));

        return new ToplaneChampions(toplaneChampions);
    }
}
