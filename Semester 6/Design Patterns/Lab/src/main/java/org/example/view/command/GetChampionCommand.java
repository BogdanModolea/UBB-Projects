package org.example.view.command;

import org.example.facade.GameImpl;

import java.util.Objects;
import java.util.Scanner;

public class GetChampionCommand extends Command {
    public GetChampionCommand(String key, String description, GameImpl game) {
        super(key, description, game);
    }

    @Override
    public void execute() {
        System.out.println("Choose the role");
        System.out.println("1. Top");
        System.out.println("2. Mid");

        Scanner scanner = new Scanner(System.in);
        String role = scanner.nextLine();

        if (Objects.equals(role, "1")) {
            System.out.println(getGame().getToplaneChampions().toString());
        } else if (Objects.equals(role, "2")) {
            System.out.println(getGame().getMidlaneChampions().toString());
        } else {
            System.out.println("Invalid role");
        }
    }
}
