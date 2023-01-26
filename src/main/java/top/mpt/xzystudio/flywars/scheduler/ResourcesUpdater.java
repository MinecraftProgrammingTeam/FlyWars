package top.mpt.xzystudio.flywars.scheduler;

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
    // resource type
    private final Map<String, Material> resType = new HashMap<String, Material>(){{
        put("firework-rocket", Material.FIREWORK_ROCKET);
        put("arrow", Material.ARROW);
        put("golden_apple", Material.GOLDEN_APPLE);
    }};

    // 显示名称
    private final Map<String, String> displayName = new HashMap<String, String>(){{
        put("firework-rocket", "#RED#核弹");
        put("arrow", "#WHITE#子弹");
        put("golden_apple", "#GOLD#天赐金苹果");
    }};

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
        resType.forEach((key, value) -> {
            List<Map<?, ?>> res = ConfigUtils.getMapListConfig("resources." + key);
            res.forEach(its -> {
                int x = (Integer) its.get("x");
                int y = (Integer) its.get("y");
                int z = (Integer) its.get("z");
                int amount = (Integer) its.get("amount");

                Location loc = new Location(gameWorld, x, y, z);
                ItemStack itemStack = GameUtils.newItem(value, displayName.getOrDefault(key, key), amount);
                gameWorld.dropItem(loc, itemStack);
            });
        });
    }
}
