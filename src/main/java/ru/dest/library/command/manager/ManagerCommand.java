package ru.dest.library.command.manager;

import org.bukkit.plugin.java.JavaPlugin;
import ru.dest.library.command.ICommand;

public interface ManagerCommand<T extends JavaPlugin> extends ICommand<T> {
    void addSubCommand(ICommand<T> cmd);
}
