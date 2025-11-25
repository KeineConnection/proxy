package me.moritz.events.proxy;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Ping {

    @Subscribe
    public void onPing(final ProxyPingEvent e) {

        /* Getting the builder for our Server-ping */
        ServerPing.Builder builder = e.getPing().asBuilder();

        /* Creating our Version */
        ServerPing.Version version = new ServerPing.Version(
                -1,
                "ᴍᴀɪɴᴛᴇɴᴀɴᴄᴇ"
        );

        /* Setting up our MOTD */
        builder.description(
                MiniMessage.miniMessage().deserialize(
                        "               <red><bold>ᴅᴇᴠᴇʟᴏᴘᴍᴇɴᴛ <gray>-</gray> ѕᴇʀᴠᴇʀ</red>\n" +
                                "            <gray>ᴄᴜʀʀᴇɴᴛʟʏ ᴜɴᴅᴇʀ ᴄᴏɴѕᴛʀᴜᴄᴛɪᴏɴ</gray>"
                )
        );

        /* Setting up our Version */
        builder.version(version);

        /* Building process */
        e.setPing(builder.build());

    }

}
