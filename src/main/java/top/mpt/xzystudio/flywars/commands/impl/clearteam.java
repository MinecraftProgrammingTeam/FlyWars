package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 清空team指令
 */
public class clearteam extends ICommand {
    public clearteam() {
        super("clearteam", "", "清除当前所有的的团队和计分板");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        // 重置计分板
        Game.scoreboardManager.reset();
        // 注销全部teams
        Game.teams.forEach(GameTeam::unregTeam);
        // 清空teams数组
        Game.teams.clear();
        return true;
    }

    public String permission(){
        return "flywars.fw.clearteam";
    }
}