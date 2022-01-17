package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/5/2022
*/

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class PlaceholderRegistration extends PlaceholderExpansion {

    private final PlaceholderPlugin plugin;

    @Override
    public String getIdentifier() {
        return "cp";
    }

    @Override
    public String getAuthor() {
        return "kayalust";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return "";
        }

        return plugin.getPlaceholderManager().getPlaceholderByName(identifier).getToDisplay(player);
    }
}
