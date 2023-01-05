package top.mpt.xzystudio.flywars.utils;

public class ChatUtils {
    public static String translateColorCode(String message) {
        return message.replaceAll("&", "§");
    }

    public static String translateColorCode(String message, Object... args) {
        return translateColorCode(String.format(message, args));
    }
}
