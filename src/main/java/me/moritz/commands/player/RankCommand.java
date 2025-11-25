package me.moritz.commands.player;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.moritz.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;

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
            /rank <set?> <userName?>
    */
    @Override
    public void execute(Invocation invocation) {

        final Player source = (Player) invocation.source();
        final String uuid = source.getUniqueId().toString();

        String[] args = invocation.arguments();

        if (args.length != 0) {

            if (main.getMongoManager().getRank(uuid).equals("STAFF")) {



            } else source.sendMessage(
                    MiniMessage.miniMessage().deserialize("<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <gray>ʏᴏᴜ’ʀᴇ <b><red>ɴᴏᴛ </red></b>ᴀʟʟᴏᴡᴇᴅ</gray>")
            );

        } else {

            switch (main.getMongoManager().getRank(uuid)) {

                case "STAFF":
                    source.sendMessage(
                            MiniMessage.miniMessage().deserialize(
                                    "<b><red>ᴘʀᴏxʏ</red></b> <gray>ʀᴀɴᴋ</gray> <dark_gray>•</dark_gray> <light_purple><b>ѕᴛᴀꜰꜰ</b></light_purple>"
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
