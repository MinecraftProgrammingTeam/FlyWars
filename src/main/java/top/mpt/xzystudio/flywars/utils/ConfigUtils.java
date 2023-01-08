package top.mpt.xzystudio.flywars.utils;

import top.mpt.xzystudio.flywars.Main;

import java.util.List;
import java.util.Map;

/**
 * Config工具类
 * @author WindLeaf & X_huihui
 */
public class ConfigUtils {
    /**
     * 获取Config
     * @param path 名称
     * @return ConfigValue
     */
    public static Object getConfig(String path) {
        Main instance = Main.instance;
        return instance.getConfig().get(path);
    }

    /**
     * 获取Config
     * @param path 名称
     * @param defaultValue 默认值
     * @return ConfigValue
     */
    public static Object getConfig(String path, Object defaultValue) {
        Object result = getConfig(path);
        return result == null ? defaultValue : result;
    }

    /**
     * 获取Config数组
     * @param path m名称
     * @return ListConfigValue
     */
    public static List<?> getListConfig(String path){
        Main instance = Main.instance;
        return instance.getConfig().getList(path);
    }

    /**
     * 获取Config数组
     * @param path 名称
     * @param defaultList 默认值
      *@return ListConfigValue
     */
    public static List<?> getListConfig(String path, List<?> defaultList){
        List<?> result = getListConfig(path);
        return result == null ? defaultList : result;
    }

    /**
     * 获取MapConfig数组
     * @param path 名称
     * @return MapConfigList
     */
    public static List<Map<?, ?>> getMapListConfig(String path){
        Main instance = Main.instance;
        return instance.getConfig().getMapList(path);
    }
}
