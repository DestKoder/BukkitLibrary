package ru.dest.library.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerUtils {
    public String getIp(@NotNull Player player){
        return player.getAddress().getAddress().getHostAddress();
    }

    public void give(@NotNull Player player, ItemStack item){
        int slot = player.getInventory().firstEmpty();
        if(slot != -1) player.getInventory().addItem(item);
        else player.getWorld().dropItemNaturally(player.getLocation(), item);
    }

    public Player getActionedOnlinePlayer(String s, boolean perUUID){
        return perUUID ? Bukkit.getPlayer(UUID.fromString(s)) : Bukkit.getPlayer(s);
    }
}
