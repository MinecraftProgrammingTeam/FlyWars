package top.mpt.xzystudio.flywars.game.items;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;

public abstract class ArrowEntry {
    public String path;

    public void ArrowEntry(String path){
        this.path = path;
    }

    /**
     * 开始处理特殊各种箭
     * @param shooter 射击的玩家
     * @param entity 被射中的玩家
     * @param arrow 实体箭
     */
    public abstract void run(Player shooter, Player entity, Arrow arrow);

    public Object get(String string){
        return ConfigUtils.getConfig(String.format("arrow.%s.%s", this.path, string));
    }

    public Object get(String string, Object defaultStr){
        return ConfigUtils.getConfig(String.format("arrow.%s.%s", this.path, string), defaultStr);
    }
}