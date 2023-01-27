package top.mpt.xzystudio.flywars.scheduler;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.events.GameOverEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.GameUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class TimeLimit extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.gameStatus){
            ChatUtils.broadcast("#AQUA#[FlyWars] #RED#游戏时间已到！结束游戏！");

            AtomicReference<String> winnerDisplayName = new AtomicReference<>();
            Game.teams.forEach(team -> {
                TeamInfo info = Game.scoreboardManager.getInfo(team);
                if (info.getAlive()) {
                    winnerDisplayName.set(winnerDisplayName + team.getTeamDisplayName() + " ");
                }
            });

            GameOverEvent gameOverEvent = new GameOverEvent(null, winnerDisplayName.get());
            GameUtils.callEvent(gameOverEvent);
        }
    }
}