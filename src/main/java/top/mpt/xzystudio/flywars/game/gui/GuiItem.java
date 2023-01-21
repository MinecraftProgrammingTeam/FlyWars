package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import top.mpt.xzystudio.flywars.game.items.ArrowEntry;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;

import java.util.ArrayList;

/**
 * 给GUI新建Item的工具库(
 */
public class GuiItem {
    public Material material;
    public String name;
    public ItemStack item;
    public ArrowEntry process;

    /**
     * 新建的ItemInfo
     * @param material 材质
     * @param name 名称
     * @param ench 附魔
     * @param process
     */
    public GuiItem(Material material, String name, Enchantment ench, ArrowEntry process) {
        name = ChatUtils.translateColor(name);
        this.material = material;
        this.name = name;
        this.process = process;
        if (ench != null) {
            this.item = ItemUtils.newItem(material, name, new ArrayList<>(), 1, false, 1, ench);
        } else {
            this.item = ItemUtils.newItem(material, name, 1);
        }
    }

    /**
     * 获取到新建的Item
     * @return 新建的Item
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * 获取到新建的Material
     * @return Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * 获取ItemName
     * @return ItemName(String)
     */
    public String getName() {
        return name;
    }
}
