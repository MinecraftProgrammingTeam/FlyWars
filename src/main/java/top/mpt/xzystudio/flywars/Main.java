package top.mpt.xzystudio.flywars;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import top.mpt.xzystudio.flywars.executor.*;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    public static Main instance;
    public static List<Team> playerData = new ArrayList<>();

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
        getCommand("fw").setExecutor(new CommandHandler());

        getLogger().info(ChatColor.GREEN + "成功启用空岛战争插件");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
