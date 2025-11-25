package me.moritz.events.connection;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import me.moritz.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class PreLogin {

    private Main main;

    public PreLogin(Main main) {
        this.main = main;
    }

    @Subscribe
    public void onPreLogin(final PreLoginEvent e) {

        final String uuid = e.getUniqueId().toString();

        if (!main.getMongoManager().getRank(uuid).equals("STAFF")) {
            e.setResult(
                    PreLoginEvent.PreLoginComponentResult.denied(
                            MiniMessage.miniMessage().deserialize("<gray>Currently under construction</gray>")
                    )
            );
        }

    }

}
