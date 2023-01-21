package top.mpt.xzystudio.flywars.game.gui;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public abstract class GuiProcess {
    public abstract void run(Player pShoot, Player p, Arrow arrow);
}
