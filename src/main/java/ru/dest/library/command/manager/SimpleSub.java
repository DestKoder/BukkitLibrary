package ru.dest.library.command.manager;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.command.CommandData;
import ru.dest.library.command.ICommand;
import ru.dest.library.command.annotation.CommandArgs;
import ru.dest.library.command.annotation.Permission;
import ru.dest.library.object.ArgCondition;
import ru.dest.library.utils.ChatUtils;
import ru.dest.library.utils.Patterns;
import ru.dest.library.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleSub<T extends JavaPlugin> implements ICommand<T> {

    private final Map<Integer, ArgCondition> argsConditions = new HashMap<>();
    private String permission = null;
    private String usage;

    private final String name;
    private final T plugin;
    private AbstractManagerCommand<T> root;
    private Method method;

    public SimpleSub(T plugin, AbstractManagerCommand<T> root, String name, String usage, Method method) {
        this.name = name;
        this.root = root;
        this.usage = usage;
        this.plugin = plugin;
        this.method = method;

        this.checkAnnotations();
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
        Permission permAnnotation = method.getDeclaredAnnotation(Permission.class);
        CommandArgs argsAnnotation = method.getDeclaredAnnotation(CommandArgs.class);

        if(permAnnotation != null) setPermission(permAnnotation);
        if(argsAnnotation != null && !argsAnnotation.args().trim().isEmpty()) setArgs(argsAnnotation);
    }

    @Override
    public final void perform(CommandSender sender, @NotNull CommandData data, String[] args) {
        if(permission != null && !permission.isEmpty() && !sender.hasPermission(permission)){
            Library.getInstance().getNoPermissionMessage().send(sender);
            return;
        }

        if(args.length < argsConditions.size()) {
            if(usage != null) sender.sendMessage(ChatUtils.parse(usage));
            return;
        }

        for(int i = 0; i < argsConditions.size(); i++){
            ArgCondition c = argsConditions.get(i);
            if(!c.test(args[i])) {
                c.getErrorMessage().send(data.getSender());
                return;
            }
        }

        try {
            method.setAccessible(true);
            method.invoke(root, sender, data, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull T getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull List<String> getAliases() {
        return Utils.newList(name);
    }

    @Override
    public String getName() {
        return name;
    }
}
