package top.mpt.xzystudio.flywars.utils;

import org.bukkit.entity.Player;

import java.util.Collections;

/**
 * Player工具类
 */
public class PlayerUtils {

    /**
     * 给玩家发送信息
     * @param player 玩家
     * @param message 要发送的消息
     */
    public static void send(Player player, String message) {
        StringBuilder sb = new StringBuilder();
        Collections.singletonList(message).forEach(it -> sb.append(it).append(" "));
        player.sendMessage(ChatUtils.translateColorCode(sb.toString()));
    }

    /**
     * 给玩家发送消息（带占位符)
     * @param player 玩家
     * @param message 要发送的信息
     * @param args 占位符替换
     */
    public static void send(Player player, String message, Object... args) {
        send(player, String.format(message, args));
    }

    /**
     * 给玩家显示标题
     * @param player 玩家
     * @param title 要显示的标题
     * @param subtitle 要显示的副标题
     */
    public static void showTitle(Player player, String title, String subtitle) {
        player.sendTitle(ChatUtils.translateColorCode(title), ChatUtils.translateColorCode(subtitle));
    }
}
