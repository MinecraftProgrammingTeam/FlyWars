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
        ArrayList<Class<?>> result;
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(packagePath).scan()) {
            ClassInfoList cil = scanResult.getSubclasses(clazz).directOnly();
            result = cil.getStandardClasses().asMap().values().stream().map(ClassInfo::loadClass).collect(Collectors.toCollection(ArrayList::new));
        }
        return result;
    }
}
