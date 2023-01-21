package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.commands.ICommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 玩家骑乘命令
 */
public class RideCommand extends ICommand {
    public RideCommand() {
        super("ride", "", "骑");
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<String> params = new ArrayList<>();
        players.forEach(it -> params.add(it.getName()));
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