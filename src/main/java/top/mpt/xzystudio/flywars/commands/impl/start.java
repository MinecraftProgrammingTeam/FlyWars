package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.game.Game;

public class start extends ICommand {
    public start(){
        super("start", "", "开始游戏，请在人数足够后执行");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Game game = new Game(sender);
        if (game.check()){
            game.assignTeams();
            game.startGame();
        }
        return true;
    }

    public String permission(){
        return "flywars.fw.start";
    }
}