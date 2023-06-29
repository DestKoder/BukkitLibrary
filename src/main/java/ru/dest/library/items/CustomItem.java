package ru.dest.library.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.object.IItem;

public class CustomItem implements IItem {

    private ItemId key;
    private ItemStack bukkitItem;

    public CustomItem(@NotNull ItemId key, @NotNull ItemStack bukkitItem) {
        this.key = key;

        this.bukkitItem =  Library.getInstance().getNmsTagUtils().setStringTag(bukkitItem, "itemid", this.key.toString());

        ItemRegistry.get().register(this);
    }

    @Deprecated
    public CustomItem(@NotNull NamespacedKey key, @NotNull ItemStack bukkitItem) {
        this.key = ItemId.fromString(key.toString());

        this.bukkitItem =  Library.getInstance().getNmsTagUtils().setStringTag(bukkitItem, "itemid", this.key.toString());;
    }

    public ItemId getKey() {
        return key;
    }

    public ItemStack asBukkitItem() {
        return bukkitItem;
    }

    @Override
    public ItemStack getItem() {
        return asBukkitItem();
    }
}
