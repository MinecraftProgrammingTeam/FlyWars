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
    private final Player p2;
    private final int iter;

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

    public Player getP1() {
        return this.p1;
    }

    public Player getP2() {
        return this.p2;
    }

    public boolean isTeammate(Player p1, Player p2) {
        return (this.p1 == p1 || this.p2 == p1) && (this.p2 == p2 || this.p2 == p1);
    }

    public Player getTheOtherPlayer(Player p){
        return p == p1 ? p2 : p1;
    }
}
