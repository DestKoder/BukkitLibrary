package ru.dest.library.items.i;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface IConsumeableItem {

    void onConsume(PlayerItemConsumeEvent event);
}
