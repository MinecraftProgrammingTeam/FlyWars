package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;

// TODO 光灵箭不是默认就可以标记玩家吗？
@ArrowInfo(name = "#YELLOW#标记箭", path="flag")
public class FlagArrow extends ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow) {

    }
}
