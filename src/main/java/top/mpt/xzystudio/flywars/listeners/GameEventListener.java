package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.GameTeam;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Game相关事件监听类
 */
public class GameEventListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        // 当玩家嗝屁时
        Player p = event.getEntity();
        ArrayList<GameTeam> temp = new ArrayList<>(Game.teams);
        temp.forEach(it -> { // 遍历团队 - 为啥总感觉这样更慢，不如使用iter（  - 不会慢到哪的 - 彳亍，我相信jvav的效率 - addd - 6 - 9
            if (it.players.containsKey(p)) {
                Player op = it.getTheOtherPlayer(p); // 获取到同一个团队的另一名玩家
                // 将嗝屁的玩家设置为旁观者模式
                op.setGameMode(GameMode.SPECTATOR);
                p.setGameMode(GameMode.SPECTATOR);
                PlayerUtils.showTitle(op, "#RED#你的队友 <%s> 寄了！", "即将变为观察者模式", Collections.singletonList(p.getName()), Collections.emptyList()); // 给另一名无辜的队友展示消息
                Game.teams.remove(it); // 移除团队
                ChatUtils.broadcast("[FlyWars] #BLUE#<%s> 和 <%s> 阵亡了！", p.getName(), op.getName()); // 公开处刑
            }
        });
        // 判断是不是只剩最后一个队伍（胜利）
        if (Game.teams.size() == 1){
            Game.gameOver();
        }
    }

    @EventHandler
    public void onEntityExitVehicle(VehicleExitEvent event) {
        // 玩家从另一个玩家的身上下来的时候
        // 离开玩家的实体
        Entity passenger = event.getExited();
        // 被玩家离开的实体
        Entity vehicle = event.getVehicle();
        // 如果离开vehicle的实体是玩家，且vehicle也是玩家
        if (passenger.getType() == EntityType.PLAYER && vehicle.getType() == EntityType.PLAYER) {
            // 遍历team数组
            Game.teams.forEach(it -> {
                // 如果vehicle的玩家和vehicle是队友关系，就取消玩家的行为
                if (it.isTeammate((Player) passenger, (Player) vehicle)) event.setCancelled(true);
            });
        }
    }
}
