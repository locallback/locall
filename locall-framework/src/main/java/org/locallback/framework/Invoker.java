package org.locallback.framework;

import java.lang.reflect.Method;

public class Invoker<T> {

    private final LocallContext locallContext = LocallContext.getContext();

    @SuppressWarnings("unchecked")
    public T invokeMethod(String methodName, Object... args) {
        Method method = locallContext.getAvailableMethod(methodName);
        try {
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            return (T) method.invoke(instance, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
