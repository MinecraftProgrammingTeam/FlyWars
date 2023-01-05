package top.mpt.xzystudio.flywars.game;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Team {
    /**
     * 一个Team里存放两名玩家和类型的数组
     */
    public final HashMap<Player, TeammateType> players = new HashMap<>();
    // 玩家1
    private final Player p1;
    // 玩家2
    private final Player p2;

    /**
     * 将两名玩家组进一个Team里
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public Team(Player p1, Player p2) {
        this.players.put(p1, TeammateType.P1);
        this.players.put(p2, TeammateType.P2);
        this.p1 = p1;
        this.p2 = p2;
        p1.addPassenger(p2);
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
}
