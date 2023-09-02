package ru.dest.library.database.settings;

import org.jetbrains.annotations.Nullable;
import ru.dest.library.database.DatabaseType;
import ru.dest.library.database.connection.IConnectionProvider;
import ru.dest.library.database.exception.ConnectionProviderInitializeException;
import ru.dest.library.database.exception.ConnectionSettingsLoadException;
import ru.dest.library.database.exception.InvalidConnectionSettingsException;
import ru.dest.library.utils.Utils;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConnectionSettings {

    private final String host, user, password, database, type;
    private final Path root;

    public ConnectionSettings(@Nullable String host, @Nullable String user, @Nullable String password, @Nullable String database, @Nullable String type, Path root) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database == null ? "data" : database;
        this.type = type;
        this.root = root;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getType() {
        return type;
    }

    public Path getRoot() {
        return root;
    }

    public IConnectionProvider makeProvider() throws Exception{
        if(type == null) throw new InvalidConnectionSettingsException("type");
        DatabaseType type = DatabaseType.get(this.type);
        if(type == null) throw new ConnectionProviderInitializeException("Invalid type! Possible values: MySQL, SQLIte, Local, H2, PostgreSQL");

        return type.createProvider(this);
    }

    public static class Loader {
        //File extension : loader
        private static final Map<String, ISettingsLoader> loaders = new HashMap<>();

        static {
            Utils.executeIf(loaders, map -> map.put(".json", new JsonSettingsLoader()), map-> !map.containsKey(".json"));
        }

        /**
         * Load {@link ConnectionSettings} from file;
         * @param config file with configuration settings in .json or another format
         * @param root path to root directory for file-stored databases such as H2-Local and SQLite;
         * @return Loaded {@link ConnectionSettings};
         * @throws Exception if exception occupied during loading settings from file;
         */
        public ConnectionSettings load(File config, Path root) throws Exception {
            for(String s : loaders.keySet()){
                if(config.getName().endsWith(s)) return loaders.get(s).load(config, root);
            }
            throw new ConnectionSettingsLoadException(config);
        }

        public static void rl(String e, ISettingsLoader l){
            loaders.put(e,l);
        }
    }
}
