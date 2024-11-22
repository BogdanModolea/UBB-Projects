package org.example.view.command;

import org.example.facade.GameImpl;

import java.util.Scanner;

public class BuildItemsCommand extends Command {
    public BuildItemsCommand(String key, String description, GameImpl game) {
        super(key, description, game);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Summoner's Rift!");
        System.out.println("You can build items for your champion here.");
        System.out.println("1. Doran's Blade");
        System.out.println("2. Doran's Ring");
        System.out.println("3. Doran's Shield");

        String firstItem = scanner.nextLine();



        System.out.println("Start building your first item!");
        System.out.println("1. Armour Cloth");
        System.out.println("2. Null-Magic Mantle");
        System.out.println("3. B.F. Sword");
        System.out.println("4. Needlessly Large Rod");

        String secondItem = scanner.nextLine();



        System.out.println("Finish building your second item!");
        System.out.println("1. Sunfire Cape");
        System.out.println("2. Spirit Visage");
        System.out.println("3. Infinity Edge");
        System.out.println("4. Rabadon's Deathcap");

        String thirdItem = scanner.nextLine();



        System.out.println("Build your third item!");
        System.out.println("1. Guardian Angel");
        System.out.println("2. Banshee's Veil");
        System.out.println("3. Bloodthirster");
        System.out.println("4. Hextech Gunblade");

        String fourthItem = scanner.nextLine();

        getGame().buildItems(firstItem, secondItem, thirdItem, fourthItem);
    }
}
