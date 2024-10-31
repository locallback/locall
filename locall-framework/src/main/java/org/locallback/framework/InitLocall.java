package org.locallback.framework;

import org.locallback.annotation.AnnotationProcessor;
import org.locallback.annotation.LocallFunction;
import org.locallback.common.exception.RepeatInitializeException;

import java.lang.reflect.Method;
import java.util.List;

public class InitLocall {

    private static volatile boolean isInit = false;
    private static final LocallContext locallContext = LocallContext.getInstance();

    private InitLocall() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void main(String[] args) {
        init("org.locallback");
    }

    public static void init(String packageName) {
        verifyInit();
        scanLocallFunctionAnnotation(packageName);
    }

    private synchronized static void verifyInit() {
        if (isInit) {
            throw new RepeatInitializeException();
        }
        isInit = true;
    }

    private static void scanLocallFunctionAnnotation(String packageName) {
        List<Method> locallFunctionMethods = AnnotationProcessor.getAnnotatedMethods(LocallFunction.class, packageName);
        locallFunctionMethods.forEach(method -> method.setAccessible(true));
        locallContext.setAvailableMethodList(locallFunctionMethods);
    }

}