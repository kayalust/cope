package rip.kaya.placeholders;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class Placeholder {
    private final PlaceholderPlugin plugin = PlaceholderPlugin.getInstance();

    private String name;
    private Object defaultValue = "defaultValue";

    private List<Player> playersToCheck = Lists.newArrayList();

    public Placeholder(String name) {
        this.name = name;
    }

    /* Playerdata Map */
    private final HashMap<UUID, Object> data = Maps.newHashMap();

    public void save() {
        MongoCollection<Document> collection = plugin.getMongoManager().getData();
        Document document = new Document();

        document.put("_id", name);
        document.put("defaultValue", defaultValue);

        Document dataDocument = new Document();

        for (Map.Entry<UUID, Object> entry : data.entrySet()) {
            dataDocument.put(entry.getKey().toString(), entry.getValue());
        }
        document.put("data", dataDocument);

        plugin.getMongoManager().getThread().execute(() -> collection.replaceOne(Filters.eq("_id", name), document, new ReplaceOptions().upsert(true)));
        plugin.getPlaceholderManager().getPlaceholders().add(this);
    }

    public void delete() {
        MongoCollection<Document> collection = plugin.getMongoManager().getData();

        plugin.getMongoManager().getThread().execute(() -> collection.findOneAndDelete(Filters.eq("_id", name)));
        plugin.getPlaceholderManager().getPlaceholders().removeIf(p -> p.getName().equals(name));
    }

    public void setValue(Player player, Object value) {
        data.replace(player.getUniqueId(), value);
        this.save();
    }

    public Object getPlayerValue(Player player) {
        return data.get(player.getUniqueId());
    }

    public String getToDisplay(Player player) {
        if (playersToCheck.contains(player)) {
            return this.getPlayerValue(player).toString();
        } else {
            return defaultValue.toString();
        }
    }
}
