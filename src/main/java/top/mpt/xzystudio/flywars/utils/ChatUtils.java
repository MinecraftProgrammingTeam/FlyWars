package top.mpt.xzystudio.flywars.utils;

public class ChatUtils {
    public static String translateColorCode(String message) {
        return message.replaceAll("&", "§");
    }

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
