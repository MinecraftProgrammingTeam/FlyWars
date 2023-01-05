package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.game.Team;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.utils.ConfigUtils;
import top.mpt.xzystudio.flywars.utils.ItemUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

import java.util.*;

public class start extends ICommand {
    public start(){
        super("start", "", "开始游戏，请在人数足够后执行");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Main.playerData = new ArrayList<>();
        int iter = 0;






        return true;
    }

    public String permission(){
        return "flywars.fw.start";
    }
}