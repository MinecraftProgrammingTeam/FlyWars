package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;

public class GuiItem {
    public Material material;
    public String name;
    public ItemStack item;

    public GuiItem(Material material, String name, Enchantment ench) {
        name = ChatUtils.translateColor(name);
        this.material = material;
        this.name = name;
        this.item = ItemUtils.newItem(material, name, 1, ench);
    }

    public ItemStack getItem() {
        return item;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }
}
