package ru.dest.library.database;

import ru.dest.library.database.connection.IConnectionProvider;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public interface IDatabase {

    IConnectionProvider getConnectionProvider();

    /**
     * Execute update query in parallel thread
     *
     * @param sql  query
     * @param data Array of {@link Object} for completing specified query
     * @return result of query;
     */
    CompletableFuture<Integer> update(String sql, Object[] data);
    /**
     * Execute update query in parallel thread
     * @param sql query
     * @return result of query;
     */
    default CompletableFuture<Integer> update(String sql){
        return update(sql, new Object[0]);
    }

    /**
     * Execute select query in parallel thread
     * @param sql query
     * @param data Array of {@link Object} for completing specified query
     * @param rsh {@link ResultSetHandler} for getting result
     * @return Queried result or null
     * @param <T> expected result type
     */
    <T> T query(String sql, Object[] data, ResultSetHandler<T> rsh);
    default <T> T query(String sql, ResultSetHandler<T> rsh, Object[] data) {return query(sql,data, rsh);};
    /**
     * Execute select query in parallel thread
     * @param sql query
     * @param rsh {@link ResultSetHandler} for getting result
     * @return Queried result or null
     * @param <T> expected result type
     */
    default <T> T query(String sql, ResultSetHandler<T> rsh){
        return query(sql, new Object[0], rsh);
    }

    /**
     * Closing all connections
     * @throws SQLException if error occupied during disconnecting from database
     */
    void end() throws SQLException;
}
