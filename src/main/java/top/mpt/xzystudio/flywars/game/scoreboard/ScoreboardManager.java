package top.mpt.xzystudio.flywars.game.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * 计分板类
 */
public class ScoreboardManager {
    // 向玩家展示的计分板的标题
    private static final String title = "#BLUE#FlyWars #GREEN#飞行战争";
    private static final HashMap<GameTeam, HashMap<Player, FastBoard>> boards = new HashMap<>();
    private static final HashMap<GameTeam, TeamInfo> info = new HashMap<>(); // information复数不加s qwq

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

    private static TeamInfo getInfo(GameTeam team) {
        return info.get(team);
    }

    public static void renderScoreboard() {
        ArrayList<String> stringList = new ArrayList<>();
        ArrayList<GameTeam> teams = Game.teams;
        stringList.add("");
        teams.forEach(team ->
                stringList.add(team.getTeamDisplayName() + " " + (getInfo(team).getAlive() ? "#GREEN#✔" : "#RED#✖")) // 队伍名和存活情况 - 先不用
        );
        stringList.add("");

        boards.keySet().forEach(team -> {
            HashMap<Player, FastBoard> playerBoards = boards.get(team);
            playerBoards.keySet().forEach(player -> {
                FastBoard board = playerBoards.get(player);
                ArrayList<String> stringListCopy = stringList;
                stringListCopy.add(String.format("队友血量：|%s|", PlayerUtils.getPlayerHealthString(team.getTheOtherPlayer(player))));
                board.updateLines(stringListCopy);
                board.updateTitle(title);
            });
        });
    }

    public static void newBoard(GameTeam team) {
        HashMap<Player, FastBoard> playerBoards = new HashMap<>();
        team.players.keySet().forEach(player -> playerBoards.put(player, new FastBoard(player)));
        boards.put(team, playerBoards);
    }

    public static void deleteBoard(GameTeam team) {
        HashMap<Player, FastBoard> playerBoards = boards.get(team);
        playerBoards.keySet().forEach(player -> playerBoards.get(player).delete());
        boards.remove(team);
    }
}
