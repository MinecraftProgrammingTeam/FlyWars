package top.mpt.xzystudio.flywars.scheduler;

import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PigSche extends BukkitRunnable{
    Pig pigEntity;
    Player player;

    public void setPigEntity(Pig pig) {
        pigEntity = pig;
    }

    public void setPlayer(Player pla) {
        player = pla;
    }

    @Override
    public void run() {
        pigEntity.teleport(player.getLocation());
    }
}
