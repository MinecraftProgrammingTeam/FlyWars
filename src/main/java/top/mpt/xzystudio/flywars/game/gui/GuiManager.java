package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.*;
import top.mpt.xzystudio.flywars.game.gui.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GuiManager {
    public static List<GuiItem> items = new ArrayList<>();
    public static final String title = ChatUtils.translateColor("#AQUA#[FlyWars] #GREEN#商店系统");

    private static GameTeam getTeam(Player player) {
        AtomicReference<GameTeam> team = new AtomicReference<>();
        Game.teams.forEach(it -> {
            if (it.isPlayerInTeam(player)){
                team.set(it);
            }
        });
        return team.get();
    }

    private static TeamInfo getInfo(Player player){
        GameTeam team = getTeam(player);
        if (team == null){
            return null;
        }
        return ScoreboardManager.info.get(team);
    }

    public static void init() {
        items.add(new GuiItem(Material.SPECTRAL_ARROW, "#AQUA#寒冰箭", null, new slow()));
        items.add(new GuiItem(Material.SPECTRAL_ARROW, "#RED#火焰箭", null, new fire()));
        items.add(new GuiItem(Material.SPECTRAL_ARROW, "#LIGHT_PURPLE#末影箭", null, new teleport()));
        items.add(new GuiItem(Material.SPECTRAL_ARROW, "#YELLOW#标记箭", null, new flag()));
        items.add(new GuiItem(Material.SPECTRAL_ARROW, "#DARK_RED#爆炸箭", null, new boom()));
    }

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