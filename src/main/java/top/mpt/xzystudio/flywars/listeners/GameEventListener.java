package top.mpt.xzystudio.flywars.listeners;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import top.mpt.xzystudio.flywars.events.GameOverEvent;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 游戏相关事件监听器
 */
public class GameEventListener implements Listener {
    @EventHandler
    public void onTeamEliminated(TeamEliminatedEvent event) {
        Player p = event.getPlayer();
        GameTeam team = event.getTeam();
        GameTeam killer = event.getKiller();
        Player op = team.getTheOtherPlayer(p); // 获取到同一个团队的另一名玩家
        // 将嗝屁的玩家设置为旁观者模式
        if (op.isOnline()) op.setGameMode(GameMode.SPECTATOR);
        if (p.isOnline()) p.setGameMode(GameMode.SPECTATOR);
        PlayerUtils.showTitle(op, "#RED#你的队友 %s 寄了！", "即将变为观察者模式", Collections.singletonList(p.getName()), null); // 给另一名无辜的队友展示消息 // 这里无需在%s外面加<>，因为会有[]
        ChatUtils.broadcast("[FlyWars] %s被%s淘汰了！", team.getTeamDisplayName(), killer != null ? killer.getTeamDisplayName() : ""); // 公开处刑

        TeamInfo info = Game.scoreboardManager.getInfo(team);
        info.setAlive(false);
        if (killer != null) {
            TeamInfo killerInfo = Game.scoreboardManager.getInfo(killer);
            killerInfo.addKillCount();
        }
        Game.scoreboardManager.renderScoreboard();

        // 判断是不是只剩最后一个队伍（胜利）
        AtomicInteger ifGameOver = new AtomicInteger();

        GameTeam aliveTeam = GameUtils.getTeamBy(t -> Game.scoreboardManager.getInfo(t).getAlive());
        if (aliveTeam != null) ifGameOver.getAndIncrement();

        // TODO EJECT不知道管不管用，同时解散队伍
        p.eject();
        op.eject();
        team.unregTeam();

        // GameOver
        if (ifGameOver.get() == 1){
            GameOverEvent gameOverEvent = new GameOverEvent(aliveTeam);
            GameUtils.callEvent(gameOverEvent);
        } else if (ifGameOver.get() == 0) {
            LoggerUtils.warning("#RED#请勿在玩家数不足4个时开始游戏");

            GameOverEvent gameOverEvent = new GameOverEvent(team);
            GameUtils.callEvent(gameOverEvent);
        }
    }

    @EventHandler
    public void onGameOver(GameOverEvent event) {
        // 游戏结束时，获取胜利的team
        GameTeam winner = event.getWinner();
        // 重置计分板
        Game.scoreboardManager.reset();
        // 取消资源刷新
        if (Game.resUpdater != null) Game.resUpdater.cancel();
        // 清除世界内的掉落物
        event.getWinner().getP1().getWorld().getEntities().forEach(it -> {
            if (it.getType() == EntityType.DROPPED_ITEM) it.remove();
        });
        // 遍历teams数组
        Game.teams.forEach(team -> {
            // 把每个team注销
//            team.unregTeam(); // 上面队伍解散事件中已经unreg过了，重复unreg可能异常
            // 获取团队信息
            TeamInfo info = Game.scoreboardManager.getInfo(team);
            // 获取每个玩家
            ArrayList<Player> players = new ArrayList<>(team.players.keySet());
            // 遍历每个玩家
            players.forEach(p -> {
                // 发放奖励
                if (info.getAlive()){
                    ConfigUtils.getMapListConfig("prize").forEach(it -> {
                        String material = (String) it.get("material");
                        material = material.toUpperCase();
                        int amount = (Integer) it.get("amount");
                        String name = (String) it.get("name");

                        p.getInventory().addItem(GameUtils.newItem(Material.valueOf(material), name, amount));
                    });
                }

                // 给玩家显示标题
                PlayerUtils.showTitle(p, "#GREEN#游戏结束！", "#GOLD#恭喜#RESET#%s#GOLD#取得胜利！",
                        null, Collections.singletonList(winner.getTeamDisplayName()));// 不知道为啥这样会显示#GOLD#[#AQUA#[青队#AQUA#]#RESET#]
                // 给胜利者和失败着分别显示不同消息
                PlayerUtils.send(p, "          %s          ", info.getAlive() ? "#GOLD#你的队伍胜利了！" : "#RED#你的队伍失败了！");
                PlayerUtils.send(p, "  队伍成员：#AQUA# %s  ", players.stream().map(Player::getName).collect(Collectors.joining(", ")));
                PlayerUtils.send(p, "  本局游戏队伍击杀数： #YELLOW#%s  ", info.getKillCount());

                // 不能让玩家白嫖鞘翅金苹果和烟花火箭吧 - addd
                p.getInventory().clear();
                // 将玩家传送至指定的坐标
                // 不能让玩家在空中就切生存吧qwq
                Location loc = new Location(p.getWorld(),
                        (Integer) ConfigUtils.getConfig("end-x", 0),
                        (Integer) ConfigUtils.getConfig("end-y", 0),
                        (Integer) ConfigUtils.getConfig("end-z", 0));
                p.teleport(loc);
                // 切换回冒险
                p.setGameMode(GameMode.ADVENTURE);
            });
        });
        // 清空数组
        Game.teams.clear();
    }
}
