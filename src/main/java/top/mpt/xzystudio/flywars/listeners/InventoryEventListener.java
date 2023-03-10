package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import top.mpt.xzystudio.flywars.game.gui.GuiManager;

/**
 * GUI(Inventory)EventListener
 */
public class InventoryEventListener implements Listener {
    // 当Inventory内的物品被点击
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) { return;}
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(GuiManager.title)){
            event.setCancelled(true);
            GuiManager.processClick(event);
        }
    }
}
