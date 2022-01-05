package rip.kaya.placeholders.managers;

/*
    โค้ดโดย kayalust @ Refine Development
    โปรเจ็ก: Placeholders
    วันที่: 1/4/2022
*/

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import rip.kaya.placeholders.PlaceholderPlugin;

@Getter
@RequiredArgsConstructor
public class MongoManager {

    private final PlaceholderPlugin plugin;

    private MongoClient client;
    private MongoDatabase database;

    private MongoCollection<Document> data;

    public void init() {
        this.client = MongoClients.create(plugin.getConfig().getString("mongo.uri"));
        this.database = client.getDatabase(plugin.getConfig().getString("mongo.database"));

        plugin.getLogger().info("Successfully initiated a connection to the database!");

        this.loadCollections();

        plugin.getLogger().info("Successfully retrieved the data collection!");
    }

    public void loadCollections() {
        data = this.database.getCollection("data");
    }

    public void shutdown() {
        this.client.close();
    }
}
