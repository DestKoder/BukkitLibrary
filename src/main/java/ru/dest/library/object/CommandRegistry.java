package ru.dest.library.object;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.command.AbstractCommand;
import ru.dest.library.command.ConfigCommand;
import ru.dest.library.exception.CommandNotFoundException;

import java.lang.reflect.Field;

/**
 * This Class is designed to register various types of commands. <br>
 *
 * An object of this class has already been created for each BukkitPlugin, you can get it via BukkitPlugin.commandRegistry();
 * @param <T> - your main class
 *
 * @since 1.0
 * @author DestKoder
 */
public final class CommandRegistry<T extends JavaPlugin> {
    private final JavaPlugin plugin;
    private CommandMap map;

    public CommandRegistry(@NotNull final JavaPlugin plugin) throws Exception {
        this.plugin = plugin;
        Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);

        map = (CommandMap) field.get(Bukkit.getServer());
    }

    /**
     * Method for registering runtime commands
     * @param cmd {@link AbstractCommand} to register
     */
    public void register(@NotNull final AbstractCommand<T> cmd){
        map.register(plugin.getDescription().getName(), cmd);
    }

    /**
     * Method for registering several {@link AbstractCommand} in one call
     * @param commands list of commands to register
     */
    @SafeVarargs
    public final void register(AbstractCommand<T> @NotNull ... commands){
        for (AbstractCommand<T> cmd : commands) {
            this.register(cmd);
        }
    }


    /**
     * Method for registering plugin.yml-command
     * @param command {@link ConfigCommand} command to register.
     */
    public final void register(@NotNull final ConfigCommand<T> command){
        PluginCommand cmd = plugin.getCommand(command.getAliases().get(0));

        if(cmd == null) {
            throw new CommandNotFoundException(command.getAliases().get(0));
        }

        cmd.setExecutor(command);
        if(command instanceof TabCompleter) cmd.setTabCompleter((TabCompleter) command);
    }

    /**
     * Method for registering several plugin.yml-commands
     * @param commands - list of {@link ConfigCommand} to register;
     */
    @SafeVarargs
    public final void registerCommands(ConfigCommand<T>... commands){
        if(commands == null) return;

        for(ConfigCommand<T> info : commands){
            this.register(info);
        }
    }
}
