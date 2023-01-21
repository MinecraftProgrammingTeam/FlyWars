package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;

/**
 * 特殊箭矢：爆炸箭
 */
@ArrowInfo(name = "爆炸箭")
public class ExplosionArrow implements ArrowEntry {
    @Override
    public void run(Player pShoot, Player p, Arrow arrow){

    }
}
