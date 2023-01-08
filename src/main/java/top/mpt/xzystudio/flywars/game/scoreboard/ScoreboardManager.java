package top.mpt.xzystudio.flywars.game.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 计分板类
 */
public class ScoreboardManager {
    // 向玩家展示的计分板的标题
    private static final String title = ChatUtils.translateColor("#BLUE#FlyWars #GREEN#飞行战争");
    private static final HashMap<GameTeam, HashMap<Player, FastBoard>> boards = new HashMap<>();
    private static final HashMap<GameTeam, TeamInfo> info = new HashMap<>();

    /*
    |========================|
    | FlyWars 飞行战争         |  => 标题
    |                        |  => 0
    | 红队 √ (你)             |  => 1
    | 蓝队 ×                  |  => 2
    | 绿队 √                  |  => 3
    |                        |  => teams.length + 1
    | 队友血量：|==========|   |  => teams.length + 2
    | 击杀数：5                |  => teams.length + 3
    |========================|
     */

    public TeamInfo getInfo(GameTeam team) {
        return info.get(team);
    }

    public void renderScoreboard() {
        ArrayList<GameTeam> teams = Game.teams;
        teams.forEach(team -> {
           team.players.keySet().forEach(player -> {
               ArrayList<String> stringList = new ArrayList<>();

               stringList.add(""); // 空行
               teams.forEach(t -> stringList.add(" " + t.getTeamDisplayName() + " " + (getInfo(t).getAlive() ? "#GREEN#✔" : "#RED#✖")));
               stringList.add(""); // 空行
               // 队友血量显示
               stringList.add(String.format(" 队友血量：|#RED#%s#RESET#|", PlayerUtils.getPlayerHealthString(team.getTheOtherPlayer(player))));
               // 击杀数显示
               stringList.add(String.format(" 击杀数：%s", getInfo(team).getKillCount()));
               // FastBoard计分板创建
               FastBoard board = boards.get(team).get(player);
               board.updateTitle(title);
               board.updateLines(stringList.stream().map(ChatUtils::translateColor).collect(Collectors.toList()));
           });
        });
    }

    public void newBoard(GameTeam team) {
        HashMap<Player, FastBoard> playerBoards = new HashMap<>();
        team.players.keySet().forEach(player -> playerBoards.put(player, new FastBoard(player)));
        boards.put(team, playerBoards);
        info.put(team, new TeamInfo(team));
    }

    public void reset() {
        boards.values().forEach(map -> map.values().forEach(FastBoard::delete));
        boards.clear();
    }
}
