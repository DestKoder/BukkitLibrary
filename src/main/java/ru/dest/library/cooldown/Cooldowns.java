package ru.dest.library.cooldown;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dest.library.task.TaskManager;
import ru.dest.library.utils.TimeUtils;

import java.util.*;

/**
 * This class represents a manager for memory-stored cooldowns
 *
 * @since 1.0
 * @author DestKoder
 */
public class Cooldowns {

    private final Plugin plugin;

    public Cooldowns(Plugin plugin) {
        this.plugin = plugin;
    }

    public void start(){
        TaskManager.get().callRepeating( "cd"+ TimeUtils.getCurrentUnixTime(),plugin,20, false, new BukkitRunnable() {
            @Override
            public void run() {
                data.values().forEach(dataList -> {
                    dataList.removeIf(data -> data.getExpires()-TimeUtils.getCurrentUnixTime() == 0);
                });
            }
        });
    }

    protected final Map<UUID, List<CooldownData>> data = new HashMap<>();

    public void setOnCooldown(@NotNull Player player,@NotNull String action, int timeInSeconds){
        long expires = TimeUtils.getCurrentUnixTime() + timeInSeconds;

        if(!data.containsKey(player.getUniqueId())) data.put(player.getUniqueId(), new ArrayList<>());

        List<CooldownData> dat = data.get(player.getUniqueId());

        CooldownData d = getCooldownData(dat, action);

        if(d != null) {
            d.setExpires(expires);
        }else {
            dat.add(new CooldownData(action, expires));
        }
    }

    public boolean isOnCooldown(@NotNull Player player,@NotNull String action){
        if(!data.containsKey(player.getUniqueId())) return false;

        return getCooldownData(data.get(player.getUniqueId()), action) != null;
    }

    public long getLeftTime(@NotNull Player player, String action){
        if(!data.containsKey(player.getUniqueId())) return 0;

        CooldownData data = getCooldownData(this.data.get(player.getUniqueId()), action);

        if(data == null) return 0;
        return data.getExpires()-TimeUtils.getCurrentUnixTime();
    }

    @Nullable
    private CooldownData getCooldownData(@NotNull List<CooldownData> data,@NotNull String action){
        for(CooldownData d : data){
            if(d.getAction().equalsIgnoreCase(action)) return d;
        }
        return null;
    }

}
