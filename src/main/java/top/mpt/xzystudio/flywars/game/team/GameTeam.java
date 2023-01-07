package top.mpt.xzystudio.flywars.game.team;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.utils.ChatUtils;

import java.util.HashMap;

/**
 * Team类
 */
public class GameTeam {
    /**
     * 一个Team里存放两名玩家和类型的数组
     */
    public final HashMap<Player, TeammateType> players = new HashMap<>();
    // 玩家1
    private final Player p1;
    // 玩家2
    private final Player p2;
    // Team类
    private final Team team;
    // 颜色代码
    private final String color;
    // 颜色代码对应中文
    private final String colorName;
    // 计分板
    public static ScoreboardManager board;

    /**
     * 将两名玩家组进一个Team里
     * @param p1 玩家1
     * @param p2 玩家2
     * @param color 显示的颜色(给程序看的颜色代码)
     * @param colorName 队伍的显示颜色(给玩家看的颜色名字[如青队的青])
     */
    public GameTeam(Player p1, Player p2, Team team, String color, String colorName) {
        this.players.put(p1, TeammateType.P1);
        this.players.put(p2, TeammateType.P2);
        this.p1 = p1;
        this.p2 = p2;
        this.team = team;
        this.color = color;
        this.colorName = colorName;
    }

    /**
     * 给team设置计分板
     * @param board 计分板
     */
    public void setBoard(ScoreboardManager board){
        this.board = board;
    }

    public void unregTeam(){
        this.team.unregister();
    }

    /**
     * 获取此Team的计分板
     * @return 此Team计分板
     */
    public ScoreboardManager getBoard() {
        return board;
    }

    public Team getTeam(){
        return this.team;
    }

    /**
     * 获取Team里玩家1的变量
     * @return 玩家1
     */
    public Player getP1() {
        return this.p1;
    }

    /**
     * 获取Team里玩家2的变量
     * @return 玩家2
     */
    public Player getP2() {
        return this.p2;
    }

    /**
     * 获取两名玩家是否为队友
     * @param p1 玩家1
     * @param p2 玩家2
     * @return 是否为队友
     */
    public boolean isTeammate(Player p1, Player p2) {
        return (this.p1 == p1 || this.p2 == p1) && (this.p2 == p2 || this.p2 == p1);
    }

    /**
     * 通过Team里一个玩家获取此Team里的另一个玩家
     * @param p 一个玩家
     * @return 另一个玩家
     */
    public Player getTheOtherPlayer(Player p){
        return p == p1 ? p2 : p1;
    }

    /**
     * 获取Team的显示名称
     * @return Team的显示名称
     */
    public String getTeamDisplayName() {
        return ChatUtils.translateColor("#%s#[%s队]#RESET#", this.color, this.colorName);
    }

    /**
     * 让p2骑在p1头上
     */
    public void ride(){
        p1.addPassenger(p2);
    }

    /**
     * 检查玩家是否在某个Team里
     * @param p 玩家
     */
    public boolean isPlayerInTeam(Player p){
        // 如果p1 p2都不为null
        if (p1 != null && p2 != null){
           // 如果任意一位玩家为p
            return p1 == p || p2 == p;
        }
        return false;
    }

     public boolean isP1(Player p){
        return p == p1;
     }
}
