package org.locallback.framework.context;

import java.lang.reflect.Method;
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

    /* 可用方法容器 */
    private final Map<String, Method> methodContainer = new HashMap<>();

    public void registerAvailableMethodList(List<Method> availableMethodList) {
        availableMethodList.forEach(this::registerAvailableMethod);
    }

    /**
     * 注册可用方法
     *
     * @param method 方法
     */
    public void registerAvailableMethod(Method method) {
        methodContainer.put(method.getName(), method);
    }

    /**
     * 通过方法名获取可用方法
     *
     * @param fullMethodName 完整方法名 包名.类名#方法名
     * @return Method
     */
    public Method getMethod(String fullMethodName) {
        return methodContainer.get(fullMethodName);
    }

}
