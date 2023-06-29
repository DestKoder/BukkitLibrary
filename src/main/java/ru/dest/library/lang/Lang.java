package ru.dest.library.lang;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.object.Message;
import ru.dest.library.object.Title;

public interface Lang {

    /**
     * Get message as string, if you want to get title add .title or .subtitle
     * @param key Key of needed Message
     * @return Message as raw String without placeholders support etc. or null if no message provided with such key
     */
    @NotNull
    String getRawString(String key);

    /**
     * Get message from file
     * @param key Key of needed Message
     * @return {@link Message} from file or null if key not exists;
     */
    @NotNull
    Message getMessage(String key);
    @NotNull
    Title getTitle(String key);

    @NotNull
    Message getMessage(String key, Player placeholder);
    @NotNull
    Title getTitle(String key, Player placeholder);
}
