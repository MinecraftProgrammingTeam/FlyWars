package top.mpt.xzystudio.flywars.game;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeammateType;
import top.mpt.xzystudio.flywars.scheduler.ResourcesUpdate;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.*;

/**
 * Game的逻辑类
 */
public class Game {
    // 存储所有的队伍对象
    public static final ArrayList<GameTeam> teams = new ArrayList<>();
    // 获取config里的开始游戏最少需要的玩家数
    private final Integer minPlayerCount = (Integer) ConfigUtils.getConfig("min-player-count", 2);
    // 获取当前在线的玩家数
    private final ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    // 计分板
    public static final ScoreboardManager scoreboardManager = new ScoreboardManager();
    /**
     * 资源刷新
     */
    public static final ResourcesUpdate resUpdater = new ResourcesUpdate();
    
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
//        if (players.size() % 2 != 0){
//            PlayerUtils.send(sender, "#RED#玩家数量为奇数，不能开始游戏！");
//            return false;
//        }
        if (players.size() < minPlayerCount) { // 若玩家数未达到config里的minPlayerCount
            PlayerUtils.send(sender, "#RED#%s个人都不到，你想跟谁玩？", minPlayerCount.toString());
            return false;
        }
        return true;
    }

    /**
     * 随机分配队伍
     */
    public void assignTeams() {
        teams.clear();
        ArrayList<Player> copy = new ArrayList<>(players);
        // 循环直到玩家数组为空
        while (!copy.isEmpty()) {
            if (copy.size() == 1){
                // 最后一个人剩下可能是单数，不让他加入游戏了
                PlayerUtils.send(sender, "#RED#剩余一个玩家 #BLUE#<%s> #RED#不得加入游戏！", copy.get(0).getName());
                break;
            }
            // 取两个随机数当做下标
            Random rand = new Random();
            int i1 = rand.nextInt(copy.size()),
                i2 = rand.nextInt(copy.size());
            // 如果抽到了相同的下标就重新抽一遍，直到抽到不是相同下标的玩家
            while (i1 == i2) i2 = rand.nextInt(copy.size());
            // 获取对应数组下标的玩家
            Player p1 = copy.get(i1);
            Player p2 = copy.get(i2);
            // 随机颜色
            ChatColor color = ChatUtils.randomColor(); // ChatColor.RED
            // 循环，避免创建相同名称的团队
            while(Bukkit.getScoreboardManager().getMainScoreboard().getTeam(color.name()) != null){
                color = ChatUtils.randomColor();
            }
            // 将随机到的颜色转化为颜色中文名
            String colorName = ChatUtils.getColorName(color);
            // 注册Team
            Team team = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().registerNewTeam(color.name());
            // 设置前缀
            team.setPrefix(String.format("[%s队] ", colorName));
            // 设置颜色
            team.setColor(color);
            // 友伤关闭 - 没爱了 - 6
            team.setAllowFriendlyFire(false);
            // 加入Team
            GameTeam gameTeam = new GameTeam(p1, p2, team, color.name(), colorName);

            for (Player p : Arrays.asList(p1, p2)) {
                p.eject();
                p.setHealth(20);
                team.addEntry(p.getName());
                copy.remove(p);
            }

            teams.add(gameTeam);
        }
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        Player pl = (Player) sender;
        // 遍历服务器里每一位玩家
        for (Player p : Main.instance.getServer().getOnlinePlayers()){
            // 向玩家展示标题
            PlayerUtils.showTitle(p, "#RED#游戏即将开始", "请保存好贵重物品，即将清空物品栏！");
        }

        new BukkitRunnable() {
            // 在run方法中不要创建计分板，要不然后期`GameTeam.getBoard()`获取不到（（（
            @Override
            public void run() {
                // 资源刷新点
                resUpdater.setGameWorld(pl.getWorld());
                try {
//                    if (!resUpdater.isCancelled()) resUpdater.cancel(); // 这样写没用，还是会报错，干脆不用它了，用trycatch
                    resUpdater.runTaskTimer(Main.instance, 0, Long.parseLong(ConfigUtils.getConfig("refresh-tick", 600).toString()));
                } catch (Exception e){
                    Main.instance.getLogger().warning(ChatUtils.translateColor("#RED#奇奇怪怪的BUG出现了，不过应该问题不大"));
                    ChatUtils.broadcast(ChatUtils.translateColor("[FlyWars] #GOLD#BugWars: #RED#创建资源刷新点任务失败，可能是已存在！"));
                }
                // 计分板
                teams.forEach(scoreboardManager::newBoard);
                scoreboardManager.renderScoreboard();
                // 遍历team数组
                for (GameTeam gameTeam : teams) {
                    // 获取每个team的两名玩家
                    Player p1 = gameTeam.getP1();
                    Player p2 = gameTeam.getP2();
                    HashMap<Player, TeammateType> map = gameTeam.players;
                    // 遍历一个team的两名玩家的数组
                    for (Player p : Arrays.asList(p1, p2)) {
                        // 将玩家TP到腐竹指定的场所
                        Location loc = new Location(pl.getWorld(),
                                (Integer) ConfigUtils.getConfig("start-x", 0),
                                (Integer) ConfigUtils.getConfig("start-y", 0),
                                (Integer) ConfigUtils.getConfig("start-z", 0));
                        p.teleport(loc); // tp
                        gameTeam.ride(); // 骑
                        // 向玩家展示信息
                        PlayerUtils.send(p, "[FlyWars] 你是 %s, 你的队友是 <%s>", gameTeam.getTeamDisplayName() ,gameTeam.getTheOtherPlayer(p).getName());
                        PlayerUtils.showTitle(p, "#GREEN#游戏开始", "#RED#FlyWars 飞行战争");
                        p.setGameMode(GameMode.SURVIVAL);
                        PlayerInventory inv = p.getInventory();
                        // 清空背包
                        inv.clear();
                        // 给予玩家♂物资♂
                        if (map.get(p) == TeammateType.P1) { // 如果玩家为P1(移动者)
                            inv.setChestplate(ItemUtils.newItem(Material.ELYTRA, "#AQUA#战争鞘翅", new ArrayList<>(), 1, true, 0, null));
                            inv.setItemInOffHand(ItemUtils.newItem(Material.GOLDEN_APPLE, "#GOLD#天赐金苹果", 0, null));
                            inv.setItemInMainHand(ItemUtils.newItem(Material.FIREWORK_ROCKET, "#RED#核弹", 0, null));
                        } else {    // 如果玩家为P2(攻击者)
                            inv.setChestplate(ItemUtils.newItem(Material.GOLDEN_CHESTPLATE, "#GOLD#金光晃晃", new ArrayList<>(), 1, true, 1, null));
                            inv.setItemInMainHand(ItemUtils.newItem(Material.CROSSBOW, "#RED#AK47", new ArrayList<>(), 1, true, 1, Enchantment.MULTISHOT, Enchantment.QUICK_CHARGE));
                            inv.setItemInOffHand(ItemUtils.newItem(Material.ARROW, "#WHITE#子弹", new ArrayList<>(), 64, false, 1, null));
                        }
                    }
                }
            }
        }.runTaskLater(Main.instance, (Integer) ConfigUtils.getConfig("delay-tick", 200));
        // runTaskLater 延迟♂执行
    }
}