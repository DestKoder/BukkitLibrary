package ru.dest.library.object;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.dest.library.utils.ChatUtils;

import java.util.List;

import static ru.dest.library.utils.ChatUtils.format;
import static ru.dest.library.utils.ChatUtils.sendMessage;


/**
 * This class represent a message stored in lang file
 *
 * @since 1.0
 * @author DestKoder
 */
public class Message {

    private String value;

    public Message(String value) {
        this.value = value;
    }

    /**
     * Format message text
     * @param format - list of {@link List} of {@link Pair} pairs of key and value
     * @return formatted {@link Message};
     */
    public Message format(List<Pair<String, String>> format){
        this.value = ChatUtils.format(value, format);
        return this;
    }

    public Message format(Pair<String, String> format) {
        this.value = ChatUtils.format(value, format);
        return this;
    }

    public Message format(String key, String value){
        this.value = ChatUtils.format(this.value, key, value);
        return this;
    }

    /**
     * Sends message to a player
     * @param player {@link Player} to whom message will be sent
     */
    public void send(Player player){
        sendMessage(player, value);
    }

    /**
     * Sends message to CommandSender
     * @param sender {@link CommandSender} to whom message will be sent
     */
    public void send(CommandSender sender){
        sendMessage(sender, value);
    }

    /**
     * Broadcast message to all online players
     */
    public void broadcast(){
        Bukkit.getOnlinePlayers().forEach(p -> sendMessage(p, value));
    }

    /**
     * Broadcast message to all players with given permission
     * @param permission - Permission to check
     */
    public void broadcast(String permission){
        Bukkit.getOnlinePlayers().forEach(p -> {if(p.hasPermission(permission)) sendMessage(p, value);});
    }

    /**
     * Create modern version of this message
     *
     * <p>Add support of hover and click events</p>
     *
     * @return moder version of this message
     */
    public AdvancedMessage createModernVersion(){
        return new AdvancedMessage(new TextComponent(value));
    }

    /**
     * @return message's text
     */
    public String getValue() {
        return value;
    }
}
