package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.game.gui.GuiManager;
import top.mpt.xzystudio.flywars.scheduler.PigSche;

/**
 * 打开GUI指令
 */
public class opengui extends ICommand {
    public opengui(){
        super("opengui", "", "打开GUI");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        // 测试用的qwq
        GuiManager.openGui((Player) sender);
        return true;
    }

    public String permission(){
        return "flywars.fw.opengui";
    }
}