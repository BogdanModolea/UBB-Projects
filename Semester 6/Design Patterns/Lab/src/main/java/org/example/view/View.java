package org.example.view;

import org.example.facade.GameImpl;
import org.example.view.command.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class View {
    private Map<String, Command> commands = new HashMap<>();
    private GameImpl game;

    private void addCommand(Command c) {
        this.commands.put(c.getKey(), c);
    }

    private void fillCommandsMap() {
        this.addCommand(new GetChampionCommand("1", "Get Champions", game));
        this.addCommand(new ShopKeeperCommand("2", "Enter Shop", game));
        this.addCommand(new TotalSpentCommand("3", "Get Total Gold Spent", game));
        this.addCommand(new BuyCommand("4", "Select Runes", game));
        this.addCommand(new BuildItemsCommand("5", "Build Items", game));
    }

    public View() {
        this.game = new GameImpl();
        this.fillCommandsMap();
    }

    private void printMenu() {
        for (var command : this.commands.values()) {
            System.out.println(command.getKey() + " " + command.getDescription());
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            try {
                com.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
