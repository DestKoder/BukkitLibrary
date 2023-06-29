package ru.dest.library.lang;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.Message;
import ru.dest.library.object.Title;
import ru.dest.library.utils.ChatUtils;
import ru.dest.library.utils.Utils;

import java.util.List;

public class YamlLang implements Lang{

    private final FileConfiguration cfg;
    private final String prefix;

    public YamlLang(@NotNull FileConfiguration cfg) {
        this.cfg = cfg;
        if(!cfg.isSet("prefix")) prefix = "";
        else prefix = cfg.getString("prefix");
    }

    @Override
    @NotNull
    public String getRawString(String key) {
        if(!cfg.isSet(key) || cfg.isConfigurationSection(key)) return "NULL";
        if(!cfg.isList(key)) {
            return ChatUtils.parse(cfg.getString(key));
        }
        List<String> strings = cfg.getStringList(key);

        return Utils.collectionToString(ChatUtils.parse(strings), "\n");
    }

    @Override
    public @NotNull Message getMessage(String key) {
        String msg = prefix+ getRawString(key);
        return new Message(msg);
    }

    @Override
    @NotNull
    public Title getTitle(String key) {
        String title = getRawString(key + ".title"), subtitle = getRawString(key+".subtitle");

        return new Title(title, subtitle);
    }

    @Override
    @NotNull
    public Message getMessage(String key, Player placeholder) {
        String msg =  prefix+ getRawString(key);

        msg = ChatUtils.applyPlaceholders(msg, placeholder);

        return new Message(msg);
    }

    @Override
    @NotNull
    public Title getTitle(String key, Player placeholder) {
        String title = getRawString(key + ".title"), subtitle = getRawString(key+".subtitle");
        title = ChatUtils.applyPlaceholders(title, placeholder);

        return new Title(title, subtitle);
    }
}
