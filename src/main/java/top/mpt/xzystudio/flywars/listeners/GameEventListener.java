package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.events.GameOverEvent;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.EventUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Collections;
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
        PlayerUtils.showTitle(op, "#RED#你的队友 <%s> 寄了！", "即将变为观察者模式", Collections.singletonList(p.getName()), null); // 给另一名无辜的队友展示消息
        ChatUtils.broadcast("[FlyWars] %s被%s淘汰了！", team.getTeamDisplayName(), killer != null ? killer.getTeamDisplayName() : ""); // 公开处刑

        TeamInfo info = Game.scoreboardManager.getInfo(team);
        info.setAlive(false);
        if (killer != null) {
            TeamInfo killerInfo = Game.scoreboardManager.getInfo(killer);
            killerInfo.addKillCount();
        }
        Game.scoreboardManager.renderScoreboard();

        // 判断是不是只剩最后一个队伍（胜利）
        if (Game.teams.size() == 1){
            GameOverEvent gameOverEvent = new GameOverEvent(Game.teams.get(0));
            EventUtils.callEvent(gameOverEvent);
        } else if (Game.teams.isEmpty()) {
            Main.instance.getLogger().info(ChatUtils.translateColor("#RED#好奇怪，真的奇怪，为啥teams空了"));
        }
    }

    @EventHandler
    public void onGameOver(GameOverEvent event) {
        GameTeam winner = event.getWinner();
        // Game.scoreboardManager.reset(); // TODO 这里注释掉是因为四个人测试的时候，某一个team阵亡之后，计分板不会接着删除，是为了查看计分板工作正不正常，测试结束后请取消注释！
        Game.teams.forEach(GameTeam::unregTeam);
        Game.teams.clear();
        Game.teams.forEach(team -> {
            TeamInfo info = Game.scoreboardManager.getInfo(team);
            ArrayList<Player> players = new ArrayList<>(team.players.keySet());
            players.forEach(p -> {
                PlayerUtils.showTitle(p, "#GREEN#游戏结束！", "#GOLD#恭喜%s取得胜利！",
                        null, Collections.singletonList(winner.getTeamDisplayName()));
                PlayerUtils.send(p, "          %s          ", info.getAlive() ? "#GOLD#你的队伍胜利了！" : "#RED#你的队伍失败了！");
                PlayerUtils.send(p, "  队伍成员：#BLUE#%s  ", players.stream().map(Player::getName).collect(Collectors.joining(", ")));
                PlayerUtils.send(p, "  本局游戏队伍击杀数：#YELLOW#%s  ", info.getKillCount());

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
    }
}
