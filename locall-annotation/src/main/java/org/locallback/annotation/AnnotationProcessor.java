package org.locallback.annotation;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationProcessor {

    Class<? extends Annotation> annotation;

    public AnnotationProcessor(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    /**
     * 获取指定包中使用指定注解的所有方法
     *
     * @param packageName package name
     * @return list of methods
     */
    public List<Method> getAnnotatedMethods(String... packageName) {
        List<Method> methods = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {

            scanResult.getAllClasses().forEach(classInfo -> {
                Class<?> clazz = classInfo.loadClass();
                Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> isIncludedAnnotation(annotation, clazz, method)
                                && !isExcludedAnnotation(clazz, method))
                        .peek(method -> method.setAccessible(true))
                        .forEach(methods::add);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return methods;
    }

    private boolean isIncludedAnnotation(Class<? extends Annotation> annotation, Class<?> clazz,
                                         Method method) {
        return clazz.isAnnotationPresent(annotation) || method.isAnnotationPresent(annotation);
    }

    private boolean isExcludedAnnotation(Class<?> clazz, Method method) {
        Exclude exclude = method.getAnnotation(Exclude.class);

        if (exclude == null || exclude.annotation().length == 0) {
            return exclude != null;
        }

        return Arrays.stream(exclude.annotation()).anyMatch(excludeAnnotation ->
                method.isAnnotationPresent(excludeAnnotation) || clazz.isAnnotationPresent(excludeAnnotation)
        );
    }

}
