package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class ItemUtils {
    public static ItemStack newItem(Material type, String displayName, List<String> lores, int amount){
        ItemStack myItem = new ItemStack(type, amount);
        ItemMeta im = myItem.getItemMeta();
        assert im != null;
        im.setDisplayName(displayName);
        im.setLore(lores);
        myItem.setItemMeta(im);
        return myItem;
    }

    public static ItemStack newItem(Material type, String displayName){
        return newItem(type, displayName, Collections.emptyList(), 64);
    }
}
