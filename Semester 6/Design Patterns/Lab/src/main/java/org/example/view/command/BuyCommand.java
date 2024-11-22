package org.example.view.command;

import org.example.facade.GameImpl;
import org.example.strategy.FuturesMarket;
import org.example.strategy.PlayerGold;

import java.util.Scanner;

public class BuyCommand extends Command {
    public BuyCommand(String key, String description, GameImpl game) {
        super(key, description, game);
    }

    @Override
    public void execute() {
        System.out.println("Choose your runes");
        System.out.println("1. Cookie Delivery");
        System.out.println("2. Future's Market");

        Scanner scanner = new Scanner(System.in);
        String rune = scanner.nextLine();

        if(rune.equals("1")) {
            Scanner user = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = user.nextLine();

            PlayerGold playerGold = new PlayerGold(username);
            this.getGame().selectRunes(playerGold);
            this.getGame().buy();
        } else if(rune.equals("2")) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter the side: ");
            String side = reader.nextLine();

            FuturesMarket futuresMarket = new FuturesMarket(side);
            this.getGame().selectRunes(futuresMarket);
            this.getGame().buy();
        } else {
            System.out.println("Invalid rune");
        }
    }
}
