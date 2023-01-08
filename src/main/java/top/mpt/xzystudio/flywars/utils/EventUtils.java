package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import top.mpt.xzystudio.flywars.Main;

/**
 * 事件工具类
 */
public class EventUtils {
    /**
     * 调用事件
     * @param event 事件
     */
    public static void callEvent(Event event) {
        Bukkit.getScheduler().runTask(Main.instance, () -> Main.instance.getServer().getPluginManager().callEvent(event));
    }
}