package top.mpt.xzystudio.flywars.scheduler;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.GameUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刷新资源类
 */
public class ResourcesUpdater extends BukkitRunnable {
    // The world where will play FlyWars.
    private static World gameWorld;

    /**
     * 设置进行游戏的世界
     * @param gameWorld 进行游戏的世界
     */
    public void setGameWorld(World gameWorld) {
        ResourcesUpdater.gameWorld = gameWorld;
    }

    @Override
    public void run() {
        ConfigUtils.getMapListConfig("resources").forEach(its -> {
            int x = (Integer) its.get("x");
            int y = (Integer) its.get("y");
            int z = (Integer) its.get("z");
            int amount = (Integer) its.get("amount");
            String material = (String) its.get("material");
            material = material.toUpperCase();
            String name = (String) its.get("name");

            Location loc = new Location(gameWorld, x, y, z);
            ItemStack itemStack = GameUtils.newItem(Material.valueOf(material), name, amount);
            gameWorld.dropItem(loc, itemStack);
        });
    }
}
