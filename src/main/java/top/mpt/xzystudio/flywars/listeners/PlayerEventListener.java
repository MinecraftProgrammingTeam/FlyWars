package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.gui.GuiItem;
import top.mpt.xzystudio.flywars.game.gui.GuiManager;
import top.mpt.xzystudio.flywars.game.items.arrows.SlowArrow;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.scheduler.PickUpTimer;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.GameUtils;
import top.mpt.xzystudio.flywars.utils.LoggerUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 玩家相关事件监听器
 */
public class PlayerEventListener implements Listener {
    public static ArrayList<Player> limitedSpeedPlayers = new ArrayList<>();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // 当玩家退出游戏时
        // 拿到玩家
        Player p = event.getPlayer();
        GameUtils.getTeamByPlayer(p, team -> {
            Player op = team.getTheOtherPlayer(p);
            PlayerUtils.send(op, "[FlyWars] #RED#你的队友退出了游戏！");
            TeamEliminatedEvent eliminatedEvent = new TeamEliminatedEvent(p, team, null);
            GameUtils.callEvent(eliminatedEvent);
        });
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // 当玩家被其他玩家打的时候
        if (event.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) event.getEntity();
            GameUtils.getTeamByPlayer(p, t -> Game.scoreboardManager.renderScoreboard());

