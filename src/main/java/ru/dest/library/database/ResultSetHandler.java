package ru.dest.library.database;


import java.sql.ResultSet;

public interface ResultSetHandler<T> {

    ResultSetHandler<Boolean> CHECK = ResultSet::next;

    T handle(ResultSet rs) throws Exception;

}
