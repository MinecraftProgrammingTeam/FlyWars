package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        // 当玩家退出游戏时
        // 拿到玩家
        Player p = event.getPlayer();
        // 遍历team
        Game.teams.forEach(it -> {
            // 如果某个team里有这个退出游戏的玩家
            if (it.isPlayerInTeam(p)){

            }
        });
    }
}
