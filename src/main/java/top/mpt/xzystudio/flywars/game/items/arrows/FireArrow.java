package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.utils.GameUtils;

@ArrowInfo(name = "#RED#火焰箭", path = "fire")
public class FireArrow extends ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        GameUtils.getTeamByPlayer(entity, t -> t.players.keySet().forEach(player -> player.setFireTicks(get("ticks", 50))));
    }
}
