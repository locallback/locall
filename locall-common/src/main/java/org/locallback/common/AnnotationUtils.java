package org.locallback.common;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationUtils {

    /**
     * 获取指定包中使用指定注解的所有方法
     *
     * @param annotation  annotation class
     * @param packageName package name
     * @return list of methods
     */
    public static List<Method> getAnnotatedMethods(Class<? extends Annotation> annotation, String packageName) {
        List<Method> methods = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {

            scanResult.getAllClasses().forEach(classInfo -> {
                Class<?> clazz = classInfo.loadClass();
                Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(annotation))
                        .forEach(methods::add);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return methods;
    }
}
