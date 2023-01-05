package top.mpt.xzystudio.flywars.game;

import org.bukkit.entity.Player;

public class Team {
    private final Player p1;
    private final Player p2;
    private final int iter;

    public Team(Player p1, Player p2, int iter) {
        this.p1 = p1;
        this.p2 = p2;
        this.iter = iter;
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
        if (p == p1) return p2;
        else return p1;
    }
}
