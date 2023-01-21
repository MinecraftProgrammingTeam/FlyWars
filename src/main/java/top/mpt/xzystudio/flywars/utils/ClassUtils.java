package top.mpt.xzystudio.flywars.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;

public class ClassUtils {
    public static ArrayList<Class<?>> getSubclasses(String packagePath) {
        ArrayList<Class<?>> result = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(packagePath).scan()) {

        }
        return result;
    }
}
