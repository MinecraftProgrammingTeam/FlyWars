package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.listeners.PlayerEventListener;
import top.mpt.xzystudio.flywars.utils.GameUtils;

@ArrowInfo(name = "#AQUA#寒冰箭", path = "slow")
public class SlowArrow extends ArrowEntry {
    public static final int limit = get(SlowArrow.class, "limit", 1);

    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        GameUtils.getTeamByPlayer(entity, team -> {
            Player p1 = team.getP1();
            PlayerEventListener.limitedSpeedPlayers.add(p1);
        });
    }
}
