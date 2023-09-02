package ru.dest.library.database;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.database.connection.IConnectionProvider;
import ru.dest.library.database.settings.ConnectionSettings;
import ru.dest.library.database.thread.QueryTask;
import ru.dest.library.database.thread.UpdateTask;
import ru.dest.library.database.util.Utils;
import ru.dest.library.loging.ILogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class SimplePool implements IDatabase{

    private final IConnectionProvider connectionProvider;
    private final ILogger logger;

    private final Vector<Connection> available = new Vector<>();
    private final Vector<Connection> used = new Vector<>();

    public SimplePool(@NotNull ConnectionSettings settings, ILogger logger, int poolSize) throws Exception {
        this.connectionProvider = settings.makeProvider();
        this.logger = logger;

        for(int i = 0; i < poolSize; i ++){
            available.add(connectionProvider.connect());
        }
    }

    public synchronized Connection retrieve() throws SQLException {
        Connection newConn = null;
        if (available.size() == 0) {
            newConn = connectionProvider.connect();
        } else {
            newConn = (Connection) available.lastElement();
            available.removeElement(newConn);
        }
        used.addElement(newConn);
        return newConn;
    }

    public synchronized void putback(Connection c) throws NullPointerException {
        if (c != null) {
            if (used.removeElement(c)) {
                available.addElement(c);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }

    @Override
    public IConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    public CompletableFuture<Integer> update(String sql, Object[] data){
        try{
            Connection connection = retrieve();
            CompletableFuture<Integer> future = new CompletableFuture<>();

            Executors.newCachedThreadPool().submit(() -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                Utils.completeQuery(stmt, data);
                future.complete(stmt.executeUpdate());
                stmt.close();
                putback(connection);
                return null;
            });

            return future;
        } catch (Exception e) {
            logger.warning("Error occupied during update task");
            logger.error(e);
            return null;
        }
    }

    @Override
    public <T> T query(String sql, Object[] data, ResultSetHandler<T> rsh){
        Connection connection = null;
        try{
            connection = retrieve();
            FutureTask<T> task = new FutureTask<>(new QueryTask<T>(connection, sql, data, rsh));

            new Thread(task).start();

            return task.get();
        } catch (SQLException | InterruptedException | ExecutionException e) {
            logger.warning("Error occupied during update task");
            logger.error(e);
            return null;
        }finally {
            putback(connection);
        }
    }

    public void end() throws SQLException {
        for(Connection conn : used) putback(conn);

        for(Connection connection : available) {
            connection.close();
        }

        available.clear();
    }

}
