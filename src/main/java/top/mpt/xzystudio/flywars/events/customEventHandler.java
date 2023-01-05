package top.mpt.xzystudio.flywars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.Plugin;
import top.mpt.xzystudio.flywars.Main;

public class customEventHandler implements Listener {
    Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void EntityDeath(PlayerDeathEvent event) {

    }
}
