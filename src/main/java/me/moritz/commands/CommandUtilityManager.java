package me.moritz.commands;

import com.velocitypowered.api.command.CommandMeta;
import me.moritz.Main;

/**
 * CommandManager
 *
 * @author Moritz Schumann
 * @since 20.11.2025
 */
public class CommandUtilityManager {

    private Main main;

    public CommandUtilityManager(Main main) {
        this.main = main;
    }

    public CommandMeta meta(String name, String... aliases) {
        return main.getCommandManager().metaBuilder(name)
                .aliases(aliases)
                .plugin(main)
                .build();
    }

}
