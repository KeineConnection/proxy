package me.moritz.events.connection;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.moritz.Main;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class PostLogin {

    private Main main;

    public PostLogin(Main main) {
        this.main = main;
    }

    @Subscribe
    public void onPostLogin(final PostLoginEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (!main.getMongoManager().checkIfPlayerExists(uuid.toString())) {
            main.getMongoManager().createPlayer(uuid.toString(), player.getUsername(), "PLAYER", 0, 0, 0);
        }

        // TODO: Show an action with following text: "Stats loading..."
        player.sendActionBar(
                Component.text("Action Bar")
        );

        /*
            TODO: Create the tablist header & footer

        player.sendPlayerListHeaderAndFooter(

        );
        */

    }

}
