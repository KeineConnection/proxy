package me.moritz.database;

import com.mongodb.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import me.moritz.Main;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * MongoManager
 *
 * @author Moritz Schumann
 * @since 20.11.2025
 */
public class MongoManager {

    private final Main main;

    public MongoManager(Main main) {
        this.main = main;
    }

    /*
        UUID,
        Name,
        Rank,
        Coins,
        Kills,
        Deaths

        Method to create a new Player
    */
    public void createPlayer(String uuid, String name, String rank, int coins, int kills, int deaths) {
        Document playerProfile = new Document("uuid", uuid)
                .append("name", name)
                .append("rank", rank)
                .append("coins", coins)
                .append("kills", kills)
                .append("deaths", deaths);

        try {
            InsertOneResult playerProfileResult = main.getCollection().insertOne(playerProfile);

            System.out.println("Successfully created an player profile!");
        } catch (MongoException e) {
            System.out.println("Error while creating an player profile!");
        }
    }

    /* Method to check if the player is in our DB */
    public boolean checkIfPlayerExists(String uuid) {
        Bson filter = Filters.eq("uuid", uuid);

        try {
            Document player = main.getCollection().find(filter).first();

            return player != null;

        } catch (MongoException e) {
            System.out.println("Error while filtering players.");

            return false;
        }

    }

    /* Getter methods
     *
     * TODO: Coins   ✓
     * TODO: Deaths  ✓
     * TODO: Kills   ✓
     * TODO: Rank    ✓
     *
     */
    public int getCoins(String uuid) {
        Bson filter = Filters.eq("uuid", uuid);
        Document playerCoins = main.getCollection().find(filter).first();

        if (playerCoins != null) {
            return playerCoins.getInteger("coins");
        }

        return 0;
    }

    public int getDeaths(String uuid) {
        Bson filter = Filters.eq("uuid", uuid);
        Document playerDeaths = main.getCollection().find(filter).first();

        if (playerDeaths != null) {
            return playerDeaths.getInteger("deaths");
        }

        return 0;
    }

    public int getKills(String uuid) {
        Bson filter = Filters.eq("uuid", uuid);
        Document playerKills = main.getCollection().find(filter).first();

        if (playerKills != null) {
            return playerKills.getInteger("kills");
        }

        return 0;
    }

    // Player, Staff
    public String getRank(String uuid) {
        Bson filter = Filters.eq("uuid", uuid);
        Document playerRank = main.getCollection().find(filter).first();

        if (playerRank != null) {
            return playerRank.getString("rank");
        }

        return "player";
    }

    /* UPDATE methods
     *
     * TODO: Coins    ✓
     * TODO: Deaths
     * TODO: Kills
     * TODO: Rank
     *
     */
    public void updateCoins(String uuid, int coins) {
        Bson filter = Filters.eq("uuid", uuid);
        Bson update = Updates.set("coins", coins);

        main.getCollection().findOneAndUpdate(filter, update);
    }

    /* INCREASE methods
     *
     * TODO: Coins    ✓
     * TODO: Deaths
     * TODO: Kills
     *
     */
    public void increaseCoins(String uuid, int coins) {
        Bson filter = Filters.eq("uuid", uuid);
        Bson update = Updates.inc("coins", coins);

        main.getCollection().findOneAndUpdate(filter, update);
    }
}
