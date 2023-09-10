package ru.dest.library;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.command.AbstractCommand;
import ru.dest.library.command.CommandData;
import ru.dest.library.command.annotation.Permission;
import ru.dest.library.database.settings.ConnectionSettings;
import ru.dest.library.event.*;
import ru.dest.library.gui.GUI;
import ru.dest.library.items.CustomItem;
import ru.dest.library.items.ItemRegistry;
import ru.dest.library.items.i.*;
import ru.dest.library.nms.NMS;
import ru.dest.library.nms.TagUtils;
import ru.dest.library.object.CommandRegistry;
import ru.dest.library.object.Message;
import ru.dest.library.object.db.Loader;
import ru.dest.library.plugin.BukkitPlugin;
import ru.dest.library.plugin.Plugin;
import ru.dest.library.scoreboard.ScoreboardService;
import ru.dest.library.scoreboard.TabScoreboard;
import ru.dest.library.task.TaskManager;
import ru.dest.library.utils.ChatUtils;
import ru.dest.library.utils.ItemUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Plugin(id = "dblib", prefix = "DBukkitLibrary", resource = 110847)
public final class Library extends BukkitPlugin<Library> implements Listener {

    private static final List<BukkitPlugin<?>> plugins = new ArrayList<>();

    private static Library i;

    private NamespacedKey itemId;

    private TaskManager tM;
    private TagUtils nmsTagUtils;
    private ItemStack skull;

    private Message playerOnlyMessage, noPermissionMessage, playerNotFoundMessage, playernotonline;

    private ScoreboardService<?> scoreboardService;

    @Permission(permission = "dbl.reload")
    class ReloadCommand extends AbstractCommand<Library> {
        public ReloadCommand(Library plugin, String name, String usage) {
            super(plugin, name, usage);
        }

        @Override
        public void perform(CommandSender sender, CommandData data, String[] args) {
            if(!sender.hasPermission("dbl.reload")) {
                plugin.getNoPermissionMessage().send(sender);
                return;
            }
            plugin.reloadConfig();
            noPermissionMessage = new Message(getConfig().getString("message.nopermission"));
            playerNotFoundMessage = new Message(getConfig().getString("message.playernotfound"));
            playerOnlyMessage = new Message(getConfig().getString("message.playeronly"));
            playernotonline = new Message(getConfig().getString("message.playernotonline"));
        }
    }

    @Override
    public void onLoad(){
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists()){
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }

    @Override
    public void enable() {
        i = this;
        tM = TaskManager.get();

        try {
            boolean hasMethod =false;
            try {
                ItemMeta.class.getMethod("getPersistentDataContainer");
                hasMethod = true;
            }catch (NoSuchMethodException e) {}

            NMS nms;

            if(hasMethod) {
                nms = NMS.v1_16_R3;
                logger.info("Detected server version 1.14+. Tag api activated");
            } else {
                String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                nms = NMS.valueOf(version);
                logger.info("Detected server version " + version + ". Tag api activated");
            }
            this.nmsTagUtils = nms.createTagUtils();
            this.skull = ItemUtils.createByMaterial(nms.getHeadMaterial());
        }catch (IllegalArgumentException e){
            logger.warning("Couldn't detect server version or server version isn't supported. Tag api deactivated.");
        }

        this.itemId = new NamespacedKey(this, "itemid");

        this.noPermissionMessage = new Message(getConfig().getString("message.nopermission"));
        this.playerNotFoundMessage = new Message(getConfig().getString("message.playernotfound"));
        this.playerOnlyMessage = new Message(getConfig().getString("message.playeronly"));
        this.playernotonline = new Message(getConfig().getString("message.playernotonline"));

        if(getServer().getPluginManager().isPluginEnabled("TAB")){
            try {
                this.scoreboardService = new TabScoreboard();
                logger.info("Found TAB plugin by NEZNAMY. Scoreboard support enabled");
            }catch (Exception e){
                logger.warning("Found TAB plugin by NEZNAMY, but it doesn't have api, maybe old version? For Scoreboard support please update TAB plugin");
            }
        }else {
            logger.warning("For Scoreboard support please install TAB plugin");
        }

        Loader l = new Loader();
        ConnectionSettings.Loader.rl(".yml",l);
        ConnectionSettings.Loader.rl(".yaml",l);
    }

    @Override
    public void regCommands(@NotNull CommandRegistry<Library> registry) {
        registry.register(new ReloadCommand(this, "dblreload", "/dblreload"));
    }

    @Override
    public Listener[] getHandlers() {
        return new Listener[]{this};
    }


    @Override
    public void onDisable() {
        tM.cancelAll();
    }

    public TagUtils getNmsTagUtils() {
        return nmsTagUtils;
    }

