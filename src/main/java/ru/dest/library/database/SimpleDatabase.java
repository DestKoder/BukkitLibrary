package ru.dest.library.database;

import org.jetbrains.annotations.NotNull;
import ru.dest.library.database.connection.IConnectionProvider;
import ru.dest.library.database.settings.ConnectionSettings;
import ru.dest.library.database.thread.QueryTask;
import ru.dest.library.database.util.Utils;
import ru.dest.library.loging.ILogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class SimpleDatabase implements IDatabase {

    private final IConnectionProvider provider;
    private final ILogger logger;

    private Connection connection;

    public SimpleDatabase(@NotNull ConnectionSettings settings, ILogger logger) throws Exception {
        this.provider = settings.makeProvider();
        this.logger = logger;
        getConnection();
    }

    private Connection getConnection() throws SQLException{
        if(connection == null || connection.isClosed()) connection = provider.connect();
        return connection;
    }

    @Override
    public IConnectionProvider getConnectionProvider() {
        return provider;
    }

    @Override
    public CompletableFuture<Integer> update(String sql, Object[] data) {
        try{
            Connection connection = getConnection();
            CompletableFuture<Integer> future = new CompletableFuture<>();

            Executors.newCachedThreadPool().submit(() -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                Utils.completeQuery(stmt, data);
                future.complete(stmt.executeUpdate());
                stmt.close();
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
    public <T> T query(String sql, Object[] data, ResultSetHandler<T> rsh) {
        try{
            Connection connection = getConnection();
            FutureTask<T> task = new FutureTask<>(new QueryTask<T>(connection, sql, data, rsh));

            new Thread(task).start();

            return task.get();
        } catch (Exception e) {
            logger.warning("Exception occupied during update task");
            logger.error(e);
            return null;
        }
    }

    @Override
    public void end() {
        try {
            if(connection != null && !connection.isClosed()) connection.close();
        }catch (SQLException e){
            logger.error(e);
        }
    }
}
