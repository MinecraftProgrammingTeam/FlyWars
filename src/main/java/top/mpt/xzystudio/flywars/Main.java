package top.mpt.xzystudio.flywars;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import top.mpt.xzystudio.flywars.executor.*;
import top.mpt.xzystudio.flywars.game.Team;
import top.mpt.xzystudio.flywars.listeners.*;

import java.util.*;

public final class Main extends JavaPlugin {
    public static Main instance;
    public static List<Team> playerData = new ArrayList<>();  // 存放团队
    public static Map<String, Integer> teamMap = new HashMap<>();  // 存放每个玩家所属的团队在playerData的下标

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Plugin startup logic
        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Plugin plugin = getPlugin(Main.class);

        // reg commands
        Objects.requireNonNull(getCommand("fw")).setExecutor(new CommandHandler());

        // reg listeners
        getServer().getPluginManager().registerEvents(new customEventListener(), this);

        getLogger().info(ChatColor.GREEN + "成功启用空岛战争插件");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
