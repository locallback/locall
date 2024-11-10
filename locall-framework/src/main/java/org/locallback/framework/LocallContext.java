package org.locallback.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocallContext {

    private static volatile LocallContext INSTANCE = null;

    private LocallContext() {
    }

    public static LocallContext getContext() {
        if (INSTANCE == null) {
            synchronized (LocallContext.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocallContext();
                }
            }
        }
        return INSTANCE;
    }

    /* 可用方法列表 */
    private final List<Method> availableMethodList = new ArrayList<>();
    /* 可用方法Map */
    private final Map<String, Method> availableMethodMap = new HashMap<>();

    protected void registerAvailableMethodList(List<Method> availableMethodList) {
        availableMethodList.forEach(this::registerAvailableMethod);
    }

    /**
     * 将可用方法列表转换为Map，可通过方法名获取方法
     * 为保证availableMethodList和availableMethodMap的一致性，故不提供Map的set方法
     *
     * @param method AvailableMethod
     */
    protected void registerAvailableMethod(Method method) {
        synchronized (this) {
            availableMethodList.add(method);
            availableMethodMap.put(method.getName(), method);
        }

    }

    /**
     * 通过方法名获取可用方法
     *
     * @param fullMethodName 完整方法名 包名.类名#方法名
     * @return Method
     */
    public Method getMethod(String fullMethodName) {
        return availableMethodMap.get(fullMethodName);
    }

    protected List<Method> getAvailableMethodList() {
        return availableMethodList;
    }

}
