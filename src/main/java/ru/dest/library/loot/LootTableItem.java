package ru.dest.library.loot;

import org.bukkit.inventory.ItemStack;
import ru.dest.library.utils.chance.ChanceObject;

public class LootTableItem extends ChanceObject {

    private final ItemStack item;
    public LootTableItem(ItemStack item, double chance) {
        super(chance);
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
