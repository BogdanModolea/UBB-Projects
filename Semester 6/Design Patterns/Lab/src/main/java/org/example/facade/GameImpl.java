package org.example.facade;

import org.example.facade.champions.MidlaneChampions;
import org.example.facade.champions.ToplaneChampions;
import org.example.facade.inventory.BoughtItems;
import org.example.facade.inventory.MidlaneInventory;
import org.example.facade.inventory.ToplaneInventory;
import org.example.singleton.Logger;
import org.example.strategy.BuyStrategy;
import org.example.strategy.Context;

public class GameImpl implements Game{
    private final MidlaneInventory midlaneInventory;
    private final ToplaneInventory toplaneInventory;
    private final BoughtItems boughtItems;
    private final Logger logger = Logger.getInstanceOf();
    private Context context;

    public GameImpl() {
        this.midlaneInventory = new MidlaneInventory();
        this.toplaneInventory = new ToplaneInventory();
        this.boughtItems = new BoughtItems();
    }

    @Override
    public ToplaneChampions getToplaneChampions() {
        logger.log("Game Implementation: getToplaneChampions() called");
        return (ToplaneChampions) toplaneInventory.getChampions();
    }

    @Override
    public MidlaneChampions getMidlaneChampions() {
        logger.log("Game Implementation: getMidlaneChampions() called");
        return (MidlaneChampions) midlaneInventory.getChampions();
    }

    @Override
    public int getChampionPrice() {
        logger.log("Game Implementation: getChampionPrice() called");
        return boughtItems.getTotalPrice();
    }

    @Override
    public void buyItem() {
        logger.log("Game Implementation: buyItem() called");
        boughtItems.addItem();
    }

    @Override
    public void selectRunes(BuyStrategy buyStrategy) {
        logger.log("Game Implementation: selectRunes() called");
        context = new Context(buyStrategy);
    }

    @Override
    public void buy() {
        logger.log("Game Implementation: buy() called");
        context.buy(getChampionPrice());
        boughtItems.reset();
    }


    @Override
    public void buildItems(String firstItem, String secondItem, String thirdItem, String fourthItem) {
        logger.log("Game Implementation: buildItems() called");
        boughtItems.buildItems(firstItem, secondItem, thirdItem, fourthItem);
        this.buyItem();
    }
}
