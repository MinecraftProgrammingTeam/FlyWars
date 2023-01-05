package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.Team;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.scheduler.PigSche;

import java.util.*;

public class start extends ICommand {
    public start(){
        super("start", "", "开始游戏，请在人数足够后执行");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        if (players.size() % 2 != 0){
            sender.sendMessage(ChatColor.RED + "玩家数量为奇数，不能开始游戏！");
            return true;
        } else if (players.size() < 4) {
            sender.sendMessage(ChatColor.RED + "四个人都不到，你想跟谁玩？？？");
            return true;
        }

//        Iterator<Player> iter = players.iterator();
//        while (iter.hasNext()) {
//            Collections.shuffle(players);
//            Player p1 = iter.next();
//            Player p2 = iter.next();
//            Team team = new Team(p1, p2);
//            Main.playerData.add(team); // NPE
//            players.removeAll(Arrays.asList(p1, p2));
//        }

        while(!players.isEmpty()) {
            Random rand = new Random();
            int i1 = rand.nextInt(players.size()),
                    i2 = rand.nextInt(players.size());
            while (i1 == i2) i2 = rand.nextInt(players.size());
            Player p1 = players.get(i1);
            Player p2 = players.get(i2);
            Team team = new Team(p1, p2);
            players.remove(p1);
            players.remove(p2);
            Main.playerData.add(team);
        }
        return true;
    }

    public String permission(){
        return "flywars.fw.start";
    }
}