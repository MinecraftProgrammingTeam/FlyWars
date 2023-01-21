package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

/**
 * 设置世界
 */
public class setborder extends ICommand {
    public setborder() {
        super("setborder", "<边界距>", "设置世界边界");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        WorldBorder worldBorder = Bukkit.createWorldBorder();
        worldBorder.setCenter(p.getLocation());
        worldBorder.setSize(Double.parseDouble(args[0]));
        PlayerUtils.send(sender, "[FlyWars] #GREEN#设置世界边界成功！");
        return true;
    }

    public String permission(){
        return "flywars.fw.setborder";
    }
}