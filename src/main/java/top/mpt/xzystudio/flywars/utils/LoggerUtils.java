package top.mpt.xzystudio.flywars.utils;

import top.mpt.xzystudio.flywars.Main;

/**
 * 日志输出工具库
 */
public class LoggerUtils {
    /**
     * 输出info(with arguments)
     * @param log info
     * @param args arguments
     */
    public static void info(Object log, Object... args){
        Main.instance.getLogger().info(ChatUtils.translateColor(log.toString(), args));
    }

    /**
     * 输出warning(with arguments)
     * @param log warning
     * @param args arguments
     */
    public static void warning(Object log, Object... args){
        Main.instance.getLogger().warning(ChatUtils.translateColor(log.toString(), args));
    }
}