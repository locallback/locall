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

    private static final Logger log = LogManager.getLogger();

    private static volatile boolean isInit = false;
    private static final LocallContext locallContext = LocallContext.getContext();
    private static final CallCache callCache = CallCache.getInstance();

    private InitLocall() {
    }

    public static void main(String[] args) {
        init("org.locallback");
        LocallConfig.enableCallCache();
        Caller<String> caller = new Caller<>();
        long start = System.currentTimeMillis();
        String findPrimes = caller.callMethod("findPrimes", "10000000");
        long end = System.currentTimeMillis();
        System.out.println("未命中缓存 运行耗时: " + (end - start) + "ms");
        long start1 = System.currentTimeMillis();
        caller.callMethod("findPrimes", "10000000");
        long end1 = System.currentTimeMillis();
        System.out.println("命中缓存 运行耗时: " + (end1 - start1) + "ms");
    }

    public static void init(String... packageName) {
        verifyInit();
        scanLocallFunctionAnnotation(packageName);
        scanLocallCacheAnnotation(packageName);
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

    private static void scanLocallCacheAnnotation(String... packageName) {
        AnnotationProcessor processor = new AnnotationProcessor(LocallCache.class);
        List<Method> methodList = processor.getAnnotatedMethods(packageName);
        methodList.forEach(method -> callCache.addLocallCachedMethod(method.getName()));
        methodList.forEach(method -> log.info("Locall cache method: {}", method.getName()));
    }

}