package top.mpt.xzystudio.flywars.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mpt.xzystudio.flywars.Main;
import top.mpt.xzystudio.flywars.Team;
import top.mpt.xzystudio.flywars.commands.ICommand;
import top.mpt.xzystudio.flywars.scheduler.PigSche;

import java.util.*;

public class start extends ICommand {
    public start(){
        super("start", "", "开始游戏，请在人数足够后执行");
    }

    public static ItemStack NewItem(Material type, String DisplayName, List<String> Lores){
        ItemStack myItem = new ItemStack(type);
        ItemMeta im = myItem.getItemMeta();
        im.setDisplayName(DisplayName);
        im.setLore(Lores);
        myItem.setItemMeta(im);
        return myItem;
    }
    public static ItemStack NewItem(Material type, String DisplayName){
        return NewItem(type, DisplayName, Arrays.asList());
    }

    public boolean onCommand(CommandSender sender, String[] args) {
//        Main.instance.getServer().broadcastMessage(ChatColor.WHITE + "[FlyWars] " + ChatColor.BLUE + "管理员已开始游戏，开始随机组队");

        List<Player> players = new ArrayList<>(Main.instance.getServer().getOnlinePlayers());

        if (players.size() % 2 != 0){
            sender.sendMessage(ChatColor.RED + "玩家数量为奇数，不能开始游戏！");
            return true;
        } else if (players.size() < 4) {
            sender.sendMessage(ChatColor.RED + "四个人都不到，你想跟谁玩？？？");
            return true;
        }

        while(!players.isEmpty()) {
            Random rand = new Random();
            int i1 = rand.nextInt(players.size()),
                    i2 = rand.nextInt(players.size());
            while (i1 == i2) i2 = rand.nextInt(players.size());
            Player p1 = players.get(i1);
            Player p2 = players.get(i2);

            p1.sendMessage(ChatColor.WHITE + "[FlyWars] " + ChatColor.BLUE + "您与<" + p2.getName() + ">组为一队");
            p2.sendMessage(ChatColor.WHITE + "[FlyWars] " + ChatColor.BLUE + "您与<" + p1.getName() + ">组为一队");
            p1.sendTitle(ChatColor.GREEN + "游戏开始", ChatColor.RED + "FlyWars飞行战争");
            p2.sendTitle(ChatColor.GREEN + "游戏开始", ChatColor.RED + "FlyWars飞行战争");
            Main.instance.getLogger().info("test");
            p1.setGameMode(GameMode.SURVIVAL);
            p2.setGameMode(GameMode.SURVIVAL);
            p1.setFlying(true);
            p1.getInventory().clear();
            p1.getInventory().setChestplate(NewItem(Material.ELYTRA, "战争鞘翅"));

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