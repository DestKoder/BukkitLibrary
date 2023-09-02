package ru.dest.library.scoreboard;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.dest.library.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabScoreboard extends ScoreboardService<TabAPI>{

    private final Map<String, Scoreboard> registeredBoards = new HashMap<>();
    public TabScoreboard() {
        super(TabAPI.getInstance());
    }

    @Override
    public void setScoreboard(Player player, String title, List<String> lines) {
        if(registeredBoards.containsKey(title)){
            t.getScoreboardManager().showScoreboard(t.getPlayer(player.getUniqueId()), registeredBoards.get(title));
            return;
        }
        Scoreboard sb = t.getScoreboardManager().createScoreboard("sb_"+ TimeUtils.getCurrentUnixTime(), title, lines);
        t.getScoreboardManager().showScoreboard(t.getPlayer(player.getUniqueId()), sb);
        registeredBoards.put(title, sb);
    }

    @Override
    public void returnDefaultScoreboard(@NotNull Player player) {
        t.getScoreboardManager().resetScoreboard(t.getPlayer(player.getUniqueId()));
    }
}
