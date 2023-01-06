package top.mpt.xzystudio.flywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import top.mpt.xzystudio.flywars.game.team.GameTeam;

/**
 * 自定义事件(队伍淘汰事件)
 */
public class TeamEliminatedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    // 死亡的玩家
    private final Player player;
    // 被淘汰的队伍
    private final GameTeam team;
    // 击杀该队伍的队伍
    private final GameTeam killer;

    /**
     * 自定义事件:队伍被淘汰事件
     * @param player 死亡的玩家
     * @param team 被淘汰的队伍
     * @param killer 淘汰该队伍的玩家
     */
    public TeamEliminatedEvent(Player player, GameTeam team, GameTeam killer) {
        this.player = player;
        this.team = team;
        this.killer = killer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * 获取死亡的玩家
     * @return 死亡的玩家
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * 获取被淘汰的队伍
     * @return 被淘汰的队伍
     */
    public GameTeam getTeam() {
        return this.team;
    }

    /**
     * 获取淘汰该队伍的玩家
     * @return玩家的队伍该淘汰
     */
    public GameTeam getKiller() {
        return this.killer;
    }
}
