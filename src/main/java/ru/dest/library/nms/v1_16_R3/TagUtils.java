package ru.dest.library.nms.v1_16_R3;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class TagUtils implements ru.dest.library.nms.TagUtils {

    public TagUtils(){};

    @Override
    public ItemStack setStringTag(ItemStack item, String key, String value) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return item;
        nbt.setString(key, value);
        i.setTag(nbt);

        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public ItemStack setIntegerTag(ItemStack item, String key, int value) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return item;
        nbt.setInt(key, value);
        i.setTag(nbt);

        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public ItemStack setDoubleTag(ItemStack item, String key, double value) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return item;
        nbt.setDouble(key, value);
        i.setTag(nbt);

        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public String getStringTagValue(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return null;
        return nbt.getString(key);
    }

    @Override
    public Integer getIntegerTagValue(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return null;
        return nbt.getInt(key);
    }

    @Override
    public Double getDoubleTagValue(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return null;
        return nbt.getDouble(key);
    }

    @Override
    public boolean hasTag(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = i.getTag();
        if(nbt == null) return false;
        return nbt.hasKey(key);
    }

}
