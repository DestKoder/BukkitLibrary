package ru.dest.library.database.settings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.database.exception.ConnectionSettingsLoadException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class JsonSettingsLoader implements ISettingsLoader{

    @Override
    public ConnectionSettings load(File file, Path root) throws Exception {
        JsonObject object = new JsonParser().parse(FileUtils.readFileToString(file, StandardCharsets.UTF_8)).getAsJsonObject();

        if(object == null) throw new ConnectionSettingsLoadException(file, "Invalid json structure.");

        return new ConnectionSettings(getValueOrNull(object, "host"), getValueOrNull(object, "user"), getValueOrNull(object, "password"), getValueOrNull(object, "database"), getValueOrNull(object, "type"), root);
    }

    private @Nullable String getValueOrNull(@NotNull JsonObject jsonObject, String key){
        return jsonObject.has(key) ? jsonObject.get(key).getAsString() : null;
    }

}
