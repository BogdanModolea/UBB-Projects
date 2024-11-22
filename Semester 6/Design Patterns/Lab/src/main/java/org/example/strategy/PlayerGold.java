package org.example.strategy;

public class PlayerGold implements BuyStrategy {
    private String username;

    public PlayerGold(String username) {
        this.username = username;
    }

    @Override
    public void buy(int gold) {
        System.out.println("Player " + username + " used " + gold + " gold");
    }
}
