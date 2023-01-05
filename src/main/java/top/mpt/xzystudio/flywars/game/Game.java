package top.mpt.xzystudio.flywars.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.*;

public class Game {
    // 获取config里的开始游戏最少需要的玩家数
    private final Integer minPlayerCount = (Integer) ConfigUtils.getConfig("min-player-count", 2);
    // 获取当前在线的玩家数
    private final ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    private CommandSender sender = null;

    public Game(CommandSender sender) {
        this.sender = sender;
    }

    // 检查当前在线的玩家数目是否合规
    private boolean check() {
        // 若玩家数为奇数
        if (players.size() % 2 != 0){
            PlayerUtils.send((Player) sender, "&c玩家数量为奇数，不能开始游戏！");
            return false;
        } else if (players.size() < minPlayerCount) { // 若玩家数未达到config里的minPlayerCount
            PlayerUtils.send((Player) sender, "&c{}个人都不到，你想跟谁玩？");
            return false;
        }
        return true;
    }

    // 随机分配队伍
    private void startGame() {
        int iter = 0;
        // 循环直到玩家数组为空
        while (!players.isEmpty()) {
            // 取两个随机数当做下标
            Random rand = new Random();
            int i1 = rand.nextInt(players.size()),
                    i2 = rand.nextInt(players.size());
            // 如果抽到了相同的下标就重新抽一遍
            while (i1 == i2) i2 = rand.nextInt(players.size());
            // 获取对应数组下标的玩家
            Player p1 = players.get(i1);
            Player p2 = players.get(i2);
            // 创建一个Map，放入player
            Map<Player, String> map = new HashMap<>();
            map.put(p1, "p1");
            map.put(p2, "p2");
            // 创建一个Team，
            Team team = new Team(p1, p2, iter);
            // 在主类的playerdata下加一个刚刚创建的team
            Main.playerData.add(team);
            // 主类teammap下加player的name和下标
            Main.teamMap.put(p1.getName(), iter);
            Main.teamMap.put(p2.getName(), iter);
            // 下标递增
            iter++;
            // 循环遍历随机分配到的两名玩家组成的数组
            for (Player p : Arrays.asList(p1, p2)) {
                PlayerUtils.send(p, "&f[FlyWars] &9你与 [{}] 组为一队", (Objects.equals(map.get(p), "p1") ? p2 : p1).getName());
                PlayerUtils.showTitle(p, "&a游戏开始", "&cFlyWars飞行战争");
                p.setGameMode(GameMode.SURVIVAL);
                if (Objects.equals(map.get(p), "p1")) {
                    if (!p.getAllowFlight()) p.setAllowFlight(true);
                    p.setFlying(true);
                    PlayerInventory inv = p.getInventory();
                    inv.clear();
                    inv.setChestplate(ItemUtils.newItem(Material.ELYTRA, ChatColor.AQUA + "战争鞘翅"));
                    inv.setItemInOffHand(ItemUtils.newItem(Material.GOLDEN_APPLE, ChatColor.GOLD + "天赐金苹果"));
                    inv.setItemInMainHand(ItemUtils.newItem(Material.FIREWORK_ROCKET, ChatColor.RED + "核弹"));
                }
                // 在players数组里删除已经被分好组的玩家
                players.remove(p);
            }
        }
    }
}
