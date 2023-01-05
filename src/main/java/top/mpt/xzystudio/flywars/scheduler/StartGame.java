package top.mpt.xzystudio.flywars.scheduler;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import top.mpt.xzystudio.flywars.game.Team;
import top.mpt.xzystudio.flywars.game.TeammateType;
import top.mpt.xzystudio.flywars.utils.ItemUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static top.mpt.xzystudio.flywars.game.Game.teams;

public class StartGame extends BukkitRunnable{
    @Override
    public void run() {
        // 遍历team数组
        for (Team team : teams) {
            // 获取每个team的两名玩家
            Player p1 = team.getP1();
            Player p2 = team.getP2();
            HashMap<Player, TeammateType> map = team.players;
            // 遍历一个team的两名玩家的数组
            for (Player p : Arrays.asList(p1, p2)) {
                // 向玩家展示信息
                PlayerUtils.send(p, "&f[FlyWars] &9你与 <%s> 组为一队", team.getTheOtherPlayer(p).getName());
                PlayerUtils.showTitle(p, "&a游戏开始", "&cFlyWars 飞行战争");
                p.setGameMode(GameMode.SURVIVAL);
                if (map.get(p) == TeammateType.P1) {
                    // 设置玩家飞行
                    if (!p.getAllowFlight()) p.setAllowFlight(true);
                    p.setFlying(true);
                    // 清空玩家背包
                    PlayerInventory inv = p.getInventory();
                    inv.clear();
                    // 给玩家发放物资
                    inv.setChestplate(ItemUtils.newItem(Material.ELYTRA, ChatColor.AQUA + "战争鞘翅", new ArrayList<>(), 1, true));
                    inv.setItemInOffHand(ItemUtils.newItem(Material.GOLDEN_APPLE, ChatColor.GOLD + "天赐金苹果"));
                    inv.setItemInMainHand(ItemUtils.newItem(Material.FIREWORK_ROCKET, ChatColor.RED + "核弹"));
                }
            }
        }
    }
}
