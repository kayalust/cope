package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;

@Getter @Setter
@RequiredArgsConstructor
public class Placeholder {

    private final PlaceholderPlugin plugin = PlaceholderPlugin.getInstance();

    private final String name;
    private Object defaultValue = "defaultValue";

    /* Playerdata Map */
    private final HashMap<UUID, String> data = new HashMap<>();

    public void save() {
        final MongoCollection<Document> collection = plugin.getMongoManager().getData();
        final Document document = new Document();

        document.put("_id", name);
        document.put("defaultValue", defaultValue.toString());

        final Document dataDocument = new Document();

        for (Map.Entry<UUID, String> entry : data.entrySet()) {
            dataDocument.put(entry.getKey().toString(), entry.getValue());
        }

        document.put("data", dataDocument);

        ForkJoinPool.commonPool().execute(() -> collection.replaceOne(Filters.eq("_id", name), document, new ReplaceOptions().upsert(true)));
        plugin.getPlaceholderManager().getPlaceholders().put(name, this);
    }

    public void delete() {
        final MongoCollection<Document> collection = plugin.getMongoManager().getData();

        ForkJoinPool.commonPool().execute(() -> collection.findOneAndDelete(Filters.eq("_id", name)));
        plugin.getPlaceholderManager().getPlaceholders().remove(name);
    }

    public void setValue(UUID uuid, String value) {
        if (data.containsKey(uuid)) {
            data.replace(uuid, value);
        } else {
            data.put(uuid, value);
        }

        this.save();
    }

    public void setValue(Player player, String value) {
        if (data.containsKey(player.getUniqueId())) {
            data.replace(player.getUniqueId(), value);
        } else {
            data.put(player.getUniqueId(), value);
        }

        this.save();
    }

    public String getPlayerValue(Player player) {
        return (data.get(player.getUniqueId()) == null ? defaultValue.toString() : data.get(player.getUniqueId()));
    }

    public String getPlayerValue(UUID uuid) {
        return (data.get(uuid) == null ? defaultValue.toString() : data.get(uuid));
    }

    public String getToDisplay(UUID uuid) {
        if (data.containsKey(uuid)) {
            return this.getPlayerValue(uuid);
        } else {
            return defaultValue.toString();
        }
    }
}
