package ru.dest.library.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {

    Connection connect() throws SQLException;
}
