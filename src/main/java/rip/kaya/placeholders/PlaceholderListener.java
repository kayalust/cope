package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlaceholderListener implements Listener {

    private final PlaceholderPlugin plugin = PlaceholderPlugin.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Placeholder placeholder : plugin.getPlaceholderManager().getPlaceholders()) {
            placeholder.getPlayersToCheck().add(player);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        for (Placeholder placeholder : plugin.getPlaceholderManager().getPlaceholders()) {
            placeholder.getPlayersToCheck().removeIf(p -> p.getUniqueId().equals(player.getUniqueId()));
        }
    }
}
