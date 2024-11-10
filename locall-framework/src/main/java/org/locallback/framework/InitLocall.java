package org.locallback.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locallback.annotation.AnnotationProcessor;
import org.locallback.annotation.LocallCache;
import org.locallback.annotation.LocallFunction;
import org.locallback.common.config.LocallConfig;
import org.locallback.common.exception.RepeatInitializeException;

import java.lang.reflect.Method;
import java.util.List;

public class InitLocall {

    private static final Logger log = LogManager.getLogger(InitLocall.class);

    private static volatile boolean isInit = false;
    private static final LocallContext locallContext = LocallContext.getContext();
    private static final CallCache callCache = CallCache.getInstance();

    private InitLocall() {
    }

    public static void main(String[] args) {
        init("org.locallback");
        LocallConfig.enableCallCache();
        Caller<List<String>> caller = new Caller<>();
        List<String> result = caller.callMethod("findPrimes", 1000000);

        Caller<String> callerNative = new Caller<>("127.0.0.1", 18233);
        String added = callerNative.callMethod(true, "add", 2, 3);
        System.out.println(added);
    }

    public static void init(String... packageName) {
        verifyInit();
        scanLocallFunctionAnnotation(packageName);
        scanLocallCacheAnnotation(packageName);
    }

    private synchronized static void verifyInit() {
        if (isInit) throw new RepeatInitializeException();
        isInit = true;
    }

    private static void scanLocallFunctionAnnotation(String... packageName) {
        AnnotationProcessor processor = new AnnotationProcessor(LocallFunction.class);
        List<Method> methodList = processor.getAnnotatedMethods(packageName);
        locallContext.registerAvailableMethodList(methodList);
    }

    private static void scanLocallCacheAnnotation(String... packageName) {
        AnnotationProcessor processor = new AnnotationProcessor(LocallCache.class);
        List<Method> methodList = processor.getAnnotatedMethods(packageName);
        methodList.forEach(method -> callCache.addLocallCachedMethod(method.getName()));
    }

}