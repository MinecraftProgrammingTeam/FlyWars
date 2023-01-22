package top.mpt.xzystudio.flywars.utils;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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

    /**
     * 对象类指定根据初始化对象实例
     * @param clazz 类对象
     * @param args 参数，可为空
     * @return 初始化后的实例对象
     * @param <T> 泛♂型
     */
    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            if (args.length == 0) return clazz.newInstance();
            else {
                Constructor<T> constructor = clazz.getDeclaredConstructor(
                        (Class<?>[]) Arrays.stream(args).map(o -> (Class<?>) o.getClass()).toArray()
                );
                return constructor.newInstance(args);
            }
        } catch (Exception e) {
            LoggerUtils.warning("#RED#创建对象失败：%s", e.getMessage());
        }
        return null;
    }

    /**
     * 根据注解类获取指定类的注解对象
     * @param clazz 类指定
     * @param annotationClass 类注解
     * @return 获取到的注解对象，如果不存在则为 `null`
     * @param <T> 注解泛型
     */
    public static <T> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
        Annotation result = clazz.getAnnotation((Class<Annotation>) annotationClass);
        return result == null ? null : (T) result;
    }
}
