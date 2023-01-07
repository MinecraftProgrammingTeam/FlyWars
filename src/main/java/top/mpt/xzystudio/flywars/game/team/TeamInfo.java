package top.mpt.xzystudio.flywars.game.team;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * 队伍信息查询类
 */
public class TeamInfo {
    // 要查询信息的Team
    private GameTeam team;
    // Team杀敌数
    private Integer killCount = 0;
    // 每个队员的血量
    private final HashMap<UUID, Integer> mateHealth = new HashMap<>();
    // 是否存活
    private Boolean alive = true;

    /**
     * TeamInfo初始化
     */
    public TeamInfo(GameTeam team) {
        this.team = team;
    }

    /**
     * 获取队伍杀敌数
     * @return 杀敌数
     */
    public Integer getKillCount() {
        return this.killCount;
    }

    /**
     * 获取队员的血量
     */
    public HashMap<UUID, Integer> getMateHealth() {
        return this.mateHealth;
    }

    /**
     * 设置队伍杀敌数
     * @param value 杀敌数
     */
    public void setKillCount(Integer value) {
        this.killCount = value;
    }

    /**
     * 设置团队里某个玩家的血量
     * @param player 玩家
     * @param health 血量
     */
    public void setHealth(Player player, Integer health) {
        UUID uuid = player.getUniqueId();
        this.mateHealth.replace(uuid, health);
    }

    public Boolean getAlive() {
        return this.alive;
    }

    public void setAlive(Boolean value) {
        this.alive = value;
    }
}
