package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ClassUtils;
import top.mpt.xzystudio.flywars.utils.GameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GUI管理器
 */
public class GuiManager {
    public static List<GuiItem> items = new ArrayList<>();
    public static final String title = ChatUtils.translateColor("#AQUA#[FlyWars] #GREEN#商店系统");

    /**
     * 获取打开GUI的玩家所在Team的Info
     * @param player 玩家
     * @return TeamInfo
     */
    private static TeamInfo getInfo(Player player){
        GameTeam team = GameUtils.getTeam(player, null);
        return team != null ? ScoreboardManager.info.get(team) : null;
    }

    /**
     * 初始化
     */
    public static void init() {
        // TODO newInstance时传入path参数
        ClassUtils.getSubClasses(ArrowEntry.class, "top.mpt.xzystudio.flywars.game.items.arrows").forEach(clazz -> {
            ArrowInfo info = clazz.getAnnotation(ArrowInfo.class);
            ArrowEntry entry = ClassUtils.newInstance(clazz);
            if (entry != null) items.add(new GuiItem(info.material(), info.name(), null, entry));
        });
    }

    /**
     * 给指定玩家打开GUI
     * @param player 玩家
     */
    public static void openGui(Player player) {
        TeamInfo info = getInfo(player);
        if (info == null) {
            player.sendMessage(ChatUtils.translateColor("#AQUA#[FlyWars] #RED#商店信息获取失败，您可能不在当前游戏中"));
            return;
        }

        Inventory inv = Bukkit.createInventory(player, InventoryType.CHEST, title);
        items.forEach(it -> {
            inv.addItem(it.getItem());
        });
        GameTeam team = GameUtils.getTeam(player, null);
        List<String> lores = Arrays.asList("#GREEN#击杀数：" + info.getKillCount(), "#AQUA#所属队伍：" + (team != null ? team.getTeamDisplayName() : "[无法获取]"));
        inv.setItem(22, GameUtils.newItem(Material.PLAYER_HEAD, "#YELLOW#" + player.getName(), lores, 1, false, 0, null));
        player.openInventory(inv);
    }
}