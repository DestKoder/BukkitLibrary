package ru.dest.library.command.manager;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.command.AbstractCommand;
import ru.dest.library.command.CommandData;
import ru.dest.library.command.ICommand;
import ru.dest.library.utils.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class ObjectManagerCommand<T extends JavaPlugin, OBJECT> extends BaseManagerCommand<T> {

    private static final Class<?>[] parameterTypes = new Class[]{CommandSender.class, CommandData.class, String[].class};

    public ObjectManagerCommand(T plugin, String name, String usage) {
        super(plugin, name, usage);
        this.registerCommands();
    }

    public ObjectManagerCommand(T plugin, String name, String usage, List<String> aliases) {
        super(plugin, name, usage, aliases);
        this.registerCommands();
    }

    public ObjectManagerCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(plugin, name, description, usageMessage, aliases);
        this.registerCommands();
    }

    @Override
    public final void perform(CommandSender sender, CommandData data, String @NotNull [] args) {
        if(args.length < 2){
            data.getSender().sendMessage(this.usageMessage);
            return;
        }

        String[] arguments = new String[args.length -2];

        System.arraycopy(args, 2, arguments, 0, args.length - 2);

        CommandData cd = new CommandData(arguments, data.getSender(), this, args[1]);

        ICommand<T> sub = getCommandByName(args[1]);

        if(sub == null) {
            __default(sender, cd, cd.getArgs());
            return;
        }

        sub.perform(sender, cd, cd.getArgs());
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        List<String> toReturn = new ArrayList<>();
        if(args.length == 1){
            toReturn.addAll(getAvailableObjectsValues());
            return toReturn;
        }
        if(args.length == 2){
            for(ICommand<T> cmd : subCommands){
                toReturn.addAll(cmd.getAliases());
                if(!toReturn.contains(cmd.getName())) toReturn.add(cmd.getName());
            }
            return toReturn;
        }
        ICommand<T> sub = getCommandByName(args[1]);
        if (sub instanceof TabCompleter || sub instanceof AbstractCommand) {
            String[] arguments = new String[args.length - 2];

            System.arraycopy(args, 2, arguments, 0, args.length - 2);

            if (sub instanceof TabCompleter)
                toReturn.addAll(Objects.requireNonNull(((TabCompleter) sub).onTabComplete(sender, this, alias, arguments)));
            else toReturn.addAll(((AbstractCommand<T>) sub).tabComplete(sender, alias, arguments));
        }

        return toReturn;

    }

    protected abstract OBJECT getActionedObject(String s);

    protected List<String> getAvailableObjectsValues(){return Utils.newList();}
    private void registerCommands(){
        for(Method method : getClass().getDeclaredMethods()){
            if(method.getName().equalsIgnoreCase("__default")) continue;
            if(!Arrays.equals(method.getParameterTypes(), parameterTypes)) continue;
            addSubCommand(new SimpleSub<>(plugin, this, method.getName(), "/"+getName()+" help", method));
        }
    }
}
