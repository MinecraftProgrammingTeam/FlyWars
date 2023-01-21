package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;

@ArrowInfo(name = "#DARK_RED#爆炸箭")
public class ExplosiveArrow implements ArrowEntry {
    private static final int size = 5;
    private static final boolean setFire = false;
    private static final boolean breakBlock = false;

    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        Location loc = entity.getLocation();
        World world = loc.getWorld();
        if (world != null) {
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            world.createExplosion(x, y, z, size, setFire, breakBlock);
        }
    }
}
