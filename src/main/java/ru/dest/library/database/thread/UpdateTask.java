package ru.dest.library.database.thread;

import ru.dest.library.database.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class UpdateTask extends CompletableFuture<Integer> {
    private final String query;
    private final Connection connection;
    private final Object[] data;

    public UpdateTask(String query, Object[] data, Connection connection) {
        this.query = query;
        this.connection = connection;
        this.data = data;
    }


}
