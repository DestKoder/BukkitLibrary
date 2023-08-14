package ru.dest.library.object.db;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.dest.library.database.settings.ConnectionSettings;
import ru.dest.library.database.settings.ISettingsLoader;

import java.io.File;
import java.nio.file.Path;

public class Loader implements ISettingsLoader {

    @Override
    public ConnectionSettings load(File file, Path path) throws Exception {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return new ConnectionSettings(
                config.getString("host"),
                config.getString("user"),
                config.getString("password"),
                config.getString("database"),
                config.getString("type"),
                path
        );
    }
}
