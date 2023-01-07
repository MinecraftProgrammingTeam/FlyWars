package top.mpt.xzystudio.flywars.game.team;

/**
 * 队伍信息查询类
 */
public class TeamInfo {
    // 要查询信息的Team
    private GameTeam team;
    // Team杀敌数
    private Integer killCount = 0;
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
     * 增加队伍杀敌数
     */
    public void addKillCount() {
        this.killCount++;
    }

    public Boolean getAlive() {
        return this.alive;
    }

    public void setAlive(Boolean value) {
        this.alive = value;
    }
}
