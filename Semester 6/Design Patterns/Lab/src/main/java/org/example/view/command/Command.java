package org.example.view.command;

import org.example.facade.GameImpl;

public abstract class Command {
    private String key;
    private String description;

    private GameImpl game;

    Command(String key, String description, GameImpl keeper) {
        this.key = key;
        this.description = description;
        this.game = keeper;
    }

    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public GameImpl getGame(){
        return game;
    }
}
