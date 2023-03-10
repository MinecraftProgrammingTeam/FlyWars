package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;

@ArrowInfo(name = "#DARK_RED#爆炸箭", path = "explosive")
public class ExplosiveArrow extends ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        Location loc = entity.getLocation();
        World world = loc.getWorld();
        if (world != null) {
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            world.createExplosion(x, y, z, get("size", 5), get("setFire", false), get("breakBlock", false));
        }
    }
}
