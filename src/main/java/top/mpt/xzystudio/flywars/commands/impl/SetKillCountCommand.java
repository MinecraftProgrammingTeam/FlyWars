package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.events.TeamEliminatedEvent;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.scoreboard.ScoreboardManager;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.game.team.TeamInfo;
import top.mpt.xzystudio.flywars.utils.ChatUtils;
import top.mpt.xzystudio.flywars.utils.GameUtils;
import top.mpt.xzystudio.flywars.utils.PlayerUtils;

public class SetKillCountCommand extends ICommand {
    public SetKillCountCommand() {
        super("setkillcount", "<击杀数>", "设置击杀数");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        GameUtils.getTeamByPlayer(p, team -> {
            TeamInfo info = ScoreboardManager.info.get(team);
            info.setKillCount(Integer.valueOf(args[0]));
            PlayerUtils.send(sender, "#AQUA#[FlyWars] #GREEN#修改成功！");
        });
        return true;
    }

    public String permission(){
        return "flywars.fw.setkillcount";
    }
}