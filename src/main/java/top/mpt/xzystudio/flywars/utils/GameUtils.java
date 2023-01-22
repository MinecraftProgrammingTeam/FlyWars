package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameUtils {
    /**
     * 调用事件
     * @param event 事件
     */
    public static void callEvent(Event event) {
        Bukkit.getScheduler().runTask(Main.instance, () -> Main.instance.getServer().getPluginManager().callEvent(event));
    }

    /**
     * 新增物品
     * @param type 物品类型
     * @param displayName 显示名称
     * @param lores 物品属性
     * @param amount 物品数目
     * @param unbreakable 物品是否为无限耐久
     * @param level 附魔的等级
     * @param ench 附魔属性
     * @return 物品
     */
    public static ItemStack newItem(Material type, String displayName, List<String> lores, int amount, boolean unbreakable, int level, Enchantment... ench) {
        ItemStack myItem = new ItemStack(type, amount);
        ItemMeta im = myItem.getItemMeta();
        assert im != null;
        im.setDisplayName(ChatUtils.translateColor(displayName));
        im.setLore(lores.stream().map(ChatUtils::translateColor).collect(Collectors.toList()));
        im.setUnbreakable(unbreakable);
        if (ench != null && level != 0){
            for (Enchantment e : ench){
                im.addEnchant(e, level, true);
            }
        }
        myItem.setItemMeta(im);
        return myItem;
    }

    /**
     * 新增物品
     * @param type 物品类型
     * @param displayName 显示名称
     * @return 物品
     */
    public static ItemStack newItem(Material type, String displayName){
        return newItem(type, displayName, Collections.emptyList(), 64, false, 0, null);
    }

    public static ItemStack newItem(Material type, String displayName, int level, Enchantment... ench){
        return newItem(type, displayName, Collections.emptyList(), 64, false, 0, ench);
    }

    public static ItemStack newItem(Material type, String displayName, int amount){
        return newItem(type, displayName, Collections.emptyList(), amount, false, 0, null);
    }

    /**
     * 根据所给的条件找到对应的游戏队伍对象
     * @param function 判断匿名函数
     * @return 游戏队伍对象，不存在则为 `null`
     */
    public static GameTeam getTeamBy(Function<GameTeam, Boolean> function) {
        AtomicReference<GameTeam> result = new AtomicReference<>();
        Game.teams.forEach(team -> {
            if (function.apply(team)) result.set(team);
        });
        return result.get();
    }

    /**
     * 根据玩家获取玩家所在的游戏队伍对象
     * @param player 玩家
     * @param consumer 获取游戏队伍后要做的事情
     * @return 游戏队伍对象，不存在则为 `null`
     */
    public static GameTeam getTeam(Player player, Consumer<GameTeam> consumer) {
        GameTeam team = getTeamBy(t -> t.isPlayerInTeam(player));
        if (consumer != null) consumer.accept(team);
        return team;
    }
}
