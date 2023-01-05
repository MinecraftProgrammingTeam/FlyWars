package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.Team;
import top.mpt.xzystudio.flywars.commands.ICommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ride extends ICommand {
    public ride(){
        super("ride", "<玩家ID>", "骑");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Bukkit.getPlayer(args[0]).setPassenger((Player) sender);
        return true;
    }

    public String permission(){
        return "flywars.fw.ride";
    }
}