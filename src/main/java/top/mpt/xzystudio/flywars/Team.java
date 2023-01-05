package top.mpt.xzystudio.flywars;

import org.bukkit.entity.Player;

public class Team {
    private final Player p1;
    private final Player p2;

    public Team(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        p1.setPassenger(p2);
    }

    public Player getP1() {
        return this.p1;
    }

    public Player getP2() {
        return this.p2;
    }
}
