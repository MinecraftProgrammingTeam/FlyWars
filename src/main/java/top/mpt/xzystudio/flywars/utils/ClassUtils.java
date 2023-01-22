package top.mpt.xzystudio.flywars.utils;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;

/**
 * Class工具库
 */
@SuppressWarnings("unchecked")
public class ClassUtils {
    /**
     * 获取指定包下的所有指定类的子类
     * @param clazz 要获取的类
     * @param packagePath 包路径
     * @return 子类列表
     */
    public static <T> ArrayList<Class<T>> getSubClasses(Class<T> clazz, String packagePath) {
        Reflections reflections = new Reflections(packagePath);
        return reflections.get(SubTypes.of(clazz).asClass())
                .stream().map(c -> (Class<T>) c)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LoggerUtils.warning("#RED#创建对象失败：%s", e.getMessage());
        }
        return null;
    }
}
