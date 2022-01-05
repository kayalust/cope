package rip.kaya.placeholders.managers;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import rip.kaya.placeholders.Placeholder;
import rip.kaya.placeholders.PlaceholderPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PlaceholderManager {

    private final PlaceholderPlugin plugin;

    private final List<Placeholder> placeholders = Lists.newArrayList();

    public void init() {
        FindIterable<Document> cursor = plugin.getMongoManager().getData().find();

        for (Document document : cursor) {
            Placeholder placeholder = new Placeholder(document.getString("name"));

            placeholder.setDefaultValue(document.getString("defaultValue"));

            Document dataDocument = (Document) document.get("data");

            for (Map.Entry<String, Object> entry : dataDocument.entrySet()) {
                placeholder.getData().put(UUID.fromString(entry.getKey()), entry.getValue());
            }
        }
    }

    public Placeholder getPlaceholderByName(String name) {
        return placeholders.stream().filter(placeholder -> placeholder.getName().equals(name)).findFirst().orElse(null);
    }
}