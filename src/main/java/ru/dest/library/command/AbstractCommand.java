package ru.dest.library.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.annotation.CommandArgs;
import ru.dest.library.command.annotation.Permission;
import ru.dest.library.object.ArgCondition;
import ru.dest.library.utils.ChatUtils;
import ru.dest.library.utils.Patterns;

import java.util.*;

public abstract class AbstractCommand<T extends JavaPlugin> extends Command implements ICommand<T>{

    private final Map<Integer, ArgCondition> argsConditions = new HashMap<>();
    private String permission = null;

    protected final T plugin;

    public AbstractCommand(T plugin, String name, String usage){
        super(name, "some abstract command", usage, Collections.emptyList());
        this.checkAnnotations();
        this.plugin = plugin;
    }

    public AbstractCommand(T plugin, String name, String usage, List<String> aliases){
        super(name, "Some abstract command", usage, aliases);
        this.checkAnnotations();
        this.plugin = plugin;
    }

    public AbstractCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usage, @NotNull List<String> aliases) {
        super(name, description, usage, aliases);
        this.checkAnnotations();
        this.plugin = plugin;
    }

    private void setPermission(Permission a){
        if(a != null) permission = a.permission();
    }
    private void setArgs(@NotNull CommandArgs argsAnnotation){
        String[] args = argsAnnotation.args().split(" ");
        for(int i = 0; i < args.length; i++){
            String s = args[i].substring(1, args[i].length()-1);
            if(s.equalsIgnoreCase("string")){
                argsConditions.put(i, new ArgCondition(s, str -> true));
            }else if(s.equalsIgnoreCase("int")){
                argsConditions.put(i, new ArgCondition(s, (str)->str.matches(Patterns.INTEGER.pattern())));
            }else if(s.equalsIgnoreCase("double")){
                argsConditions.put(i, new ArgCondition(s, (str)->str.matches(Patterns.DOUBLE.pattern())));
            }else if(s.equalsIgnoreCase("bool")){
                argsConditions.put(i, new ArgCondition(s, str -> str.matches(Patterns.BOOLEAN.pattern())));
            }else if(s.equalsIgnoreCase("time")){
                argsConditions.put(i, new ArgCondition(s, str -> str.matches(Patterns.TIME_UNIT.pattern())));
            }
        }
    }
    private void checkAnnotations(){
        Permission permAnnotation = getClass().getDeclaredAnnotation(Permission.class);
        CommandArgs argsAnnotation = getClass().getDeclaredAnnotation(CommandArgs.class);

        if(permAnnotation != null) setPermission(permAnnotation);
        if(argsAnnotation != null && !argsAnnotation.args().trim().isEmpty()) setArgs(argsAnnotation);
    }

    @Override
    public final boolean execute(CommandSender sender, String label, String[] args) {
        CommandData data = new CommandData(args, sender, this, label);

        this.execute(data, data.getArgs());
        return true;
    }
    private void execute(@NotNull CommandData data, String[] args) {
        boolean finished = false;
        CommandSender sender = data.getSender();

        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            Library.i().getNoPermissionMessage().send(sender);
        } else if (args.length < argsConditions.size()) {
            if (usageMessage != null) sender.sendMessage(ChatUtils.parse(usageMessage));
        } else {
            for (int i = 0; i < argsConditions.size(); i++) {
                ArgCondition c = argsConditions.get(i);
                if (!c.test(args[i])) {
                    c.getErrorMessage().send(data.getSender());
                    finished = true;
                    break;
                }
            }
            if (!finished) {
                this.perform(sender, data, args);
            }
        }

    }

    public abstract void perform(CommandSender sender, CommandData data, String[] args);

    @Override
    public final @NotNull T getPlugin() {
        return plugin;
    }

    @Override
    @NotNull
    public List<String> getAliases() {
        return super.getAliases() == null ? Collections.emptyList() : super.getAliases();
    }
}
