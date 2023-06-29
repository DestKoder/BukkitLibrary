package ru.dest.library.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private static ItemRegistry instance;

    private final Map<ItemId, CustomItem> itemMap;

    public ItemRegistry(){
        itemMap = new HashMap<>();
        instance = this;
    }

    public static ItemRegistry get() {
        if(Library.getInstance().getNmsTagUtils() == null) throw new UnsupportedOperationException("Tag support isn't enabled. Custom Items will not work.");
        return instance == null ? new ItemRegistry() : instance;
    }

    public void register(CustomItem item) {
        this.itemMap.put(item.getKey(), item);
    }

    public void unregister(ItemId key){
        this.itemMap.remove(key);
    }

    @Deprecated
    public void unregister(@NotNull NamespacedKey key){
        this.itemMap.remove(ItemId.fromString(key.toString()));
    }

    public CustomItem getItem(ItemId key) {
        return itemMap.get(key);
    }

    @Deprecated
    public CustomItem getItem(@NotNull NamespacedKey key){
        return itemMap.get(ItemId.fromString(key.toString()));
    }

    public CustomItem getItem(@NotNull ItemStack item){
        String itemId = Library.getInstance().getNmsTagUtils().getStringTagValue(item, "itemid");

        if(itemId == null) return null;

        return getItem(ItemId.fromString(itemId));
    }

    public boolean isCustomItem(ItemStack item){
        String itemId = Library.getInstance().getNmsTagUtils().getStringTagValue(item, "itemid");
        return itemId != null;
    }
}
