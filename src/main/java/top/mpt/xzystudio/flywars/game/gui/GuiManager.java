package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.game.items.ArrowInfo;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.*;
import top.mpt.xzystudio.flywars.game.items.arrows.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * GUI管理器
 */
public class GuiManager {
    public static List<GuiItem> items = new ArrayList<>();
    public static final String title = ChatUtils.translateColor("#AQUA#[FlyWars] #GREEN#商店系统");

    /**
     * 获取打开GUI的玩家所在的Team
     * @param player 玩家
     * @return GameTeam
     */
    private static GameTeam getTeam(Player player) {
        AtomicReference<GameTeam> team = new AtomicReference<>();
        Game.teams.forEach(it -> {
            if (it.isPlayerInTeam(player)){
                team.set(it);
            }
        });
        return team.get();
    }

    /**
     * 获取打开GUI的玩家所在Team的Info
     * @param player 玩家
     * @return TeamInfo
     */
    private static TeamInfo getInfo(Player player){
        GameTeam team = getTeam(player);
        if (team == null){
            return null;
        }
        return ScoreboardManager.info.get(team);
    }

    /**
     * 初始化
     */
    public static void init() {
        ClassUtils.getSubClasses(ArrowEntry.class, "top.mpt.xzystudio.flywars.game.items.arrows").forEach(clazz -> {
            ArrowInfo info = clazz.getAnnotation(ArrowInfo.class);
            try {
                items.add(new GuiItem(info.material(), info.name(), null, (ArrowEntry) clazz.newInstance()));
            } catch (InstantiationException | IllegalAccessException e) {
                LoggerUtils.warning("#RED#%s", e.getMessage());
            }
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
        List<String> lores = Arrays.asList("#GREEN#击杀数：" + info.getKillCount(), "#AQUA#所属队伍：" + getTeam(player).getTeamDisplayName());
        inv.setItem(22, ItemUtils.newItem(Material.PLAYER_HEAD, "#YELLOW#" + player.getName(), lores, 1, false, 0, null));
        player.openInventory(inv);
    }
}