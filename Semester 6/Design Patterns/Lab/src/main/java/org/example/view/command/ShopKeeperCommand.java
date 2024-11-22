package org.example.view.command;

import org.example.facade.GameImpl;

import java.util.Objects;
import java.util.Scanner;

public class ShopKeeperCommand extends Command {
    public ShopKeeperCommand(String key, String description, GameImpl game) {
        super(key, description, game);
    }

    @Override
    public void execute() {
        System.out.println("Need more items?");
        Scanner scanner = new Scanner(System.in);
        boolean needMoreItems = true;

        while (needMoreItems) {
            String item = scanner.nextLine();
            if (Objects.equals(item, "Full")) {
                needMoreItems = false;
            } else if (!Objects.equals(item, "")) {
                getGame().buyItem();
            }
        }
    }
}
