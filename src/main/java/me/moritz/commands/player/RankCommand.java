package me.moritz.commands.player;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.moritz.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Optional;

/**
 * RankCommand
 *
 * @author Moritz Schumann
 * @since 24.11.2025
 */
public class RankCommand implements SimpleCommand {

    private Main main;

    public RankCommand(Main main) {
        this.main = main;
    }

    /*
        TODO: Setup a rank command
            /rank <userName?> <rank?>
    */
    @Override
    public void execute(Invocation invocation) {

        final Player source = (Player) invocation.source();
        final String uuid = source.getUniqueId().toString();

        String[] args = invocation.arguments();

        /*
            OWNER -> Aqua
            STAFF -> Green
            PLAYER -> Gray
         */
        if (args.length != 0) {

            if (main.getMongoManager().getRank(uuid).equals("OWNER")) {

                if (args.length == 2) {

                    Optional<Player> p = main.getProxyServer().getPlayer(args[0]);

                    if (p.isPresent()) {

                        if (args[1].equalsIgnoreCase("OWNER") || args[1].equalsIgnoreCase("STAFF") || args[1].equalsIgnoreCase("PLAYER")) {

                            Player playerToUpdate = p.get();

                            String rank = args[1].toUpperCase();

                            main.getMongoManager().updateRank(playerToUpdate.getUniqueId().toString(), rank);

                            playerToUpdate.disconnect(
                                    MiniMessage.miniMessage().deserialize("<red>ʏᴏᴜʀ ʀᴀɴᴋ ᴡᴀѕ ᴜᴘᴅᴀᴛᴇᴅ</red>")
                            );

                            switch (rank) {
                                case "OWNER" -> source.sendMessage(main.getPREFIX().append(
                                        MiniMessage.miniMessage().deserialize("<gray>ᴜᴘᴅᴀᴛᴇᴅ <b><aqua>ʀᴀɴᴋ</aqua></b> ꜰʀᴏᴍ <player></gray>",
                                                Placeholder.parsed("player", playerToUpdate.getUsername()))
                                ));
                                case "STAFF" -> source.sendMessage(main.getPREFIX().append(
                                        MiniMessage.miniMessage().deserialize("<gray>ᴜᴘᴅᴀᴛᴇᴅ <b><green>ʀᴀɴᴋ</green></b> ꜰʀᴏᴍ <player></gray>",
                                                Placeholder.parsed("player", playerToUpdate.getUsername()))
                                ));
                                case "PLAYER" -> source.sendMessage(main.getPREFIX().append(
                                        MiniMessage.miniMessage().deserialize("<gray>ᴜᴘᴅᴀᴛᴇᴅ <b>ʀᴀɴᴋ</b> ꜰʀᴏᴍ <player></gray>",
                                                Placeholder.parsed("player", playerToUpdate.getUsername()))
                                ));


                            }

                        } else source.sendMessage(main.getPREFIX().append(
                                MiniMessage.miniMessage().deserialize("<gray>ʀᴀɴᴋ ɴᴏᴛ ꜰᴏᴜɴᴅ, ᴜѕᴇ <b><aqua>ᴏᴡɴᴇʀ</aqua></b>, <b><green>ѕᴛᴀꜰꜰ</green></b>, <b>ᴘʟᴀʏᴇʀ</b></gray>")
                        ));

                    } else source.sendMessage(main.getPREFIX().append(
                            MiniMessage.miniMessage().deserialize("<gray>ᴘʟᴀʏᴇʀ ɴᴏᴛ ᴏɴʟɪɴᴇ</gray>")
                    ));

                } else source.sendMessage(main.getPREFIX().append(
                        MiniMessage.miniMessage().deserialize("<gray/>> ᴜѕᴀɢᴇ: <b>/ʀᴀɴᴋ <player> <ʀᴀɴᴋ></b></gray>")
                ));

            } else source.sendMessage(main.getPERMISSION());

        } else {

            switch (main.getMongoManager().getRank(uuid)) {

                case "OWNER":
                    source.sendMessage(
                            MiniMessage.miniMessage().deserialize(
                                    "<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <b><aqua>ᴏᴡɴᴇʀ</aqua></b>"
                            )
                    );
                    break;
                case "STAFF":
                    source.sendMessage(
                            MiniMessage.miniMessage().deserialize(
                                    "<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <b><green>ѕᴛᴀꜰꜰ</green></b>"
                            )
                    );
                    break;
                case "PLAYER":
                    source.sendMessage(
                            MiniMessage.miniMessage().deserialize(
                                    "<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <gray><b>ᴘʟᴀʏᴇʀ</b></gray>"
                            )
                    );
                    break;
                default:
                    source.sendMessage(
                            MiniMessage.miniMessage().deserialize(
                                    "<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <gray>ᴀɴ ᴇʀʀᴏʀ ᴏᴄᴄᴜʀʀᴇᴅ. ᴘʟᴇᴀѕᴇ ɴᴏᴛɪꜰʏ <light_purple><b>ѕᴛᴀꜰꜰ</b></light_purple>.</gray>"
                            )
                    );
                    break;

            }

        }

    }

}
