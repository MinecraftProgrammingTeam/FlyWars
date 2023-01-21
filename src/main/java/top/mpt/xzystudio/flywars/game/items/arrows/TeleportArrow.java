package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.concurrent.atomic.AtomicReference;

@ArrowInfo(name = "#LIGHT_PURPLE#末影箭")
public class TeleportArrow implements ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow){
        // 获取到P1（被骑乘者）
        AtomicReference<Player> p1 = new AtomicReference<>();
        Game.teams.forEach(it -> {
            if (it.isPlayerInTeam(entity)){
                if (it.isP1(entity)) p1.set(entity);
                else p1.set(entity);
            }
        });
        // 传送被骑乘者到射击者那里 - 如果直接传送entity会造成entity从p1身上掉下来
        p1.get().teleport(shooter.getLocation());

        PlayerUtils.send(entity, "[FlyWars] #YELLOW#您已被%s的末影箭射中，传送到对方位置");
    }
}
