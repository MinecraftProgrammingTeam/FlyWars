package top.mpt.xzystudio.flywars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import top.mpt.xzystudio.flywars.game.gui.GuiManager;

public class inventoryEventListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) { return;}
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(GuiManager.title)){
            event.setCancelled(true);
            // TODO check and give the player the item
        }
    }
}
