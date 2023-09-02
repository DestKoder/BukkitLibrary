package ru.dest.library.database.connection;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.database.settings.ConnectionSettings;
import ru.dest.library.database.exception.InvalidConnectionSettingsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2RemoteProvider implements IConnectionProvider {

    private final String LINK, USER, PASS;

    public H2RemoteProvider(@NotNull ConnectionSettings config) throws ClassNotFoundException {
        if(config.getHost() == null) throw new InvalidConnectionSettingsException("host");
        if(config.getUser() == null) throw new InvalidConnectionSettingsException("user");
        if(config.getPassword() == null) throw new InvalidConnectionSettingsException("password");

        this.LINK = "jdbc:h2:tcp://"+config.getHost()+"/"+config.getDatabase();
        this.USER = config.getUser();
        this.PASS = config.getPassword();

        Class.forName("org.h2.Driver");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK, USER, PASS);
    }
}
