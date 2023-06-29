package ru.dest.library.plugin;

import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dest.library.loging.ConsoleLogger;
import ru.dest.library.object.CommandRegistry;
import ru.dest.library.utils.TimeUtils;

import java.io.File;

public abstract class BukkitPlugin<T extends JavaPlugin> extends JavaPlugin {

    protected PluginManager pluginManager;
    protected CommandRegistry<T> registry;
    protected ConsoleLogger logger;

    private String id, prefix;
    private int resource;
    @Override
    public void onEnable(){
        I();
        logger = new ConsoleLogger(prefix.isEmpty() ? getName() : prefix, true);
        this.pluginManager = getServer().getPluginManager();
        long time = TimeUtils.getCurrentUnixTime();

        logger.info(ConsoleLogger.CYAN + "==========[ " +ConsoleLogger.GREEN + getName() + " by " + getDescription().getAuthors().get(0) + ConsoleLogger.CYAN + " ]==========");
        try {
            this.registry = new CommandRegistry<>(this);
        } catch (Exception e) {
            logger.error(e);
        }
        this.enable();

        this.regCommands(registry);

        for (Permission permission : getPermissions()) {
            pluginManager.addPermission(permission);
        }
        for (Listener handler : getHandlers()) {
            pluginManager.registerEvents(handler, this);
        }

        logger.info(ConsoleLogger.CYAN + "==========[ " +ConsoleLogger.GREEN + "Enabled in "+(TimeUtils.getCurrentUnixTime()-time) + " seconds" + ConsoleLogger.CYAN + " ]==========");
    }

    public abstract void enable();
    public void regCommands(CommandRegistry<T> registry){}
    public Permission[] getPermissions(){return new Permission[0];}
    public Listener[] getHandlers() {return new Listener[0];};

    private void I() {
        Plugin annotation = getClass().getDeclaredAnnotation(Plugin.class);
        if(annotation == null) throw new IllegalStateException("BukkitPlugin must have Plugin annotation!");

        this.id = annotation.id();
        this.prefix = annotation.prefix();
        this.resource = annotation.resource();
    }
}
