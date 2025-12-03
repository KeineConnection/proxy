package me.moritz.commands.player;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.moritz.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Optional;

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

        if (args.length >= 1) {
            /*
             TODO: update logic    - with permission (proxy.coins.update)
                /coins update <userName> <amountOfCoins>

             TODO: remove logic    - with permission (proxy.coins.remove)
                /coins remove <userName> <amountOfCoins>

             TODO: pay logic       - without permission
                /coins pay <userName> <amountOfCoins>
            */

            switch (args[0]) {

                /*
                    Without a valid player, update own coins
                    /coins update <amount>
                        -> Future

                    With a valid player
                    /coins update <player> <amount>
                    /coins args[0] args[1]  args[2]

                 */
                case "update":
                    if (main.getMongoManager().getRank(uuid).equals("STAFF")) {



                    } else source.sendMessage(main.getPERMISSION());

                    break;

                /*
                    /coins add     <player> <amount>
                    /coins args[0] args[1]  args[2]
                 */
                case "add":
                    if (main.getMongoManager().getRank(uuid).equals("STAFF")) {
                        if (args.length >= 2) {

                            Optional<Player> p = main.getProxyServer().getPlayer(args[1]);

                            if (p.isPresent()) {

                                if (args.length >= 3) {

                                    try {

                                        int coinsToAdd = Integer.parseInt(args[2]);

                                        // Here we can add the number of coins to the given player
                                        main.getMongoManager().increaseCoins(p.get().getUniqueId().toString(), coinsToAdd);

                                        // Sending the command executer a message with the amount and the players name
                                        source.sendMessage(
                                                MiniMessage.miniMessage().deserialize(
                                                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray><b><yellow><player></yellow></b> ʀᴇᴄᴇɪᴠᴇᴅ <b><green><coins> </green></b>ᴄᴏɪɴѕ</gray>",
                                                        Placeholder.parsed("player", p.get().getUsername()),
                                                        Formatter.number("coins", coinsToAdd)
                                                )
                                        );

                                        // Sending to receiver a message with the amount of coins
                                        p.get().sendMessage(
                                                MiniMessage.miniMessage().deserialize(
                                                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray>ʏᴏᴜ ʀᴇᴄᴇɪᴠᴇᴅ <b><green><coins> </green></b>ᴄᴏɪɴѕ</gray>",
                                                        Formatter.number("coins", coinsToAdd)
                                                )
                                        );

                                    } catch (NumberFormatException e) {

                                        source.sendMessage(
                                                MiniMessage.miniMessage().deserialize(
                                                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray>ʏᴏᴜ <dark_gray><b>ᴅɪᴅɴ’ᴛ</b></dark_gray> ᴇɴᴛᴇʀ ᴀ <b><green>ɴᴜᴍʙᴇʀ</green></b>.</gray>"
                                                )
                                        );

                                    }

                                } else source.sendMessage(
                                        MiniMessage.miniMessage().deserialize(
                                                "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray>ᴡʀᴏɴɢ ᴜѕᴀɢᴇ! ᴜѕᴇ /<b>ᴄᴏɪɴѕ ᴀᴅᴅ </b><<yellow><b>ᴜѕᴇʀɴᴀᴍᴇ</b></yellow>> <<b><green>ᴀᴍᴏᴜɴᴛ</green></b>>"
                                        )
                                );

                            } else source.sendMessage(
                                    MiniMessage.miniMessage().deserialize(
                                            "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray><b><yellow>ᴘʟᴀʏᴇʀ </yellow></b><b><dark_gray>ɴᴏᴛ </dark_gray></b>ᴏɴʟɪɴᴇ</gray>"
                                    )
                            );

                        } else source.sendMessage(
                                MiniMessage.miniMessage().deserialize(
                                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <gray>ᴡʀᴏɴɢ ᴜѕᴀɢᴇ! ᴜѕᴇ /<b>ᴄᴏɪɴѕ ᴀᴅᴅ </b><<yellow><b>ᴜѕᴇʀɴᴀᴍᴇ</b></yellow>> <<b><green>ᴀᴍᴏᴜɴᴛ</green></b>>"
                                )
                        );

                    // Permission denied
                    } else source.sendMessage(main.getPERMISSION());

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
                        "<b><red>ᴘʀᴏxʏ</red></b> <gray>ᴄᴏɪɴѕ</gray> <dark_gray>•</dark_gray> <b><green><coins></green></b>",

                        Formatter.number("coins", main.getMongoManager().getCoins(uuid))
                )
        );

    }

    // TODO: Check args.length to return a list for every length
    /*
    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of("update", "remove", "add", "pay");
    }

     */

}
