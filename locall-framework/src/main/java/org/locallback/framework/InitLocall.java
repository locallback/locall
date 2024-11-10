package org.locallback.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locallback.annotation.AnnotationProcessor;
import org.locallback.annotation.LocallFunction;
import org.locallback.common.exception.RepeatInitializeException;

import java.lang.reflect.Method;
import java.util.List;

public class InitLocall {

    private static final Logger log = LogManager.getLogger();

    private static volatile boolean isInit = false;
    private static final LocallContext locallContext = LocallContext.getContext();

    private InitLocall() {
    }

    public static void main(String[] args) {
        init("org.locallback");
        Caller<String> caller = new Caller<>();
        String test = caller.callMethod("test");
        System.out.println(test);
    }

    public static void init(String... packageName) {
        verifyInit();
        scanLocallFunctionAnnotation(packageName);
    }

    private synchronized static void verifyInit() {
        if (isInit) {
            throw new RepeatInitializeException();
        }
        isInit = true;
    }

    private static void scanLocallFunctionAnnotation(String... packageName) {
        AnnotationProcessor annotationProcessor = new AnnotationProcessor(LocallFunction.class);
        List<Method> locallFunctionMethods = annotationProcessor.getAnnotatedMethods(packageName);
        locallContext.registerAvailableMethodList(locallFunctionMethods);
    }

}