package ru.dest.library.database.connection;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.database.settings.ConnectionSettings;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteProvider implements IConnectionProvider {

    private final String LINK;

    public SQLiteProvider(@NotNull ConnectionSettings config) throws ClassNotFoundException {
        File f = new File(config.getRoot().toFile(), config.getDatabase()+".db");

        this.LINK = "jdbc:sqlite:"+f;

        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(LINK);
    }
}
