package org.hmmbo.inventorysteal;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.AutoComplete;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command("sql")
public class Commands {

    private final DataBaseManager dataBase;

    public Commands(DataBaseManager dataBase) {
        this.dataBase = dataBase;
    }

    @AutoComplete("<player> <score>")
    @Subcommand("insert")
    public void onInsert(Player sender, String name, int score) {
        sender.sendMessage("Inserting data...");
        dataBase.savePlayer(name, score);
        sender.sendMessage("Data inserted successfully.");
    }

    @AutoComplete("<player> <new_score>")
    @Subcommand("update")
    public void onUpdate(Player sender, String name, int newScore) {
        sender.sendMessage("Updating data...");
        dataBase.updatePlayerScore(name, newScore);
        sender.sendMessage("Data updated successfully.");
    }

    @AutoComplete("<player>")
    @Subcommand("select")
    public void onSelect(Player sender, String name) {
        sender.sendMessage("Retrieving data...");
        int score = dataBase.getPlayerScore(name);
        sender.sendMessage("Score of " + name + ": " + score);
    }
}
