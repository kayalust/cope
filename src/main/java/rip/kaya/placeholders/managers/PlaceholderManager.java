package rip.kaya.placeholders.managers;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import com.mongodb.client.FindIterable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bukkit.entity.Player;
import rip.kaya.placeholders.Placeholder;
import rip.kaya.placeholders.PlaceholderPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class PlaceholderManager {

    private final PlaceholderPlugin plugin;

    @Getter private final Map<String, Placeholder> placeholders = new HashMap<>();

    public void init() {
        FindIterable<Document> iterable = plugin.getMongoManager().getData().find();

        for (Document document : iterable) {
            Placeholder placeholder = new Placeholder(document.getString("_id"));

            placeholder.setDefaultValue(document.getString("defaultValue"));

            Document dataDocument = (Document) document.get("data");

            for (Map.Entry<String, Object> entry : dataDocument.entrySet()) {
                placeholder.getData().put(UUID.fromString(entry.getKey()), entry.getValue().toString());
            }

            plugin.getLogger().info("Successfully loaded placeholder " + placeholder.getName() + " with " + dataDocument.size() + " entries");
            placeholders.put(placeholder.getName(), placeholder);
        }
    }

    public void loadPlayerData(Player player) {
        AtomicReference<FindIterable<Document>> iterable = new AtomicReference<>();
        ForkJoinPool.commonPool().execute(() -> iterable.set(plugin.getMongoManager().getData().find()));

        if (iterable.get() == null) {
            plugin.getLogger().warning("There are no placeholders setup! Please consider setting up one!");
            return;
        }

        for (Document document : iterable.get()) {
            Document dataDocument = (Document) document.get("data");

            for (Placeholder placeholder : placeholders.values()) {
                if (dataDocument.getString(player.getUniqueId().toString()) == null) {
                    dataDocument.put(player.getUniqueId().toString(), placeholder.getDefaultValue());
                } else {
                    placeholder.getData().replace(player.getUniqueId(), dataDocument.getString(player.getUniqueId().toString()));
                }
            }

            plugin.getLogger().info("Successfully loaded " + player.getName() +"'s data from the database");
        }


        /*
        FindIterable<Document> iterable = plugin.getMongoManager().getData().find();

        for (Document document : iterable) {
            Document dataDocument = (Document) document.get("data");

            for (Placeholder placeholder : placeholders.values()) {
                if (dataDocument.getString(player.getUniqueId().toString()) == null) {
                    dataDocument.put(player.getUniqueId().toString(), placeholder.getDefaultValue());
                } else {
                    placeholder.getData().replace(player.getUniqueId(), dataDocument.getString(player.getUniqueId().toString()));
                }
            }

            plugin.getLogger().info("Successfully loaded " + player.getName() +"'s data from the database");
        }
         */
    }

    public Placeholder getPlaceholderByName(String name) {
        return placeholders.get(name);
    }
}