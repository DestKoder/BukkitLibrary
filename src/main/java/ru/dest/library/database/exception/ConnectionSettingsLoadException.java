package ru.dest.library.database.exception;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConnectionSettingsLoadException extends Exception{

    public ConnectionSettingsLoadException(@NotNull File f) {
        super("Couldn't load connection settings from file: " + f.getPath());
    }

    public ConnectionSettingsLoadException(@NotNull File f, String message) {
        super("Couldn't load connection settings form file: " + f.getPath() +".\nException: " + message);
    }
}
