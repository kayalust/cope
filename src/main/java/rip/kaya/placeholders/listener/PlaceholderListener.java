package rip.kaya.placeholders.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import rip.kaya.placeholders.PlaceholderPlugin;

/*
 * Property of kayalust © 2022
 * Project: cope
 */
@RequiredArgsConstructor
public class PlaceholderListener implements Listener {

    private final PlaceholderPlugin plugin;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        // loads data from db, in case the data it has been changed (useful for syncing values between server)
        plugin.getPlaceholderManager().loadPlayerData(player);
    }
}
