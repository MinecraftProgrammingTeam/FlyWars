package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Bukkit;

/**
 * Chat工具类
 */
public class ChatUtils {
    /**
     * 将&颜色字符替代成§
     * @param message 要进行替换的消息
     * @return 替换好了的消息
     */
    public static String translateColorCode(String message) {
        return message.replaceAll("&", "§");
    }

    /**
     * 将&颜色字符替代成§（带占位符）
     * @param message 要进行替换的消息
     * @param args 占位符替换
     * @return 替换好了的消息
     */
    public static String translateColorCode(String message, Object... args) {
        return translateColorCode(String.format(message, args));
    }

    /**
     * 全服公告消息（带占位符）
     * @param message 要公告的消息
     * @param args 占位符替换
     */
    public static void broadcast(String message, Object... args) {
        Bukkit.getOnlinePlayers().forEach(it -> PlayerUtils.send(it, message, args));
    }
}
