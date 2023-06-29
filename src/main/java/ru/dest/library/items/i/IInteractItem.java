package ru.dest.library.items.i;

import org.bukkit.event.player.PlayerInteractEvent;

public interface IInteractItem {
    void onInteract(PlayerInteractEvent event);
}
