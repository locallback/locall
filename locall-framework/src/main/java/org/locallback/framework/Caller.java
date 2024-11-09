package org.locallback.framework;

import java.lang.reflect.Method;

public class Caller<T> {

    private final LocallContext locallContext = LocallContext.getContext();

    @SuppressWarnings("unchecked")
    public T callMethod(String methodName, Object... args) {
        Method method = locallContext.getMethod(methodName);
        try {
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            return (T) method.invoke(instance, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
