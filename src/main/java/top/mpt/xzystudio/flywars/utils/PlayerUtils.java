package top.mpt.xzystudio.flywars.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Player工具类
 * @author WindLeaf_qwq
 */
public class PlayerUtils {

    /**
     * 给玩家发送信息
     * @param sender 玩家或控制台
     * @param message 要发送的消息
     */
    public static void send(CommandSender sender, String message) {
        StringBuilder sb = new StringBuilder();
        Collections.singletonList(message).forEach(it -> sb.append(it).append(" "));
        sender.sendMessage(ChatUtils.translateColor(sb.toString()));
    }

    /**
     * 给玩家发送消息（带占位符)
     * @param sender 玩家或控制台
     * @param message 要发送的信息
     * @param args 占位符替换
     */
    public static void send(CommandSender sender, String message, Object... args) {
        send(sender, String.format(message, args));
    }

    /**
     * 给玩家显示标题
     * @param player 玩家
     * @param title 要显示的标题
     * @param subtitle 要显示的副标题
     */
    public static void showTitle(Player player, String title, String subtitle) {
        player.sendTitle(ChatUtils.translateColor(title), ChatUtils.translateColor(subtitle));
    }

    /**
     * 给玩家显示标题
     * @param player 玩家
     * @param title 标题
     * @param subtitle 副标题
     * @param titleArgs 标题占位符
     * @param subtitleArgs 副标题占位符
     */
    public static void showTitle(Player player, String title, String subtitle, List<Object> titleArgs, List<Object> subtitleArgs) {
        showTitle(player,
                titleArgs != null ? String.format(title, titleArgs) : title,
                subtitleArgs != null ? String.format(subtitle, subtitleArgs) : subtitle);
    }
}
