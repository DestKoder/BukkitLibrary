package ru.dest.library.nms;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface TagUtils {

    @Deprecated
    default ItemStack setStringTag(ItemStack item, String key, String value){
        return setStringTag(item, NamespacedKey.minecraft(key), value);
    }
    @Deprecated
    default ItemStack setIntegerTag(ItemStack item, String key, int value){
        return setIntegerTag(item, NamespacedKey.minecraft(key), value);
    }
    @Deprecated
    default ItemStack setDoubleTag(ItemStack item, String key, double value){
        return setDoubleTag(item, NamespacedKey.minecraft(key), value);
    }

    @Deprecated
    default String getStringTagValue(ItemStack item, String key) {
        return getStringTagValue(item, NamespacedKey.minecraft(key));
    }
    @Deprecated
    default Integer getIntegerTagValue(ItemStack item, String key){
        return getIntegerTagValue(item, NamespacedKey.minecraft(key));
    }
    @Deprecated
    default Double getDoubleTagValue(ItemStack item, String key){
        return getDoubleTagValue(item, NamespacedKey.minecraft(key));
    }

    ItemStack setStringTag(ItemStack item, NamespacedKey key, String value);
    ItemStack setIntegerTag(ItemStack item, NamespacedKey key, int value);
    ItemStack setDoubleTag(ItemStack item, NamespacedKey key, double value);

    String getStringTagValue(ItemStack item, NamespacedKey key);
    Integer getIntegerTagValue(ItemStack item, NamespacedKey key);
    Double getDoubleTagValue(ItemStack item, NamespacedKey key);

    @Deprecated
    default boolean hasTag(ItemStack item, String key) {return hasTag(item, NamespacedKey.minecraft(key));}

    boolean hasTag(ItemStack item, NamespacedKey key);
}
