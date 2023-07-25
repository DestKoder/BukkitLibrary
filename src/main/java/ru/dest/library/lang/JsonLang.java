package ru.dest.library.lang;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.Message;
import ru.dest.library.object.Title;
import ru.dest.library.utils.ChatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonLang implements Lang{

    private final Map<String, String> data;
    private final String prefix;

    private JsonLang(@NotNull Map<String, String> data){
        this.data = data;
        this.prefix = data.getOrDefault("prefix", "");
    }

    @Override
    @NotNull
    public String getRawString(String key) {
        return ChatUtils.parse( data.getOrDefault(key, key));
    }

    @Override
    @NotNull
    public Message getMessage(String key) {
        String msg = prefix+ getRawString(key);
        return new Message(msg);
    }

    @Override
    @NotNull
    public Title getTitle(String key) {
        String subtitle = getRawString(key+".subtitle"), title = getRawString(key + ".title");

        return new Title(title,subtitle);
    }

    @Override
    @NotNull
    public Message getMessage(String key, Player placeholder) {
        String msg = prefix+ getRawString(key);

        return new Message(ChatUtils.applyPlaceholders(msg, placeholder));
    }

    @Override
    @NotNull
    public Title getTitle(String key, Player placeholder) {
        String subtitle = getRawString(key+".subtitle"), title = getRawString(key + ".title");
        subtitle = ChatUtils.applyPlaceholders(subtitle, placeholder);

        title = ChatUtils.applyPlaceholders(title, placeholder);

        return new Title(title, subtitle);
    }

    @Contract("_ -> new")
    public static @NotNull JsonLang loadFromFile(@NotNull final File f) throws FileNotFoundException, IOException, IllegalArgumentException {
        final Map<String, String> data = new HashMap<>();
        if(!f.exists())throw  new FileNotFoundException("Lang file " + f.getName() + " doesn't exists");
        if(!f.getName().endsWith(".json")) throw new IllegalArgumentException("Given file must be a .json file!");

        JsonObject element = new JsonParser().parse(FileUtils.readFileToString(f, StandardCharsets.UTF_8)).getAsJsonObject();

        element.entrySet().forEach(e -> {
            JsonElement obj = e.getValue();
            if(obj.isJsonArray()){
                StringBuilder string = new StringBuilder();
                obj.getAsJsonArray().forEach(el -> {
                    string.append(el.getAsString());
                    string.append("\n");
                });
                String str = string.toString();
                data.put(e.getKey(), str.trim().substring(0, str.length()-"\n".length()));
            }
            data.put(e.getKey(), e.getValue().getAsString());
        });

        return new JsonLang(data);
    }
}
