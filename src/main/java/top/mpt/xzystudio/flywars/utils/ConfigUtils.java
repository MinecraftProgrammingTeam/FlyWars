package top.mpt.xzystudio.flywars.utils;

import top.mpt.xzystudio.flywars.Main;

public class ConfigUtils {
    public static Object getConfig(String path) {
        Main instance = Main.instance;
        return instance.getConfig().get(path);
    }

    public static Object getConfig(String path, Object defaultValue) {
        Object result = getConfig(path);
        return result == null ? defaultValue : result;
    }
}
