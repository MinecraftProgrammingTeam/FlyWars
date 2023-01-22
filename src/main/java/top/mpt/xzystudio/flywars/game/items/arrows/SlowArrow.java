package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.utils.GameUtils;

// TODO 在空中飞行怎么缓慢？
@ArrowInfo(name = "#AQUA#寒冰箭")
public class SlowArrow extends ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {
        GameUtils.getTeam(entity, t -> t.players.keySet().forEach(player -> player.addPotionEffect(
                new PotionEffect(PotionEffectType.SLOW, (Integer) get("duration"), (Integer) get("amplifier"))
        )));
    }
}
