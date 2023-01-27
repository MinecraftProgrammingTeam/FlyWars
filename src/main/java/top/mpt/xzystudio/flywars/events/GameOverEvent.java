package top.mpt.xzystudio.flywars.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import top.mpt.xzystudio.flywars.game.team.GameTeam;

/**
 * 游戏结束事件处理
 */
public class GameOverEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final GameTeam winner;
    private final String winnerDisplayName;

    /**
     * 自定义事件:游戏结束事件
     * @param winner 胜利者
     */
    public GameOverEvent(GameTeam winner, String winnerDisplayName) {
        this.winner = winner;
        this.winnerDisplayName = winnerDisplayName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * 获取获胜队
     * @return GameTeam winner
     */
    public GameTeam getWinner() {
        return this.winner;
    }

    public String getWinnerDisplayName() {
        return winnerDisplayName;
    }
}
