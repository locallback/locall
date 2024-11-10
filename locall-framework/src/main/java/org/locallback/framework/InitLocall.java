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
        String test = caller.callMethod("test1", "test1");
        String te = caller.callMethod("test1", "test1");
        System.out.println(test==null);
        System.out.println(te==null);
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
        AnnotationProcessor processor = new AnnotationProcessor(LocallFunction.class);
        List<Method> methodList = processor.getAnnotatedMethods(packageName);
        locallContext.registerAvailableMethodList(methodList);
    }

}