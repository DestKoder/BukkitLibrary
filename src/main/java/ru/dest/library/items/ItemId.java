package ru.dest.library.items;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemId {

    private final String plugin;
    private final String id;

    public ItemId(@NotNull Plugin plugin, String id) {
        this.plugin = plugin.getName().toLowerCase();
        this.id = id;
    }

    public ItemId(String plugin, String id) {
        this.plugin = plugin.toLowerCase();
        this.id = id;
    }

    @Contract("_ -> new")
    public static @NotNull ItemId fromString(@NotNull String s){
        String[] data = s.split(":", 2);

        return new ItemId(data[0], data[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemId itemId = (ItemId) o;

        if (!Objects.equals(plugin, itemId.getPlugin())) return false;
        return Objects.equals(id, itemId.getId());
    }

    public String getPlugin() {
        return plugin;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return plugin.toLowerCase()+id;
    }
}
