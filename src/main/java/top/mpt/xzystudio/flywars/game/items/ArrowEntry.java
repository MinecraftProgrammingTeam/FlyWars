package top.mpt.xzystudio.flywars.game.items;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.utils.ClassUtils;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;

public abstract class ArrowEntry {
    /**
     * 开始处理特殊各种箭
     * @param shooter 射击的玩家
     * @param entity 被射中的玩家
     * @param arrow 实体箭
     */
    public abstract void run(Player shooter, Player entity, Arrow arrow);

    public Object get(String string, Object... defaultValue){
        ArrowInfo info = ClassUtils.getAnnotation(this.getClass(), ArrowInfo.class);
        if (info == null){
            if (defaultValue.length == 0) return null;
            else return defaultValue;
        }
        String path = info.path();
        Object ob = ConfigUtils.getConfig(String.format("arrow.%s.%s", path, string));
        if (ob == null) {
            ob = ConfigUtils.getConfig("arrow.default." + string, defaultValue);
        }
        return ob;
    }

    public int getAmount(){
        return (Integer) get("amount", 1);
    }
    public int getCost(){
        return (Integer) get("cost", 1);
    }
}