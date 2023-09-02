package ru.dest.library.database.util;

import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utils {

    public static void completeQuery(@NotNull PreparedStatement stmt, Object @NotNull [] data) throws SQLException {
        for(int index = 0; index < data.length; index++){
            stmt.setObject(index+1, data[index]);
        }
    }
}
