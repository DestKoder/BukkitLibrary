package ru.dest.library.command.manager;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.command.CommandData;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractManagerCommand<T extends JavaPlugin> extends BaseManagerCommand<T> {

    private static final Class<?>[] parameterTypes = new Class[]{CommandSender.class, CommandData.class, String[].class};

    public AbstractManagerCommand(T plugin, String name, String usage) {
        super(plugin, name, usage);
    }

    public AbstractManagerCommand(T plugin, String name, String usage, List<String> aliases) {
        super(plugin, name, usage, aliases);
    }

    public AbstractManagerCommand(T plugin, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(plugin, name, description, usageMessage, aliases);
        this.registerCommands();
    }

    private void registerCommands(){
        for(Method method : getClass().getDeclaredMethods()){
            if(!Arrays.equals(method.getParameterTypes(), parameterTypes)) continue;
            addSubCommand(new SimpleSub<>(plugin, this, method.getName(), "/"+getName()+" help", method));
        }
    }
}
