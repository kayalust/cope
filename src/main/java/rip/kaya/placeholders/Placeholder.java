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
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class Placeholder {
    private final PlaceholderPlugin plugin = PlaceholderPlugin.getInstance();

    private String name;
    private Object defaultValue = "defaultValue";

    public Placeholder(String name) {
        this.name = name;
    }

    /* Playerdata Map */
    private final HashMap<UUID, String> data = Maps.newHashMap();

    public void save() {
        MongoCollection<Document> collection = plugin.getMongoManager().getData();
        Document document = new Document();

        document.put("_id", name);
        document.put("defaultValue", defaultValue.toString());

        Document dataDocument = new Document();

        for (Map.Entry<UUID, String> entry : data.entrySet()) {
            dataDocument.put(entry.getKey().toString(), entry.getValue());
        }

        document.put("data", dataDocument);

        plugin.runAsync(() -> collection.replaceOne(Filters.eq("_id", name), document, new ReplaceOptions().upsert(true)));
        plugin.getPlaceholderManager().getPlaceholders().put(name, this);
    }

    public void delete() {
        MongoCollection<Document> collection = plugin.getMongoManager().getData();

        plugin.runAsync(() -> collection.findOneAndDelete(Filters.eq("_id", name)));
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
        return (data.get(player.getUniqueId()) == null ? "0" : data.get(player.getUniqueId()));
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
