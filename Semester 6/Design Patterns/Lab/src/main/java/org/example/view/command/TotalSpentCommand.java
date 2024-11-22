package org.example.view.command;

import org.example.facade.GameImpl;

public class TotalSpentCommand extends Command {
    public TotalSpentCommand(String key, String description, GameImpl game) {
        super(key, description, game);
    }

    @Override
    public void execute() {
        System.out.println("Total spent: " + getGame().getChampionPrice());
    }
}
