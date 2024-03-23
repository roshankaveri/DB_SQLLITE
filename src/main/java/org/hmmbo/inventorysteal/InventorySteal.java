package org.hmmbo.inventorysteal;

import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.File;
import java.io.IOException;

public final class InventorySteal extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        File dataFile = new File(getDataFolder(), "database.db");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                getLogger().info("Database Loaded");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataBaseManager db = new DataBaseManager();
        db.connect(dataFile);
        db.createTables();

        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.register(new Commands(db));

        getLogger().info("Loaded Database Plugin");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
