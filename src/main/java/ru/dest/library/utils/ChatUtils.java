package ru.dest.library.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.object.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some utils for working with chat, sending message in it
 * @since 1.0
 * @author DestKoder
 */
public final class ChatUtils {

    private static boolean hookPlaceholder;
    private static final Pattern HEX_PATTERN = Pattern.compile("(&#[0-9a-fA-F]{6})");

    static {
        hookPlaceholder = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    /**
     * Parse color symbols in string
     * @param s - string for parse
     * @return parsed string
     */
    @NotNull
    public static String parse(String s){

        Matcher matcher = HEX_PATTERN.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1).substring(1);
            matcher.appendReplacement(sb, "" + getColorFromHexString(hex));
        }
        matcher.appendTail(sb);

        String hexColored = sb.toString();

        return ChatColor.translateAlternateColorCodes('&', hexColored);
    }

    /**
     * Converts hex String to Bukkit Color
     * @param hex {@link String} with hex code (#000000)
     * @return the resulting {@link Color}
     */
    @Contract("_ -> new")
    public static @NotNull Color getColorFromHexString(@NotNull String hex){
        return Color.fromRGB(Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                Integer.valueOf( hex.substring( 5, 7 ), 16 ));
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Color forRGB(int r, int g, int b){
        return Color.fromRGB(r,g,b);
    }

    /**
     * Parse color symbols in string list
     * @param l - string list for parse
     * @return return parsed string list
     */
    @NotNull
    public static List<String> parse(@NotNull List<String> l){
        List<String> toReturn = new ArrayList<>();

        l.forEach(s -> toReturn.add(parse(s)));

        return toReturn;
    }

    /**
     * Sets placeholders to string, if PlaceholderAPI is installed
     * @param s {@link String} in which placeholders will be set
     * @param player {@link OfflinePlayer} for placeholders data
     * @return provided string with placeholders set
     */
    public static String applyPlaceholders(String s, OfflinePlayer player){
        if(!hookPlaceholder) return s;

        s = PlaceholderAPI.setPlaceholders(player, s);
        s = PlaceholderAPI.setBracketPlaceholders(player, s);

        return s;
    }

    /**
     * Format a message
     * @param message {@link String} message which will be formatted
     * @param format {@link List} of {@link Pair} of replacements;
     * @return formatted message
     */
    @NotNull
    public static String format(@NotNull String message, @NotNull  List<Pair<String, String>> format){

        for(Pair<String, String> pair : format){
            message = format(message, pair);
        }

        return message;
    }

    @NotNull
    public static String format(@NotNull String message, @NotNull Pair<String,String> pair){
        return format(message, pair.getFirstVal(), pair.getSecondVal());
    }

    @Contract(pure = true)
    public static @NotNull String format(@NotNull String message, String key, String value){
        return message.replaceAll("\\{"+key+"}", value);
    }

    /**
     * Sends message to CommandSender
     * @param sendFor {@link CommandSender} to whom the message will be sent
     * @param message {@link String} message which will be sended;
     */
    public static void sendMessage(@NotNull CommandSender sendFor,@NotNull String message){
        sendFor.sendMessage(parse(message).split("\n"));
    }

    /**
     * Sends message to Player
     * @param sendFor {@link Player} to whom the message will be sent
     * @param message {@link String} message which will be sended;
     */
    public static void sendMessage(@NotNull Player sendFor, String message){
        sendFor.sendMessage(parse(message).split("\n"));
    }

    /**
     * Sends basecompontent-message to CommandSender
     * @param sendFor {@link CommandSender} to whom the message will be sent
     * @param message {@link BaseComponent} basecompontent-message which will be sended; Support HoverEffects, ClickableEvent etc.
     */
    public static void sendMessage(@NotNull CommandSender sendFor, BaseComponent message){
        sendFor.spigot().sendMessage(message);
    }

    /**
     * Sends basecompontent-message to Player
     * @param sendFor {@link Player} to whom the message will be sent
     * @param message {@link BaseComponent} basecompontent-message which will be sended; Support HoverEffects, ClickableEvent etc.
     */
    public static void sendMessage(@NotNull Player sendFor, BaseComponent message){
        sendFor.spigot().sendMessage(message);
    }

    /**
     * Sends basecompontent-message to Player with message position support
     * @param sendFor {@link Player} to whom the message will be sent
     * @param message {@link BaseComponent} basecompontent-message which will be sended; Support HoverEffects, ClickableEvent etc.
     * @param pos {@link ChatMessageType} position in which message will be showed
     */

    public static void sendMessage(@NotNull Player sendFor, BaseComponent message, ChatMessageType pos){
        sendFor.spigot().sendMessage(pos, message);
    }

    /**
     * Sends message to Player with message position support
     * @param sendFor {@link Player} to whom the message will be sent
     * @param message {@link BaseComponent} message which will be sended; Support HoverEffects, ClickableEvent etc.
     * @param pos {@link ChatMessageType} position in which message will be showed
     */
    public static void sendMessage(@NotNull Player sendFor, String message, ChatMessageType pos){
        sendFor.spigot().sendMessage(pos, new TextComponent(parse(message)));
    }
}
