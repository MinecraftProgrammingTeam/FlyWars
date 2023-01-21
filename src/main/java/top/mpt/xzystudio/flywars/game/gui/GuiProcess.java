package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class GuiProcess {
    public abstract void run(EntityDamageByEntityEvent event);
}
