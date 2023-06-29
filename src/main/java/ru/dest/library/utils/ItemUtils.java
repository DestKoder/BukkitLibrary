package ru.dest.library.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.Library;
import ru.dest.library.exception.InvalidMaterialException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ItemUtils {

    public static final ItemStack EMPTY = new ItemStack(Material.AIR);

    @Contract("_ -> new")
    @NotNull
    public static ItemStack createByMaterial(@NotNull String material){
        if(material.startsWith("head:")) {
            return Objects.requireNonNull(createHead(material.replace("head:", "")));
        }

        material = material.toUpperCase();
        if(!material.contains(":")) {
            Material mat = Material.getMaterial(material);

            if(mat == null) {
                throw new InvalidMaterialException("Material with name " + material + " doesn't exists");
            }
            return new ItemStack(mat);
        }
        String[] data = material.split(":");

        if(data.length < 2) {
            throw new InvalidMaterialException(material);
        }

        Material mat = Material.getMaterial(data[0]);

        if(mat == null) {
            throw new InvalidMaterialException("Material with name " + data[0] + " doesn't exists");
        }

        if(data.length == 3) {
            return new ItemStack(mat,Parser.parseInt(data[1]), Short.parseShort(data[2]));
        }

        return new ItemStack(mat, Parser.parseInt(data[1]));
    }

    @Nullable
    public static ItemStack createHead(String textureId) {
        ItemStack head = Library.getInstance().getSkullItem();
        assert head != null;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", textureId));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

            head.setItemMeta(headMeta);
            return head;
        }catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    @NotNull
    public static ItemStack createHead(OfflinePlayer player){
        ItemStack head = Library.getInstance().getSkullItem();
        assert head != null;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        headMeta.setOwningPlayer(player);

        head.setItemMeta(headMeta);
        return head;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - MaterialName of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull String material, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = createByMaterial(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - {@link Material} of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull Material material, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - MaterialName of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull String material, int id, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = createByMaterial(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));
        Library lb = Library.getInstance();
        if(lb.getNmsTagUtils() != null) item =  lb.getNmsTagUtils().setIntegerTag(item, "customModelData" ,id);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create {@link ItemStack} with given info
     * @param material - {@link Material} of the item
     * @param name - Custom name of the item
     * @param lore - Custom lore of the item
     * @return new {@link ItemStack} with given info;
     */
    @NotNull
    public static ItemStack createItem(@NotNull Material material, int id, @Nullable String name, @Nullable List<String> lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return item;

        if(name != null) meta.setDisplayName(ChatUtils.parse(name));
        if(lore != null && !lore.isEmpty()) meta.setLore(ChatUtils.parse(lore));
        Library lb = Library.getInstance();
        if(lb.getNmsTagUtils() != null) item =  lb.getNmsTagUtils().setIntegerTag(item, "customModelData" ,id);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Apply enchantments to item
     * @param item - item to apply
     * @param enchantments Section or map of enchantments and it's levels
     * @return given item with applied enchantments
     */
    @NotNull
    public static ItemStack applyEnchantments(@NotNull ItemStack item, ConfigurationSection enchantments){
        for(String s : enchantments.getKeys(false)){
            Enchantment ench = Enchantment.getByName(s);
            if(ench == null) continue;

            item.addUnsafeEnchantment(ench, enchantments.getInt(s));
        }

        return item;
    }

    /**
     * Apply enchantments to item
     * @param item - item to apply
     * @param enchantments Section or map of enchantments and it's levels
     * @return given item with applied enchantments
     */
    public static ItemStack applyEnchantments(@NotNull ItemStack item, @NotNull Map<String, Integer> enchantments){
        enchantments.forEach((name, level) -> {
            Enchantment ench = Enchantment.getByName(name);
            if(ench != null) item.addUnsafeEnchantment(ench, level);
        });

        return item;

    }
}
