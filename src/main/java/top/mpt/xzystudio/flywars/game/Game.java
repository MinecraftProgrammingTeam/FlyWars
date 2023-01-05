package top.mpt.xzystudio.flywars.game;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.scheduler.StartGame;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Game的逻辑类
 */
public class Game {
    // 存储所有的队伍对象
    public static final ArrayList<Team> teams = new ArrayList<>();
    // 获取config里的开始游戏最少需要的玩家数
    private final Integer minPlayerCount = (Integer) ConfigUtils.getConfig("min-player-count", 2);
    // 获取当前在线的玩家数
    private final ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    private final CommandSender sender;

    public Game(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * 检查当前服务器里的玩家数是否合规
     * @return 当前服务器里的玩家数是否合规
     */
    public boolean check() {
        // 若玩家数为奇数
        if (players.size() % 2 != 0){
            PlayerUtils.send((Player) sender, "&c玩家数量为奇数，不能开始游戏！");
            return false;
        } else if (players.size() < minPlayerCount) { // 若玩家数未达到config里的minPlayerCount
            PlayerUtils.send((Player) sender, "&c%s个人都不到，你想跟谁玩？", minPlayerCount.toString());
            return false;
        }
        return true;
    }

    /**
     * 随机分配队伍
     */
    public void assignTeams() {
        ArrayList<Player> copy = new ArrayList<>(players);
        // 循环直到玩家数组为空
        while (!copy.isEmpty()) {
            // 取两个随机数当做下标
            Random rand = new Random();
            int i1 = rand.nextInt(copy.size()),
                i2 = rand.nextInt(copy.size());
            // 如果抽到了相同的下标就重新抽一遍
            while (i1 == i2) i2 = rand.nextInt(copy.size());
            // 获取对应数组下标的玩家
            Player p1 = copy.get(i1);
            Player p2 = copy.get(i2);
            // 创建一个Team，
            Team team = new Team(p1, p2);
            // 把创建好的Team加入
            teams.add(team);
            // 删除分好队伍的玩家
            copy.remove(p1);
            copy.remove(p2);
        }
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        Player pl = (Player) sender;
        for (Player p : Main.instance.getServer().getOnlinePlayers()){
            Location loc = new Location(pl.getWorld(), Main.instance.getConfig().getInt("loc-x"), Main.instance.getConfig().getInt("loc-y"), Main.instance.getConfig().getInt("loc-z"));
            p.teleport(loc);
            p.sendTitle(ChatColor.RED + "游戏即将开始", "请保存好贵重物品，即将清空物品栏！");
        }

        StartGame sg = new StartGame();
        sg.runTaskLater(Main.instance, Main.instance.getConfig().getInt("delay-tick"));
    }
}
