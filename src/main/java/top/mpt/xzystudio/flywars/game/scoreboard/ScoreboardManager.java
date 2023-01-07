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
    private final String title = "#BLUE#FlyWars #GREEN#飞行战争";
    private final GameTeam team;
    private final FastBoard p1Board, p2Board;

    /**
     * 计分板管理器
     * @param team 队伍
     */
    public ScoreboardManager(GameTeam team) {
        this.team = team;
        this.p1Board = new FastBoard(team.getP1());
        this.p2Board = new FastBoard(team.getP2());
        updateTitle(this.title);
    }

    /**
     * 更新计分板标题
     * @param title 标题
     */
    public void updateTitle(String title){
        this.p1Board.updateTitle(ChatUtils.translateColor(title));
        this.p2Board.updateTitle(ChatUtils.translateColor(title));
    }

    /**
     * 更新整个计分板的内容
     * @param args 内容
     */
    public void updateLines(String... args){
        this.p1Board.updateLines(args);
        this.p2Board.updateLines(args);
    }

    /**
     * 更新计分板单行内容
     * @param line 要更新的行
     * @param text 要更新成的文本
     */
    public void updateLine(int line, String text){
        this.p1Board.updateLine(line, ChatUtils.translateColor(text));
        this.p2Board.updateLine(line, ChatUtils.translateColor(text));
    }

    /**
     * 删除计分板
     */
    public void deleteBoard(){
        this.getP1Board().delete();
        this.getP2Board().delete();
    }

    public FastBoard getP1Board() { return this.p1Board; }

    public FastBoard getP2Board(){
        return this.p2Board;
    }
}
