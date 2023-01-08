package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.mpt.xzystudio.flywars.events.GameOverEvent;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.EventUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

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
        Game.teams.forEach(it -> {
            if (ScoreboardManager.info.get(it).getAlive()) ifGameOver.getAndIncrement();
        });
        if (ifGameOver.get() == 1){
            GameOverEvent gameOverEvent = new GameOverEvent(Game.teams.get(0));
            EventUtils.callEvent(gameOverEvent);
        }
    }

    @EventHandler
    public void onGameOver(GameOverEvent event) {
        // 游戏结束时，获取胜利的team
        GameTeam winner = event.getWinner();
        Game.scoreboardManager.reset();
        // 遍历teams数组，把每个team注销
        Game.teams.forEach(GameTeam::unregTeam);
        // 遍历teams数组
        Game.teams.forEach(team -> {
            // 获取团队信息
            TeamInfo info = Game.scoreboardManager.getInfo(team);
            // 获取每个玩家
            ArrayList<Player> players = new ArrayList<>(team.players.keySet());
            // 遍历每个玩家
            players.forEach(p -> {
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
