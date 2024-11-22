package org.example.facade;

import org.example.facade.champions.MidlaneChampions;
import org.example.facade.champions.ToplaneChampions;
import org.example.strategy.BuyStrategy;

public interface Game {
    ToplaneChampions getToplaneChampions();
    MidlaneChampions getMidlaneChampions();
    int getChampionPrice();
    void buyItem();
    void selectRunes(BuyStrategy buyStrategy);
    void buy();
    void buildItems(String firstItem, String secondItem, String thirdItem, String fourthItem);
}
