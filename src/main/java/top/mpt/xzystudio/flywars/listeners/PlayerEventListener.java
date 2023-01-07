package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

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
                Player op = it.getTheOtherPlayer(p);
                PlayerUtils.showTitle(p, "#RED#您的队友已退出，队伍解散", "即将变为观察者模式");
                op.setGameMode(GameMode.SPECTATOR);
                it.getBoard().deleteBoard();
                it.unregTeam();
                Game.teams.remove(it);
                ChatUtils.broadcast("[FlyWars] %s被淘汰了", it.getTeamDisplayName());
                Game.teamDeath(it);
            }
        });
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if (event.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) event.getEntity();
            Game.teams.forEach(it -> {
                if (it.isPlayerInTeam(p)){
                    Player op = it.getTheOtherPlayer(p);
                    Game.teammateDamage(p, it);
                }
            });
        }
    }
}
