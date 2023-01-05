package top.mpt.xzystudio.flywars;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import top.mpt.xzystudio.flywars.executor.CommandHandler;
import top.mpt.xzystudio.flywars.listeners.customEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

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
