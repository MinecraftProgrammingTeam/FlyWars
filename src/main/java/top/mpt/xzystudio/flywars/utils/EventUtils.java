package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import top.mpt.xzystudio.flywars.Main;

public class EventUtils {
    public static void callEvent(Event event) {
        Bukkit.getScheduler().runTask(Main.instance, () -> Main.instance.getServer().getPluginManager().callEvent(event));
    }
}
