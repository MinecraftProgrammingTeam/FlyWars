package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.game.Game;
import top.mpt.xzystudio.flywars.game.team.GameTeam;
import top.mpt.xzystudio.flywars.scheduler.TimeLimit;

/**
 * 清空team指令
 */
public class FinishGameCommand extends ICommand {
    public FinishGameCommand() {
        super("finishgame", "", "结束游戏");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        new TimeLimit().run();
        return true;
    }

    public String permission(){
        return "flywars.fw.finishgame";
    }
}