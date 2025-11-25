package me.moritz.events.proxy;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import me.moritz.Main;

public class Shutdown {

    private Main main;

    public Shutdown(Main main) {
        this.main = main;
    }

    @Subscribe
    public void onShutdown(final ProxyShutdownEvent e) {
        if (main.getClient() != null) {
            main.getClient().close();

            main.getLogger().error("Closing the MongoDB Connection!");
        }
    }

}
