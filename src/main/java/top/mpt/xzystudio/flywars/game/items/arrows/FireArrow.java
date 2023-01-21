package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.utils.GameUtils;

@ArrowInfo(name = "#RED#火焰箭")
public class FireArrow implements ArrowEntry {
    private static final int ticks = 50;

    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        GameUtils.getTeam(entity, t -> t.players.keySet().forEach(player -> player.setFireTicks(ticks)));
    }
}
