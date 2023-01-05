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
    public ride() {
        super("ride", "", "骑");
        List<Player> players = new ArrayList<>(Main.instance.getServer().getOnlinePlayers());
        List<String> params = new ArrayList<>();
        for(Player p : players){
            params.add(p.getName());
        }
        setListParams(params);
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Objects.requireNonNull(Main.instance.getServer().getPlayer(args[0])).addPassenger((Player) sender);
        return true;
    }

    public String permission(){
        return "flywars.fw.ride";
    }
}