package top.mpt.xzystudio.flywars.game.items;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public interface ArrowEntry {
    /**
     * 开始处理特殊各种箭
     * @param shooter 射击的玩家
     * @param entity 被射中的玩家
     * @param arrow 实体箭
     */
    void run(Player shooter, Player entity, Arrow arrow);
}