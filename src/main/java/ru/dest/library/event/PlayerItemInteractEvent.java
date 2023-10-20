package ru.dest.library.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerItemInteractEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player who;
    private final Action action;
    private final ItemStack item;
    private final Block clickedBlock;
    private final BlockFace clickedFace;
    private final EquipmentSlot hand;

    private boolean cancelled;

    public PlayerItemInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace, EquipmentSlot hand) {
        this.who = who;
        this.action = action;
        this.item = item;
        this.clickedBlock = clickedBlock;
        this.clickedFace = clickedFace;
        this.hand = hand;
    }

    public Player getWho() {
        return who;
    }

    public Action getAction() {
        return action;
    }

    public ItemStack getItem() {
        return item;
    }

    public Block getClickedBlock() {
        return clickedBlock;
    }

    public BlockFace getClickedFace() {
        return clickedFace;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    public boolean hasItem() {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
