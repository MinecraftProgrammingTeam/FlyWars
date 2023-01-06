package top.mpt.xzystudio.flywars;

import org.bukkit.plugin.java.JavaPlugin;
import top.mpt.xzystudio.flywars.executor.CommandHandler;
import top.mpt.xzystudio.flywars.listeners.*;
import top.mpt.xzystudio.flywars.utils.ChatUtils;

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
        server.getPluginManager().registerEvents(new GameEventListener(), this);

        // log info
        getLogger().info(ChatUtils.translateColor("#GREEN#成功启用天空战争插件！"));
    }

    @Override
    public void onDisable() {}
}