            // 判断造成伤害的实体
            switch (event.getDamager().getType()) {
                case SPECTRAL_ARROW:  // 如果是光灵箭（道具箭）
                    event.setCancelled(true);
                    Player pShoot = Bukkit.getPlayer(Objects.requireNonNull(event.getDamager().getCustomName()));
                    if (pShoot == null){
                        LoggerUtils.warning("#RED#没有找到van家");
                        return;
                    }
                    Arrow arrow = (Arrow) event.getDamager();

                    GuiItem item = GameUtils.find(GuiManager.items, i -> Objects.equals(i.name, arrow.getName()));
                    if (item != null) item.process.run(pShoot, p, arrow);

                    break;
                case ARROW:  // 如果是普通箭（掉落机制`https://www.mcbbs.net/forum.php?mod=viewthread&tid=1417390&page=1&authorid=1916362`）
                    GameUtils.getTeamByPlayer(p,t -> {
                        if (t.isP2(p)){  // 射击到了骑在背上的人才会掉落
                            Player op = t.getP1();
                            op.removePassenger(p);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 2));
                            // 定义pickUpTime变量(单位：tick)
                            int pickUpTime = (Integer) ConfigUtils.getConfig("pick-up-time", 600);
                            // 开Bukkit.Runnable
                            PickUpTimer pickUpTimer = new PickUpTimer();
                            pickUpTimer.setTeam(t);
                            pickUpTimer.runTaskLater(Main.instance, pickUpTime);
                            // 显示title (除以20是因为默认TPS为20，可以除以这个数把Ticks转换成second)
                            PlayerUtils.showTitle(op, "#YELLOW#您的队友已从背上掉落", "#AQUA#请及时背起队友，" + (pickUpTime / 20) + "秒后将会受到伤害");
                        }
                    });
                    break;
            }
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
            for (GameTeam t: Game.teams) {
                if (t.players.containsKey(p)) team = t;
                if (t.players.containsKey(pKiller)) killer = t;
            }
            // 防止NullPointerException
            if (team != null && killer != null) {
                // 使用自定义事件
                TeamEliminatedEvent eliminatedEvent = new TeamEliminatedEvent(p, team, killer);
                GameUtils.callEvent(eliminatedEvent);
            } else {
                LoggerUtils.warning("#RED#找不到被淘汰的Team或淘汰Team的玩家");
            }
        } else {
            LoggerUtils.info("#RED#未获取到击杀者！");
            GameUtils.getTeamByPlayer(p, team -> {
                TeamEliminatedEvent eliminatedEvent = new TeamEliminatedEvent(p, team, null);
                GameUtils.callEvent(eliminatedEvent);
            });
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        // 玩家从另一个玩家的身上下来的时候
        // 假设灰灰骑在pal头顶
        // 离开骑乘实体的实体  这是灰灰
        Entity passenger = event.getEntity();
        // 被骑乘的实体       这是pal
        Entity vehicle = event.getDismounted();
        // 如果离开被骑乘实体的实体是玩家，且被骑乘实体也是玩家
        if (passenger.getType() == EntityType.PLAYER && vehicle.getType() == EntityType.PLAYER) {
            // 找到符合条件的队伍
            GameTeam team = GameUtils.getTeamBy(t -> t.isP2((Player) passenger) && t.isP1((Player) vehicle) && Game.scoreboardManager.getInfo(t).getAlive());
            if (team != null) {
                // 获取p1   这是pal
                Player op = team.getP1();
                // p2从p1头上下来
                op.removePassenger(team.getP2());
                // 给p2发光 level 2
                team.getP2().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 2));
                // 定义pickUpTime变量(单位：tick)
                int pickUpTime = (Integer) ConfigUtils.getConfig("pick-up-time", 600);
                // 开Bukkit.Runnable
                PickUpTimer pickUpTimer = new PickUpTimer();
                pickUpTimer.setTeam(team);
                pickUpTimer.runTaskLater(Main.instance, pickUpTime);
                // 显示title (除以20是因为默认TPS为20，可以除以这个数把Ticks转换成second)
                PlayerUtils.showTitle(op, "#YELLOW#您的队友已从背上掉落", "#AQUA#请及时背起队友，" + (pickUpTime / 20) + "秒后将会受到伤害");
            }
        }
    }

    // 当玩家射出弓箭的时候
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.PLAYER) {
            GameUtils.getTeamByPlayer((Player) entity, t -> event.getProjectile().setCustomName(event.getEntity().getName()));
        }
    }

    // https://github.com/Loving11ish/SpeedLimit/blob/master/src/main/java/me/loving11ish/speedlimit/events/ElytraFlightEvent.java#L61
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        // 获取移动的玩家
        Player p = event.getPlayer();
        // 获取玩家的位置
        Location from = event.getFrom();
        // 解析玩家的位置
        double x = from.getX(),
               y = from.getY(),
               z = from.getZ();
        // 获取朝向(?
        float yaw = from.getYaw(),
              pitch = from.getPitch();
        // 如果被限速的玩家包含此玩家，且此玩家正在用鞘翅飞
        if (limitedSpeedPlayers.contains(p) && p.isGliding()) {
            // 得到玩家移动到的位置
            Location to = event.getTo();
            // 防止NPE(特殊情况)
            if (to != null) {
                // 我对limit的印象只有这里的注释qwq，其他的都不是我改的(
                // 获取limit
                int limit = SlowArrow.limit;
                // 如果玩家原本的位置（X、Y、Z）减去玩家要去的位置的绝对值 大于limit
                if (Math.abs(from.getX() - to.getX()) > limit
                    || Math.abs(from.getY() - to.getY()) > limit
                    || Math.abs(from.getZ() - to.getZ()) > limit) {
                    // 将玩家Tp到原先 Y+1 的位置
                    Location old = new Location(p.getWorld(), x, y + 1, z, yaw, pitch);
                    p.teleport(old);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEntityEvent event){
        if (event.getHand() == EquipmentSlot.OFF_HAND && event.getRightClicked().getType() == EntityType.PLAYER){
            Player p2 = (Player) event.getRightClicked();
            Player p1 = event.getPlayer();
            GameUtils.getTeamByPlayer(p1, t -> {
                if (t.isP2(p2)){
                    Player p = (Player) GameUtils.find(t.getP1().getPassengers(), passenger -> passenger == t.getP1());
                    if (p == null){  // 还没捡起来
                        t.ride();
                        PlayerUtils.send(p1, "#AQUA#[FlyWars] #GREEN#真不错，你成功捡起了队友");
                        PlayerUtils.send(p2, "#AQUA#[FlyWars] #GREEN#你队友真给力哈");PlayerUtils.showTitle(t.getP2(), "#RED#您的猪队友没能及时捡起你", "qswl");
                    }
                }
            });
        }
    }
}
