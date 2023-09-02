package ru.dest.library.database.exception;

public class InvalidConnectionSettingsException extends RuntimeException{

    public InvalidConnectionSettingsException(String param){
        super("Invalid connection settings for this type of database. Missing parameter: " + param);
    }
}
