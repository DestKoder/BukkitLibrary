package ru.dest.library.nms;

import org.bukkit.inventory.ItemStack;

public interface TagUtils {

    ItemStack setStringTag(ItemStack item, String key, String value);
    ItemStack setIntegerTag(ItemStack item, String key, int value);
    ItemStack setDoubleTag(ItemStack item, String key, double value);

    String getStringTagValue(ItemStack item, String key);
    Integer getIntegerTagValue(ItemStack item, String key);
    Double getDoubleTagValue(ItemStack item, String key);

}
