package ru.dest.library.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.database.connection.IConnectionProvider;
import ru.dest.library.database.settings.ConnectionSettings;

public enum DatabaseType {
    MYSQL("ru.dest.library.database.connection.MySQLProvider"),
    SQLITE("ru.dest.library.database.connection.SQLiteProvider"),
    LOCAL("ru.dest.library.database.connection.H2LocalProvider"),
    H2("ru.dest.library.database.connection.H2RemoteProvider"),
    POSTGRESQL("ru.dest.library.database.connection.PostgreSQLProvider")
    ;

    private final String connectionProviderClass;

    DatabaseType(String connectionProviderClass) {
        this.connectionProviderClass = connectionProviderClass;
    }

    public @NotNull IConnectionProvider createProvider(ConnectionSettings settings) throws Exception {
        return (IConnectionProvider) Class.forName(connectionProviderClass).getDeclaredConstructor(ConnectionSettings.class).newInstance(settings);
    }

    public static @Nullable DatabaseType get(@NotNull String type){
        try {
            return DatabaseType.valueOf(type.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}
