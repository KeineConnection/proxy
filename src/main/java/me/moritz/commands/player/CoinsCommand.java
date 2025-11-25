package me.moritz.commands.player;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.moritz.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;

import java.util.List;

/**
 * CoinCommand
 *
 * @author Moritz Schumann
 * @since 20.11.2025
 */
public class CoinsCommand implements SimpleCommand {

    private Main main;

    public CoinsCommand(Main main) {
        this.main = main;
    }

    @Override
    public void execute(final Invocation invocation) {

        final Player source = (Player) invocation.source();
        final String uuid = source.getUniqueId().toString();

        String[] args = invocation.arguments();

        if (args.length != 0) {
            /*
             TODO: update logic    - with permission (proxy.coins.update)
                /coins update <userName> <amountOfCoins>

             TODO: add logic       - with permission (proxy.coins.add)
                /coins add <userName> <amountOfCoins>

             TODO: remove logic    - with permission (proxy.coins.remove)
                /coins remove <userName> <amountOfCoins>

             TODO: pay logic       - without permission
                /coins pay <userName> <amountOfCoins>
            */

            if (main.getMongoManager().getRank(uuid).equals("STAFF")) {

                switch (args[0]) {

                    case "update":
                        source.sendMessage(
                                MiniMessage.miniMessage().deserialize(
                                        "typed update"
                                )
                        );

                        break;
                    case "add":
                        source.sendMessage(
                                MiniMessage.miniMessage().deserialize(
                                        "type add"
                                )
                        );

                        break;
                    case "remove":
                        source.sendMessage(
                                MiniMessage.miniMessage().deserialize(
                                        "type remove"
                                )
                        );

                        break;
                    case "pay":
                        source.sendMessage(
                                MiniMessage.miniMessage().deserialize(
                                        "type pay"
                                )
                        );

                        break;
                    default:
                        source.sendMessage(
                                MiniMessage.miniMessage().deserialize(
                                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray>ᴡʀᴏɴɢ ᴜѕᴀɢᴇ!</gray>"
                                )
                        );
                        break;

                }

            } else source.sendMessage(
                    MiniMessage.miniMessage().deserialize(
                            "<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <gray>ʏᴏᴜ’ʀᴇ <b><red>ɴᴏᴛ </red></b>ᴀʟʟᴏᴡᴇᴅ</gray>"
                    )
            );

        } else source.sendMessage(
                MiniMessage.miniMessage().deserialize(
                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray><coins></gray>",

                        Formatter.number("coins", main.getMongoManager().getCoins(uuid))
                )
        );

    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of("update", "remove", "add", "pay");
    }

}
