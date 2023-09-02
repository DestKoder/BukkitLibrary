package ru.dest.library.database.connection;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.database.exception.InvalidConnectionSettingsException;
import ru.dest.library.database.settings.ConnectionSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLProvider implements IConnectionProvider {

    private final String LINK, USER, PASS;
    //jdbc:mysql://host/database
    public PostgreSQLProvider(@NotNull ConnectionSettings config) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        /* Some checks before constructing...*/
        if(config.getHost() == null) throw new InvalidConnectionSettingsException("host");
        if(config.getUser() == null) throw new InvalidConnectionSettingsException("user");
        if(config.getPassword() == null) throw new InvalidConnectionSettingsException("password");

        this.LINK = "jdbc:postgresql://"+(config.getHost().contains(":")?config.getHost() : config.getHost()+":3306") +"/"+config.getDatabase()+(config.getDatabase().contains("autoReconnect") ? "": config.getDatabase().contains("?") ? "&autoReconnect=true" : "autoReconnect=false");
        this.USER = config.getUser();
        this.PASS = config.getPassword();
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK, USER, PASS);
    }
}
