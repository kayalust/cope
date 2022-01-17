package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import rip.kaya.placeholders.command.CommandService;
import rip.kaya.placeholders.command.Drink;
import rip.kaya.placeholders.managers.MongoManager;
import rip.kaya.placeholders.managers.PlaceholderManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public class PlaceholderPlugin extends JavaPlugin {

    @Getter
    private static PlaceholderPlugin instance;

    private MongoManager mongoManager;
    private PlaceholderManager placeholderManager;

    private Executor mongoThread;

    @Override
    public void onLoad() {
        instance = this;

        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.mongoThread = Executors.newSingleThreadExecutor();

        this.mongoManager = new MongoManager(this);
        mongoManager.init();

        this.placeholderManager = new PlaceholderManager(this);
        placeholderManager.init();

        CommandService drink = Drink.get(this);

        drink.bind(Placeholder.class).toProvider(new PlaceholderProvider());
        drink.register(new PlaceholderCommands(), "cope", "cp", "customplaceholders", "customplaceholder");

        drink.registerCommands();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderRegistration(this).register();
        }
    }

    @Override
    public void onDisable() {
        mongoManager.shutdown();

        instance = null;
    }
}
