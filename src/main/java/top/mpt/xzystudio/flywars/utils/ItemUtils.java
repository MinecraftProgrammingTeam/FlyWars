package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Item工具类
 * @author WindLeaf_qwq
 */
public class ItemUtils {
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
}
