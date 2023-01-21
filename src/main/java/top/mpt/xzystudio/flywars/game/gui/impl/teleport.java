package top.mpt.xzystudio.flywars.game.gui.impl;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.gui.GuiProcess;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.concurrent.atomic.AtomicReference;

public class teleport extends GuiProcess {
    @Override
    public void run(Player pShoot, Player p, Arrow arrow){
        // 获取到P1（被骑乘者）
        AtomicReference<Player> p1 = new AtomicReference<>();
        Game.teams.forEach(it -> {
            if (it.isPlayerInTeam(p)){
                if (it.isP1(p)) p1.set(p);
                else p1.set(p);
            }
        });
        // 传送被骑乘者到射击者那里 - 如果直接传送p会造成p从p1身上掉下来
        p1.get().teleport(pShoot.getLocation());

        PlayerUtils.send(p, "[FlyWars] #YELLOW#您已被%s的末影箭射中，传送到对方位置");
    }
}