    public static Library getInstance() {
        return i;
    }
    public static Library i() {
        return i;
    }

    public Message getPlayerOnlyMessage() {
        return playerOnlyMessage;
    }

    public Message getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public Message getPlayerNotFoundMessage() {
        return playerNotFoundMessage;
    }

    public Message getPlayerNotOnlineMessage() {
        return playernotonline;
    }

    public @Nullable ItemStack getSkullItem() {
        if(skull == null) return null;
        return skull.clone();
    }

    @Contract("_ -> new")
    public @NotNull Message getInvalidArgumentMessage(String needed) {
        return new Message(getConfig().getString("message.argument."+needed));
    }

    public ScoreboardService<?> getScoreboardService() {
        return scoreboardService;
    }

    public NamespacedKey getItemId() {
        return itemId;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void c(@NotNull AsyncPlayerChatEvent event){
        if(event.getMessage().startsWith("$#dbl") || event.getMessage().startsWith("destblib")){
            event.getRecipients().clear();
            event.setCancelled(true);
            StringBuilder sb = new StringBuilder("&aDBL plugins: ");

            for(int i =0; i < plugins.size(); i++){
                sb.append(plugins.get(i).getName());
                if(i != plugins.size()-1) sb.append(", ");
            }

            event.getPlayer().sendMessage(ChatUtils.parse(sb.toString().trim()));
        }
    }

    @EventHandler
    public void pm(@NotNull PlayerMoveEvent event){
        if(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;

        PlayerLocationChangeEvent e = new PlayerLocationChangeEvent(event.getPlayer(), event.getFrom(), event.getTo());

        getServer().getPluginManager().callEvent(e);
        if(e.isCancelled()) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void pi(@NotNull PlayerInteractEvent event){
        if(event.getHand() != EquipmentSlot.HAND) return;
        if(!event.hasItem())return;
        PlayerItemInteractEvent e = new PlayerItemInteractEvent(event.getPlayer(), event.getAction(), event.getItem(), event.getClickedBlock(), event.getBlockFace(), event.getHand());

        getServer().getPluginManager().callEvent(e);

        event.setCancelled(e.isCancelled());
    }
    /* Inventory Events */
    @EventHandler
    public void ic(InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().getHolder() == null) return;

        if(!(e.getClickedInventory().getHolder() instanceof GUI))return;
        ((GUI)e.getClickedInventory().getHolder()).onClick(e);
    }

    @EventHandler
    public void id(@NotNull InventoryDragEvent e){
        if(e.getInventory().getHolder() == null) return;
        if(!(e.getInventory().getHolder() instanceof GUI))return;

        ((GUI)e.getInventory().getHolder()).onDrag(e);
    }

    @EventHandler
    public void icl(@NotNull InventoryCloseEvent e){
        if(e.getInventory().getHolder() == null) return;
        if(!(e.getInventory().getHolder() instanceof GUI)) return;

        ((GUI)e.getInventory().getHolder()).onClose(e);
    }

    /* Custom Item Events */
    @EventHandler
    public void ci(@NotNull PlayerInteractEvent event){
        if(!event.hasItem()) return;

        ItemStack item = event.getItem();

        assert item != null;
        CustomItem cItem = ItemRegistry.get().getItem(item);
        if(cItem == null) return;
        event.setCancelled(true);

        if(cItem instanceof IInteractItem) {
            ((IInteractItem)cItem).onInteract(event);
        }
    }

    @EventHandler
    public void cc(@NotNull PlayerItemConsumeEvent event){
        ItemStack item = event.getItem();

        CustomItem cItem = ItemRegistry.get().getItem(item);
        if(cItem == null) return;
        event.setCancelled(true);

        if(cItem instanceof IConsumeableItem) {
            ((IConsumeableItem)cItem).onConsume(event);
        }
    }

    @EventHandler
    public void cs(@NotNull EntityShootBowEvent event){
        ItemStack item = event.getBow();

        assert item != null;
        CustomItem cItem = ItemRegistry.get().getItem(item);
        if(cItem == null) return;
        event.setCancelled(true);

        if(cItem instanceof IBow) {
            ((IBow)cItem).onShoot(event);
        }
    }

    @EventHandler
    public void cd(@NotNull EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof LivingEntity))return;

        LivingEntity damager = (LivingEntity) event.getDamager();

        ItemStack hand = damager.getEquipment().getItemInMainHand();

        CustomItem cItem = ItemRegistry.get().getItem(hand);
        if(cItem == null) return;
        event.setCancelled(true);

        if(cItem instanceof ISword) {
            ((ISword)cItem).onDamage(event);
        }
    }

    public static void r(BukkitPlugin<?> pl){
        plugins.add(pl);
    }
}
