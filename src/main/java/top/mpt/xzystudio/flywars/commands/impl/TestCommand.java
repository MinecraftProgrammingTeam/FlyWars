package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.scheduler.PigSche;

/**
 * 测试指令
 */
public class TestCommand extends ICommand {
    public TestCommand(){
        super("test", "", "测试");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        // 测试用的qwq
        Player player = (Player) sender;
        Pig pigEntity = (Pig) player.getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
        pigEntity.setSaddle(true);
        pigEntity.setInvulnerable(true);
        pigEntity.setInvisible(true);
        PigSche pigSche = new PigSche();
        pigSche.setPigEntity(pigEntity);
        pigSche.setPlayer(player);
        pigSche.runTaskTimer(Main.instance, 0, Main.instance.getConfig().getInt("period"));
        return true;
    }

    public String permission(){
        return "flywars.fw.test";
    }
}