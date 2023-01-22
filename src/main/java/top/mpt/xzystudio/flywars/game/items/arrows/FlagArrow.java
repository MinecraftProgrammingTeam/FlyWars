package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.utils.GameUtils;

@ArrowInfo(name = "#YELLOW#标记箭", path = "flag")
public class FlagArrow extends ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        GameUtils.getTeamByPlayer(entity, team -> team.players.keySet().forEach(p ->
                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, get("duration", 10), 1))
        ));
    }
}
