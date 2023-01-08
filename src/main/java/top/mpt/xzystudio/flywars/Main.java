package top.mpt.xzystudio.flywars;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import top.mpt.xzystudio.flywars.executor.CommandHandler;
import top.mpt.xzystudio.flywars.listeners.GameEventListener;
import top.mpt.xzystudio.flywars.listeners.PlayerEventListener;
import top.mpt.xzystudio.flywars.utils.ChatUtils;

import java.util.Objects;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Server server = getServer();

        // config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // reg commands
        Objects.requireNonNull(getCommand("fw")).setExecutor(new CommandHandler());

        // reg listeners
        server.getPluginManager().registerEvents(new GameEventListener(), this);
        server.getPluginManager().registerEvents(new PlayerEventListener(), this);

        // log info
        getLogger().info(ChatUtils.translateColor("#GREEN#成功启用天空战争(FlyWars)插件！"));
        getLogger().info(ChatUtils.translateColor("#BLUE#更多信息请前往：https://github.com/MinecraftProgrammingTeam/FlyWars"));
        getLogger().info(ChatUtils.translateColor("#AQUA#本插件由MPT强力驱动，MPT论坛：https://www.minept.top"));
        getLogger().warning(ChatUtils.translateColor("#RED#我爱灰灰❤"));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatUtils.translateColor("#RED#天空战争插件已被禁用！！！"));
    }
}
