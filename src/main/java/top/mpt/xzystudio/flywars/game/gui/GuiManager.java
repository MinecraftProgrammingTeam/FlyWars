package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {
    public static List<GuiItem> items = new ArrayList<>();
    public static final String title = ChatUtils.translateColor("#GREEN#商店系统");

    public static void init() {
        items.add(new GuiItem(Material.ARROW, "#AQUA#寒冰箭", Enchantment.ARROW_DAMAGE));
        items.add(new GuiItem(Material.ARROW, "#RED火焰箭", Enchantment.ARROW_DAMAGE));
        items.add(new GuiItem(Material.ARROW, "#LIGHT_PURPLE#末影箭", Enchantment.ARROW_DAMAGE));
        items.add(new GuiItem(Material.ARROW, "#YELLOW#标记箭", Enchantment.ARROW_DAMAGE));
        items.add(new GuiItem(Material.ARROW, "#DARK_RED#爆炸箭", Enchantment.ARROW_DAMAGE));
    }

    public static void openGui(Player player){
        Inventory inv = Bukkit.createInventory(player, InventoryType.CHEST, title);
        items.forEach(it -> {
            inv.addItem(it.getItem());
        });
        player.openInventory(inv);
    }
}