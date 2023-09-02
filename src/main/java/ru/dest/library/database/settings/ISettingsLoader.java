package ru.dest.library.database.settings;

import java.io.File;
import java.nio.file.Path;

public interface ISettingsLoader {

    ConnectionSettings load(File file, Path root) throws Exception;
}
