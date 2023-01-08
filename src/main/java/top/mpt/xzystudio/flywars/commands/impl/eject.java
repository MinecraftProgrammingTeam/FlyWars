package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.commands.ICommand;

/**
 * 清空team指令
 */
public class eject extends ICommand {
    public eject() {
        super("eject", "<玩家ID>", "你为啥骑在我背上？给我下来！");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.eject();
        return true;
    }

    public String permission(){
        return "flywars.fw.rmpassenger";
    }
}