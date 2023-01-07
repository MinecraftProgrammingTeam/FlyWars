package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.Collections;

public class TeamEventListener implements Listener {
    @EventHandler
    public void onTeamEliminated(TeamEliminatedEvent event) {
        Player p = event.getPlayer();
        GameTeam team = event.getTeam();
        GameTeam killer = event.getKiller();
        Player op = team.getTheOtherPlayer(p); // 获取到同一个团队的另一名玩家
        // 将嗝屁的玩家设置为旁观者模式
        op.setGameMode(GameMode.SPECTATOR);
        p.setGameMode(GameMode.SPECTATOR);
        PlayerUtils.showTitle(op, "#RED#你的队友 <%s> 寄了！", "即将变为观察者模式", Collections.singletonList(p.getName()), null); // 给另一名无辜的队友展示消息
        team.getBoard().deleteBoard();  // 删除该团队计分板
        team.unregTeam();
        Game.teams.remove(team); // 移除团队
        String teamDisplayName = team.getTeamDisplayName();
        ChatUtils.broadcast("[FlyWars] %s被淘汰了！", team.getTeamDisplayName()); // 公开处刑

        Game.teamDeath(team);

        // 判断是不是只剩最后一个队伍（胜利）
        if (Game.teams.size() == 1){
            Main.instance.getLogger().info(ChatColor.GREEN + "GameOver!");
            Game.gameOver();
        } else if (Game.teams.isEmpty()) {
            Main.instance.getLogger().info(ChatUtils.translateColor("#RED#好奇怪，真的奇怪，为啥teams空了"));
        }
    }
}
