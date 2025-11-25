package me.moritz;

import com.google.inject.Inject;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import me.moritz.commands.CommandUtilityManager;
import me.moritz.commands.player.CoinsCommand;
import me.moritz.commands.player.RankCommand;
import me.moritz.database.MongoManager;
import me.moritz.events.connection.PostLogin;
import me.moritz.events.connection.PreLogin;
import me.moritz.events.proxy.Ping;
import me.moritz.events.proxy.Shutdown;
import org.bson.Document;
import org.slf4j.Logger;

@Plugin(id = "proxy", name = "proxy", version = BuildConstants.VERSION)
public class Main {

    private final ProxyServer proxyServer;

    @Getter
    private final Logger logger;

    /* MongoDB */
    @Getter
    private MongoClient client;

    @Getter
    private MongoManager mongoManager;

    @Getter
    private MongoDatabase database;
    @Getter
    private MongoCollection<Document> collection;

    /* Commands */
    @Getter
    private CommandManager commandManager;
    @Getter
    private CommandUtilityManager commandUtilityManager;

    @Inject
    public Main(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        /* Sending a message */
        logger.warn("Plugin started!");

        /* Register listeners/events */
        registerListeners();

        /* MongoDB */
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        /* Connection string for MongoDB */
        Dotenv dotenv = Dotenv
                .load();

        String CONNECTION_STRING = dotenv.get("CONNECTION_STRING");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        try  {
            client = MongoClients.create(settings);

            database = client.getDatabase("mc-server");
            collection = database.getCollection("playerData");

            mongoManager = new MongoManager(this);

            logger.warn("Pinged your deployment. You successfully connected to MongoDB!");

        } catch (MongoException e) {

            logger.error("MongoDB Exception! Failed to connect!");

        }

        /* Command */
        commandManager = proxyServer.getCommandManager();
        commandUtilityManager = new CommandUtilityManager(this);

        /* Commands */
        commandManager.register(commandUtilityManager.meta("coins", "c"), new CoinsCommand(this));
        commandManager.register(commandUtilityManager.meta("rank", "r"), new RankCommand(this));
    }

    private void registerListeners() {
        /* proxy */
        proxyServer.getEventManager().register(this, new Ping());
        proxyServer.getEventManager().register(this, new Shutdown(this));

        /* connection */
        proxyServer.getEventManager().register(this, new PreLogin(this));
        proxyServer.getEventManager().register(this, new PostLogin(this));
    }

}
