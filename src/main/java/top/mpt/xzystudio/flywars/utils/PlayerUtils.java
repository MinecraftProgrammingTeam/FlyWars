package top.mpt.xzystudio.flywars.utils;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class PlayerUtils {
    public static void send(Player player, String message) {
        StringBuilder sb = new StringBuilder();
        Collections.singletonList(message).forEach(it -> sb.append(it).append(" "));
        player.sendMessage(ChatUtils.translateColorCode(sb.toString()));
    }

    public static void send(Player player, String message, Object... args) {
        send(player, String.format(message, args));
    }

    public static void showTitle(Player player, String title, String subtitle) {
        player.sendTitle(ChatUtils.translateColorCode(title), ChatUtils.translateColorCode(subtitle));
    }
}
