package top.mpt.xzystudio.flywars.game.items.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.utils.GameUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

@ArrowInfo(name = "#LIGHT_PURPLE#末影箭", path = "teleport")
public class TeleportArrow extends ArrowEntry {
    @Override
    public void run(Player shooter, Player entity, Arrow arrow){
        // 获取到P1（被骑乘者）
        GameUtils.getTeam(entity, team -> {
            Player p1 = team.getP1();
            // 传送被骑乘者到射击者那里 - 如果直接传送entity会造成entity从p1身上掉下来
            p1.teleport(shooter.getLocation());
            PlayerUtils.send(entity, "[FlyWars] #YELLOW#您已被%s的末影箭射中，传送到对方位置");
        });
    }
}
