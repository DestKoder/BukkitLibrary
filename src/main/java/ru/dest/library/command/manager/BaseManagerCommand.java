package ru.dest.library.command.manager;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.command.AbstractCommand;
import ru.dest.library.command.CommandData;
import ru.dest.library.command.ICommand;
import ru.dest.library.exception.SubCommandExistsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseManagerCommand<T extends JavaPlugin> extends AbstractCommand<T> implements ManagerCommand<T> {

    private final List<ICommand<T>> subCommands = new ArrayList<>();

    public BaseManagerCommand(T plugin, String name, String usage) {
        super(plugin, name, usage);
    }

    public BaseManagerCommand(T plugin, String name, String usage, List<String> aliases) {
        super(plugin, name, usage, aliases);
    }

    public BaseManagerCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(plugin, name, description, usageMessage, aliases);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        List<String> toReturn = new ArrayList<>();
        System.out.println(args.length);
        System.out.println(Arrays.toString(args));
        if(args.length == 1){
            for(ICommand<T> cmd : subCommands){
                toReturn.addAll(cmd.getAliases());
                if(!toReturn.contains(cmd.getName())) toReturn.add(cmd.getName());
            }
            return toReturn;
        }
        ICommand<T> sub = getCommandByName(args[0]);
        if (sub instanceof TabCompleter || sub instanceof AbstractCommand) {
            String[] arguments = new String[args.length - 1];

            System.arraycopy(args, 1, arguments, 0, args.length - 1);

            if (sub instanceof TabCompleter)
                toReturn.addAll(Objects.requireNonNull(((TabCompleter) sub).onTabComplete(sender, this, alias, arguments)));
            else toReturn.addAll(((AbstractCommand<T>) sub).tabComplete(sender, alias, arguments));
        }

        return toReturn;

    }

    @Override
    public void addSubCommand(ICommand<T> cmd) {
        for(String s : cmd.getAliases()){
            if(getCommandByName(s) != null) {
                throw new SubCommandExistsException(s);
            }
        }

        this.subCommands.add(cmd);
    }

    @Override
    public final void perform(CommandSender sender, CommandData data, String @NotNull [] args) {
        if(args.length < 1){
            data.getSender().sendMessage(this.usageMessage);
            return;
        }

        ICommand<T> sub = getCommandByName(args[0]);

        if(sub == null) {
            data.getSender().sendMessage(this.usageMessage);
            return;
        }
        String[] arguments = new String[args.length -1];

        System.arraycopy(args, 1, arguments, 0, args.length - 1);

        CommandData cd = new CommandData(arguments, data.getSender(), this, args[0]);

        sub.perform(sender, cd, cd.getArgs());
    }

    @Nullable
    protected ICommand<T> getCommandByName(@NotNull String name){
        for(ICommand<T> cmd : subCommands){
            if(cmd.getAliases().contains(name) ||cmd.getName().equalsIgnoreCase(name)) return cmd;
        }
        return null;
    }
}
