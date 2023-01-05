package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.Team;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;

public class customEventListener implements Listener {
    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (damager.getType() == EntityType.PLAYER && entity.getType() == EntityType.PLAYER) {
            for (Team team : Game.teams) {
                if (team.isTeammate((Player) damager, (Player) entity)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        // 当玩家嗝屁时
        Player p = event.getEntity();
        ArrayList<Team> temp = new ArrayList<>(Game.teams);
        temp.forEach(it -> { // 遍历团队 - 为啥总感觉这样更慢，不如使用iter（  - 不会慢到哪的 - 彳亍，我相信jvav的效率 - addd - 6 - 9
            if (it.players.containsKey(p)) {
                Player op = it.getTheOtherPlayer(p); // 获取到同一个团队的另一名玩家
                // 将嗝屁的玩家设置为旁观者模式
                op.setGameMode(GameMode.SPECTATOR);
                p.setGameMode(GameMode.SPECTATOR);
                PlayerUtils.showTitle(op, String.format("&c你的队友 <%s> 寄了！", p.getName()), "即将变为观察者模式"); // 给另一名无辜的队友展示消息
//                PlayerUtils.showTitle(op, "&c你输了！", String.format("你的辣鸡队友 <%s> 寄了", p.getName())); // 给另一名无辜的队友展示消息
                Game.teams.remove(it); // 移除团队
                ChatUtils.broadcast("[FlyWars] &9<%s> 和 <%s> 阵亡了！", p.getName(), op.getName()); // 公开处刑
            }
        });
        // 判断是不是只剩最后一个队伍（胜利）
        if (Game.teams.size() == 1){
            Game.gameOver();
        }
    }
}
