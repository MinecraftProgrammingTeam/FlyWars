package top.mpt.xzystudio.flywars.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import top.mpt.xzystudio.flywars.game.team.GameTeam;

public class GameOverEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final GameTeam winner;

    /**
     * 自定义事件:游戏结束事件
     * @param winner 胜利者
     */
    public GameOverEvent(GameTeam winner) {
        this.winner = winner;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public GameTeam getWinner() {
        return this.winner;
    }
}
