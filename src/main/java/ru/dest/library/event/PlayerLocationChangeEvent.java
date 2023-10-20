package ru.dest.library.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerLocationChangeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList list = new HandlerList();

    private final Location lastLocation, newLocation;
    private boolean cancelled;

    public PlayerLocationChangeEvent(Player who, Location lastLocation, Location newLocation) {
        super(who);
        this.lastLocation = lastLocation;
        this.newLocation = newLocation;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return list;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public Location getNewLocation() {
        return newLocation;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList(){
        return list;
    }
}
