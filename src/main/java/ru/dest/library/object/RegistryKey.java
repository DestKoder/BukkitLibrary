package ru.dest.library.object;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegistryKey {

    private final String namespace;
    private final String id;

    public RegistryKey(@NotNull Plugin plugin, String id) {
        this.namespace = plugin.getName().toLowerCase();
        this.id = id;
    }

    public RegistryKey(@NotNull String plugin, String id) {
        this.namespace = plugin.toLowerCase();
        this.id = id;
    }

    @Contract("_ -> new")
    public static @NotNull RegistryKey fromString(@NotNull String s){
        String[] data = s.split(":", 2);

        return new RegistryKey(data[0], data[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistryKey registryKey = (RegistryKey) o;

        if (!namespace.equalsIgnoreCase(registryKey.getNamespace())) return false;
        return id.equalsIgnoreCase(registryKey.getId());
    }

    public String getNamespace() {
        return namespace;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return namespace.toLowerCase()+id;
    }
}
