package top.mpt.xzystudio.flywars.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

/**
 * Chat工具类
 * @author WindLeaf_qwq
 */
public class ChatUtils {
    // 颜色List
    private static final ArrayList<ChatColor> colors = new ArrayList<>();
    // 存放颜色代码对应的中文
    private static final HashMap<ChatColor, String> colorsNameMap = new HashMap<ChatColor, String>() {{
        put(AQUA, "青");
        put(BLACK, "黑");
        put(BLUE, "蓝");
        put(DARK_AQUA, "深青");
        put(DARK_BLUE, "深蓝");
        put(DARK_GRAY, "深灰"); // ??? - 我爱灰灰 - 6 - ♂ - 6
        put(DARK_GREEN, "深绿");
        put(DARK_PURPLE, "深紫");
        put(DARK_RED, "深红");
        put(GOLD, "金");
        put(GRAY, "灰");
        put(GREEN, "绿");
        put(LIGHT_PURPLE, "浅紫");
        put(RED, "红");
        put(WHITE, "白");
        put(YELLOW, "黄");
    }};

    /**
     * 将带特殊颜色代码的文本转为带颜色代码的文本
     * <p>换行
     * 例如："#RED#你输了！" -> "§c你输了！"
     * @param string 带特殊颜色代码的文本
     * @return 转换后的文本
     */
    public static String translateColor(String string) {
        // 正则表达式替换
        String result = string; // #RED#[红队]
        Pattern regex = Pattern.compile("#[A-Z_-]+#");
        Matcher matcher = regex.matcher(result);
        while (matcher.find()) {
            String code = matcher.group(); // #RED#
            result = result.replaceFirst(
                    code, // #RED#
                    "§" + ChatColor.valueOf(code.replaceAll("#", "")).getChar() // §c
            );
        }
        return result;
    }

    /**
     * 将带特殊颜色代码的文本转为带颜色代码的文本 (带占位符)
     * <p>
     * 例如："#RED#你输了！" -> "§c你输了！"
     * @param string 带特殊颜色代码的文本
     * @param args 占位符替换
     * @return 转换后的文本
     */
    public static String translateColor(String string, Object... args) {
        return translateColor(String.format(string, args));
    }

    /**
     * 全服公告消息(带占位符)
     * @param message 要公告的消息
     * @param args 占位符替换
     */
    public static void broadcast(String message, Object... args) {
        Bukkit.getOnlinePlayers().forEach(it -> PlayerUtils.send(it, message, args));
    }

    /**
     * 重设颜色
     */
    private static void resetColors() {
        colors.clear();
        colors.addAll(Arrays.stream(values()).filter(colorsNameMap::containsKey).collect(Collectors.toList()));
    }

    /**
     * 随机抽取队伍颜色
     * @return 队伍颜色
     */
    public static ChatColor randomColor() {
        Collections.shuffle(colors);
        Optional<ChatColor> random = colors.stream().findFirst();
        Supplier<ChatColor> getter = random.isPresent() ? random::get : () -> {
            resetColors();
            return randomColor();
        };
        return getter.get();
    }

    /**
     * 将颜色代码转换成中文文字
     * @param color 颜色代码
     * @return 对应中文文字
     */
    public static String getColorName(ChatColor color) {
        return colorsNameMap.getOrDefault(color, "未知");
    }
}
