package top.mpt.xzystudio.flywars.scheduler;

import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 处理pig（负责玩家骑乘玩家）
 * @author xzyStudio
 */
public class PigSche extends BukkitRunnable{
    Pig pigEntity;
    Player player;

    /**
     * 设置pig实体
     * @param pig pig实体
     */
    public void setPigEntity(Pig pig) {
        pigEntity = pig;
    }

    /**
     * 设置玩家实体
     * @param pla 实体玩家
     */
    public void setPlayer(Player pla) {
        player = pla;
    }

    @Override
    public void run() {
        pigEntity.teleport(player.getLocation());
    }
}
