package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.kaya.placeholders.command.PlaceholderCommands;
import rip.kaya.placeholders.command.api.CommandService;
import rip.kaya.placeholders.command.api.Drink;
import rip.kaya.placeholders.listener.PlaceholderListener;
import rip.kaya.placeholders.managers.MongoManager;
import rip.kaya.placeholders.managers.PlaceholderManager;
import rip.kaya.placeholders.provider.PlaceholderParamProvider;
import rip.kaya.placeholders.provider.PlaceholderProvider;

@Getter
public class PlaceholderPlugin extends JavaPlugin {

    @Getter
    private static PlaceholderPlugin instance;

    private MongoManager mongoManager;
    private PlaceholderManager placeholderManager;

    @Override
    public void onLoad() {
        instance = this;

        getConfig().options().copyDefaults();
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.mongoManager = new MongoManager(this);
        mongoManager.init();

        this.placeholderManager = new PlaceholderManager(this);
        placeholderManager.init();

        final CommandService drink = Drink.get(this);

        drink.bind(Placeholder.class).toProvider(new PlaceholderParamProvider());
        drink.register(new PlaceholderCommands(this), "cope", "cp");
        drink.registerCommands();

        Bukkit.getPluginManager().registerEvents(new PlaceholderListener(this), this);

        new PlaceholderProvider(this).register();
    }

    @Override
    public void onDisable() {
        placeholderManager.getPlaceholders().values().forEach(Placeholder::save);
        mongoManager.shutdown();

        instance = null;
    }
}
