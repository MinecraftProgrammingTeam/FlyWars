package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.game.Game;

/**
 * 开始游戏
 */

public class StartCommand extends ICommand {
    public StartCommand(){
        super("start", "[游戏时长，不指定使用配置文件]", "开始游戏，请在人数足够后执行");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        Game game = new Game(sender);
        if (game.check()){
            game.assignTeams();
            if (args.length >= 1){
                game.startGame(Integer.valueOf(args[0]));
            }else {
                game.startGame(null);
            }
        }
        return true;
    }

    public String permission(){
        return "flywars.fw.start";
    }
}