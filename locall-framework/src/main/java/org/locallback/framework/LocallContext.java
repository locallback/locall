package org.locallback.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocallContext {

    private static volatile LocallContext INSTANCE = null;

    /* 可用方法列表 */
    private final List<Method> availableMethodList = new ArrayList<>();

    private final Map<String, Method> availableMethodMap = new HashMap<>();

    protected List<Method> getAvailableMethodList() {
        return availableMethodList;
    }

    public void setAvailableMethodList(List<Method> availableMethodList) {
        availableMethodList.forEach(this::addAvailableMethod);
    }

    /**
     * 将可用方法列表转换为Map，可通过方法名获取方法
     * 为保证availableMethodList和availableMethodMap的一致性，故不提供Map的set方法
     *
     * @param method AvailableMethod
     */
    public void addAvailableMethod(Method method) {
        synchronized (this) {
            availableMethodList.add(method);
            availableMethodMap.put(method.getName(), method);
        }

    }

    /**
     * 通过方法名获取可用方法
     *
     * @param methodName 方法名
     * @return Method
     */
    public Method getAvailableMethod(String methodName) {
        return availableMethodMap.get(methodName);
    }

    private LocallContext() {
    }

    public static LocallContext getInstance() {
        if (INSTANCE == null) {
            synchronized (LocallContext.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocallContext();
                }
            }
        }
        return INSTANCE;
    }
}
