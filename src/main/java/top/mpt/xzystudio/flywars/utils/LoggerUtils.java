package top.mpt.xzystudio.flywars.utils;

import top.mpt.xzystudio.flywars.Main;

import java.util.logging.Logger;

public class LoggerUtils {
    public static void info(String string, Object... args){
        Main.instance.getLogger().info(ChatUtils.translateColor(string, args));
    }
    public static void warning(String string, Object... args){
        Main.instance.getLogger().warning(ChatUtils.translateColor(string, args));
    }
}
