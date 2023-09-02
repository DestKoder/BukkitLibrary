package ru.dest.library.database.thread;

import ru.dest.library.database.ResultSetHandler;
import ru.dest.library.database.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class QueryTask<T> implements Callable<T> {

    private final Connection connection;
    private final String query;
    private final Object[] data;
    private final ResultSetHandler<T> rsh;

    public QueryTask(Connection connection, String sql, Object[] data, ResultSetHandler<T> rsh) {
        this.connection = connection;
        this.query = sql;
        this.data = data;
        this.rsh = rsh;
    }

    @Override
    public T call() throws Exception {
        T result = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            if(data != null){
                Utils.completeQuery(stmt, data);
            }
            ResultSet res = stmt.executeQuery();

            if(res == null) return null;

            result = rsh.handle(res);

            stmt.close();
            res.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }
}
