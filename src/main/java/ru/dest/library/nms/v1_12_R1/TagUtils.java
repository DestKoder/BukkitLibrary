package ru.dest.library.nms.v1_12_R1;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class TagUtils implements ru.dest.library.nms.TagUtils {

    public TagUtils(){};

    @Override
    public ItemStack setStringTag(ItemStack item, NamespacedKey key, String value) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return item;
        nbt.setString(key.toString(), value);
        i.setTag(nbt);

        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public ItemStack setIntegerTag(ItemStack item, NamespacedKey key, int value) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return item;
        nbt.setInt(key.toString(), value);
        i.setTag(nbt);

        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public ItemStack setDoubleTag(ItemStack item, NamespacedKey key, double value) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return item;
        nbt.setDouble(key.toString(), value);
        i.setTag(nbt);

        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public String getStringTagValue(ItemStack item, NamespacedKey key) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return null;
        return nbt.getString(key.toString());
    }

    @Override
    public Integer getIntegerTagValue(ItemStack item, NamespacedKey key) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return null;
        return nbt.getInt(key.toString());
    }

    @Override
    public Double getDoubleTagValue(ItemStack item, NamespacedKey key) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return null;
        return nbt.getDouble(key.toString());
    }

    @Override
    public boolean hasTag(ItemStack item, NamespacedKey key) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return false;

        return nbt.hasKey(key.toString());
    }
}
