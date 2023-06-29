package ru.dest.library.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.AbstractCommand;
import ru.dest.library.command.CommandData;

import java.util.List;

public abstract class PlayerOnlyCommand<T extends JavaPlugin> extends AbstractCommand<T> {

    public PlayerOnlyCommand(T t, String name, String usage) {
        super(t, name, usage);
    }

    public PlayerOnlyCommand(T t, String name, String usage, List<String> aliases) {
        super(t, name, usage, aliases);
    }

    public PlayerOnlyCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usage, @NotNull List<String> aliases) {
        super(plugin, name, description, usage, aliases);
    }

    @Override
    public final void perform(CommandSender sender, CommandData data, String[] args) {
        if(!(sender instanceof Player)){
            Library.getInstance().getPlayerOnlyMessage().send(data.getSender());
            return;
        }

        this.perform((Player) sender, data, args);
    }

    public abstract void perform(Player sender, CommandData data, String[] args);
}
