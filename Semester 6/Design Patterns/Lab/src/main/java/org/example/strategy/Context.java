package org.example.strategy;

public class Context {
    private BuyStrategy buyStrategy;

    public Context(BuyStrategy buyStrategy) {
        this.buyStrategy = buyStrategy;
    }

    public void buy(int gold) {
        this.buyStrategy.buy(gold);
    }
}
