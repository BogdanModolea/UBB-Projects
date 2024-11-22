package org.example.strategy;

public class FuturesMarket implements BuyStrategy {
    private String side;

    public FuturesMarket(String side) {
        this.side = side;
    }

    @Override
    public void buy(int gold) {
        System.out.println("Shopkeeper from side " + side + " gave " + gold + " gold");
    }
}
