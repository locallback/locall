package org.locallback.annotation;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class AnnotationProcessor {

    Class<? extends Annotation> annotation;

    public AnnotationProcessor(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public List<Method> getAnnotatedMethods(String... packageName) {
        return getAnnotatedMethods(null, packageName);
    }

    /**
     * 获取指定包中使用指定注解的所有方法
     *
     * @param packageName package name
     * @return list of methods
     */
    public List<Method> getAnnotatedMethods(Predicate<Method> filter, String... packageName) {
        List<Method> methods = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {

            scanResult.getAllClasses().forEach(classInfo -> {
                Class<?> clazz = classInfo.loadClass();
                Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> isIncludedAnnotation(annotation, clazz, method)
                                && !isExcludedAnnotation(annotation, method))
                        .filter(method -> filter == null || filter.test(method))
                        .forEach(method -> {
                            method.setAccessible(true);
                            methods.add(method);
                        });
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return methods;
    }

    /**
     * 判断方法是否包含指定注解
     */
    private boolean isIncludedAnnotation(Class<? extends Annotation> annotation, Class<?> clazz, Method method) {
        return clazz.isAnnotationPresent(annotation) || method.isAnnotationPresent(annotation);
    }

    /**
     * 判断方法是否包含 @Exclude 注解, 并且 @Exclude 注解中指定的注解是否在方法或类上
     */
    private boolean isExcludedAnnotation(Class<? extends Annotation> annotation, Method method) {
        Exclude exclude = method.getAnnotation(Exclude.class);

        if (exclude == null || exclude.annotation().length == 0) {
            return exclude != null;
        }

        for (Class<? extends Annotation> aClass : exclude.annotation()) {
            if (aClass == annotation) {
                return true;
            }
        }

        return false;
    }

}
