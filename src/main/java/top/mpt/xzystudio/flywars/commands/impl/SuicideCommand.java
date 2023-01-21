package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.commands.ICommand;

/**
 * 紫砂指令
 */
public class SuicideCommand extends ICommand {
    public SuicideCommand() {
        super("suicide", "", "自杀");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.setHealth(0);
        return true;
    }

    public String permission(){
        return "flywars.fw.killme";
    }
}