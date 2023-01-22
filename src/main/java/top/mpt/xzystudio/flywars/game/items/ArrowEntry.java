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

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<? extends ArrowEntry> clazz, String string, T defaultValue) {
        ArrowInfo info = ClassUtils.getAnnotation(clazz, ArrowInfo.class);
        if (info == null) return defaultValue;
        String path = info.path();
        Object o = ConfigUtils.getConfig(String.format("arrow.%s.%s", path, string));
        if (o == null) o = ConfigUtils.getConfig(String.format("arrow.default.%s", string), defaultValue);
        return (T) o;
    }

    public <T> T get(String string, T defaultValue) {
        return get(this.getClass(), string, defaultValue);
    }

    public int getAmount(){
        return get("amount", 1);
    }
    public int getCost(){
        return get("cost", 1);
    }
}