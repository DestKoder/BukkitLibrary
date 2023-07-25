package ru.dest.library.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.object.IItem;
import ru.dest.library.object.RegistryKey;

import java.util.Objects;

public class CustomItem implements IItem {

    private final RegistryKey key;
    private final ItemStack bukkitItem;

    public CustomItem(@NotNull RegistryKey key, @NotNull ItemStack bukkitItem) {
        this.key = key;

        this.bukkitItem =  Library.getInstance().getNmsTagUtils().setStringTag(bukkitItem, "itemid", this.key.toString());

        ItemRegistry.get().register(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if(o.getClass() == o.getClass()){
            CustomItem that = (CustomItem) o;

            if (!Objects.equals(key, that.key)) return false;
            return Objects.equals(bukkitItem, that.bukkitItem);
        }else if (o.getClass() == ItemStack.class){
            ItemStack i = (ItemStack) o;
            CustomItem item = ItemRegistry.get().getItem(i);
            return equals(item);
        }else return false;
    }

    @Deprecated
    public CustomItem(@NotNull NamespacedKey key, @NotNull ItemStack bukkitItem) {
        this.key = RegistryKey.fromString(key.toString());

        this.bukkitItem =  Library.getInstance().getNmsTagUtils().setStringTag(bukkitItem, "itemid", this.key.toString());;
    }

    public RegistryKey getKey() {
        return key;
    }
    @Override
    public ItemStack getItem() {
        return bukkitItem;
    }
}
