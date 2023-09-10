package ru.dest.library.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.Library;
import ru.dest.library.object.RegistryKey;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private static ItemRegistry instance;

    private final Map<RegistryKey, CustomItem> itemMap;

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

    public void unregister(RegistryKey key){
        this.itemMap.remove(key);
    }

    @Deprecated
    public void unregister(@NotNull NamespacedKey key){
        this.itemMap.remove(RegistryKey.fromString(key.toString()));
    }

    public CustomItem getItem(RegistryKey key) {
        return itemMap.get(key);
    }

    @Deprecated
    public CustomItem getItem(@NotNull NamespacedKey key){
        return itemMap.get(RegistryKey.fromString(key.toString()));
    }

    public CustomItem getItem(@NotNull ItemStack item){
        String itemId = Library.getInstance().getNmsTagUtils().getStringTagValue(item, Library.i().getItemId());

        if(itemId == null || itemId.isEmpty()) return null;

        return getItem(RegistryKey.fromString(itemId));
    }

    public boolean isCustomItem(ItemStack item){
        String itemId = Library.getInstance().getNmsTagUtils().getStringTagValue(item, Library.i().getItemId());
        return itemId != null && !itemId.isEmpty();
    }
}
