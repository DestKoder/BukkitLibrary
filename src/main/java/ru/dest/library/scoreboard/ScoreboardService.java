package ru.dest.library.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class ScoreboardService<T> {

    T t;

    public ScoreboardService(T t) {
        this.t = t;
    }

    public abstract void setScoreboard(Player player, String title, List<String> lines);
    public abstract void returnDefaultScoreboard(Player player);
}
