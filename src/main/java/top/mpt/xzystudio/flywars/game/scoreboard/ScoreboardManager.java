package top.mpt.xzystudio.flywars.game.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

/**
 * 计分板类
 */
public class ScoreboardManager {
    // ???
    private final ArrayList<Scoreboard> azzz = new ArrayList<>();

    private final Team team;
    
    // https://bukkit.windit.net/javadoc/org/bukkit/scoreboard/Team.html

    /**
     * 计分板管理器
     */
    public ScoreboardManager(Team team) {
        this.team = team;
    }

    /**
     * 初始化计分板
     */
    public void initialScoreboards() {

    }
}
