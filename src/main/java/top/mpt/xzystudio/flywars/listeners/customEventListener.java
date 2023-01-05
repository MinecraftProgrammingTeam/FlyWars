package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.game.Team;
import org.bukkit.event.entity.*;

public class customEventListener implements Listener {
    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (damager.getType() == EntityType.PLAYER && entity.getType() == EntityType.PLAYER) {
            for (Team team : Main.playerData) {
                if (team.isTeammate((Player) damager, (Player) entity)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        if (Main.teamMap.containsKey(p.getName())) {
            int iter = Main.teamMap.get(p.getName());
            Team team = Main.playerData.get(iter);
            Player op = team.getTheOtherPlayer(p);
            op.setGameMode(GameMode.SPECTATOR);
            p.setGameMode(GameMode.SPECTATOR);
//            op.setHealth(0);
            op.sendTitle(ChatColor.RED + "Game Over", "您的队友"+p.getName()+"寄了");
            Main.instance.getServer().broadcastMessage("[FlyWars] " + ChatColor.BLUE + "<" + p.getName() + ">和<" + op.getName() + ">" + ChatColor.WHITE + "阵亡了！");
        }
    }
}
