package top.mpt.xzystudio.flywars.game.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 计分板类
 */
public class ScoreboardManager {
    // 因为要给不同玩♂家展示不同的计分板，所以建立一个HashMap存放计分板
    private final HashMap<String, FastBoard> boards = new HashMap<>();

    // 建立HashMap，给每个团队都设置一个TeamInfo
    private final HashMap<GameTeam, TeamInfo> teamInfos = new HashMap<>();

    // 向玩家展示的计分板的标题
    private final String title = "#BLUE#FlyWars #GREEN#飞行战争";
    private final GameTeam team;

    /**
     * 计分板管理器
     * @param team 队伍
     */
    public ScoreboardManager(GameTeam team) {
        this.team = team;
        this.boards.put("p1", new FastBoard(team.getP1()));
        this.boards.put("p2", new FastBoard(team.getP2()));
        updateTitle(this.title);
    }

    /**
     * 更新计分板标题
     * @param title 标题
     */
    public void updateTitle(String title){
        this.boards.get("p1").updateTitle(ChatUtils.translateColor(title));
        this.boards.get("p2").updateTitle(ChatUtils.translateColor(title));
    }

    /**
     * 更新整个计分板的内容
     * @param args 内容
     */
    public void updateLines(String... args){
        this.boards.get("p1").updateLines(args);
        this.boards.get("p2").updateLines(args);
    }

    /**
     * 更新计分板单行内容
     * @param line 要更新的行
     * @param text 要更新成的文本
     */
    public void updateLine(int line, String text){
        this.boards.get("p1").updateLine(line, ChatUtils.translateColor(text));
        this.boards.get("p2").updateLine(line, ChatUtils.translateColor(text));
    }

    /**
     * 删除计分板
     */
    public void deleteBoard(){
        this.boards.clear();
    }

    public FastBoard getP1Board(){
        Main.instance.getLogger().info(ChatColor.GREEN + "azzzz:"+this.boards.toString());
        return this.boards.get("p1");
    }

    public FastBoard getP2Board(){
        return this.boards.get("p2");
    }
}
