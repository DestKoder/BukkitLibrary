package ru.dest.library.database.connection;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.database.settings.ConnectionSettings;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2LocalProvider implements IConnectionProvider {

    private final String LINK;

    private final String user, pass;

    public H2LocalProvider(@NotNull ConnectionSettings config) throws ClassNotFoundException {
        File f = new File(config.getRoot().toFile().getAbsolutePath(), config.getDatabase()+".mv.db");
        this.LINK = "jdbc:h2:"+f;
        this.user = config.getUser() == null ? "dest" : config.getUser();
        this.pass = config.getPassword() == null ? "dest" : config.getPassword();

        Class.forName("org.h2.Driver");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK, user, pass);
    }

}
