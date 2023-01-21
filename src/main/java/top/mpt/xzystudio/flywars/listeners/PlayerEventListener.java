package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.gui.GuiManager;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.EventUtils;
import top.mpt.xzystudio.flywars.utils.LoggerUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * 玩家相关事件监听器
 */
public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // 当玩家退出游戏时
        // 拿到玩家
        Player p = event.getPlayer();
        // 遍历team
        Game.teams.forEach(it -> {
            // 如果某个team里有这个退出游戏的玩家
            if (it.isPlayerInTeam(p)){
                Player op = it.getTheOtherPlayer(p);
                PlayerUtils.send(op, "[FlyWars] #RED#你的队友退出了游戏！");
                TeamEliminatedEvent eliminatedEvent = new TeamEliminatedEvent(p, it, null);
                EventUtils.callEvent(eliminatedEvent);
            }
        });
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // 当玩家被其他玩家打的时候
        if (event.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) event.getEntity();
            Game.teams.forEach(it -> {
                // 如果玩家在teams里
                if (it.isPlayerInTeam(p)){
                    // 渲染scoreboard
                    Game.scoreboardManager.renderScoreboard();
                }
            });
        }

        if (event.getDamager().getType() == EntityType.SPECTRAL_ARROW){
            event.setCancelled(true);
            Player pShoot = Bukkit.getPlayer(Objects.requireNonNull(event.getDamager().getCustomName()));
            if (pShoot == null){
                LoggerUtils.warning("#RED#没有找到van家");
                return;
            }
            Player p = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getDamager();

            GuiManager.items.forEach(it -> {
                if (Objects.equals(it.name, arrow.getName())){
                    it.process.run(pShoot, p, arrow);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // TODO 玩家复活之后，p2仍会骑在p1头顶（不管team有没有被移除）

        // 当玩家嗝屁时
        Player p = event.getEntity();
        // 杀了该玩家的实体
        LivingEntity eKiller = event.getEntity().getKiller();
        // 如果杀死玩家的实体不为null，且服务器里能找到这个玩家
        if (eKiller != null && Bukkit.getPlayer(eKiller.getName()) != null) {
            // 杀了该玩家的玩家
            Player pKiller = Bukkit.getPlayer(eKiller.getName());
            // 定义被淘汰的team和淘汰该team的玩家的变量
            GameTeam team = null;
            GameTeam killer = null;
            // 遍历team寻找被淘汰team（通过死亡的玩家）和淘汰该team的玩家
            for (GameTeam t : Game.teams) {
                if (t.players.containsKey(p)) team = t;
                if (t.players.containsKey(pKiller)) killer = t;
            }
            // 防止NullPointerException
            if (team != null && killer != null) {
                // 使用自定义事件
                TeamEliminatedEvent eliminatedEvent = new TeamEliminatedEvent(p, team, killer);
                EventUtils.callEvent(eliminatedEvent);
            } else {
                LoggerUtils.warning("#RED#找不到被淘汰的Team或淘汰Team的玩家");
            }
        } else {
            LoggerUtils.info("#RED#未获取到击杀者！");

            GameTeam team = null;
            for (GameTeam t : Game.teams) {
                if (t.players.containsKey(p)) team = t;
            }
            if (team != null){
                TeamEliminatedEvent eliminatedEvent = new TeamEliminatedEvent(p, team, null);
                EventUtils.callEvent(eliminatedEvent);
            }
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        // TODO 与eject冲突，莫名其妙
        // 玩家从另一个玩家的身上下来的时候
        // 假设灰灰骑在pal头顶
        // 离开骑乘实体的实体  这是灰灰
        Entity passenger = event.getEntity();
        // 被骑乘的实体       这是pal
        Entity vehicle = event.getDismounted();
        // 如果离开被骑乘实体的实体是玩家，且被骑乘实体也是玩家
        if (passenger.getType() == EntityType.PLAYER && vehicle.getType() == EntityType.PLAYER) {
            // 遍历team数组
            Game.teams.forEach(it -> {
                // 如果被骑乘实体和离开骑乘实体的玩家是队友关系，就取消玩家的行为
                if (it.isP2((Player) passenger) && it.isP1((Player) vehicle) && Game.scoreboardManager.getInfo(it).getAlive()) {
                    LoggerUtils.warning("#RED#玩家下车\ngetEntity:%s\ngetDismounted:%s", event.getEntity().getName(), event.getDismounted().getName()); // TODO remove debug output
                    try {
                        vehicle.eject();
                        passenger.eject();
                        vehicle.addPassenger(passenger);
                    } catch (Exception e) {
//                        LoggerUtils.warning("#RED#又是奇奇怪怪的bug，罢了罢了"));
                    }
                }
            });
        }
    }

    // 当玩家设出来的时候(?
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event){
        Game.teams.forEach(it -> {
            if (it.isPlayerInTeam((Player) event.getProjectile())) {
                event.getEntity().setCustomName(event.getProjectile().getName());
            }
        });
    }
}
