package top.mpt.xzystudio.flywars.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class工具库
 */
public class ClassUtils {
    public static ArrayList<Class<?>> getSubClasses(Class<?> clazz, String packagePath) {
        LoggerUtils.info("尝试获取: %s | %s", clazz, packagePath);
        ArrayList<Class<?>> result;
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(packagePath).scan()) {
            ClassInfoList cil = scanResult.getSubclasses(clazz);
            cil.asMap().forEach((k, v) -> { // TODO remove debug
                LoggerUtils.info("获取到的ArrowEntry: %s | %s", k, v.getPackageName());
            });
            result = cil.getStandardClasses().asMap().values().stream().map(ClassInfo::loadClass).collect(Collectors.toCollection(ArrayList::new));
        }
        LoggerUtils.info("获取到的所有子类: %s", result.toString());
        return result;
    }
}
