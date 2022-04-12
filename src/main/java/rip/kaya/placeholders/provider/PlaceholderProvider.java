package rip.kaya.placeholders.provider;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rip.kaya.placeholders.PlaceholderPlugin;

@RequiredArgsConstructor
public class PlaceholderProvider extends PlaceholderExpansion {

    private final PlaceholderPlugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "cp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "kayalust";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {

        if (player == null) {
            return "";
        }

        return plugin.getPlaceholderManager().getPlaceholderByName(identifier).getToDisplay(player.getUniqueId());
    }
}
