package org.locallback.framework;

import org.locallback.common.enums.CacheEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Caller<T> {

    private final LocallContext locallContext = LocallContext.getContext();
    private final Map<String, Object> callCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public T callMethod(String methodName, Object... args) {
        Method method = locallContext.getMethod(methodName);
        Object o = callCache.get(method.getName() + Arrays.toString(args));
        if (o != null) {
            if (o == CacheEnum.NULL) return null;
            return (T) o;
        }

        try {
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            Object result = method.invoke(instance, args);
            callCache.put(method.getName() + Arrays.toString(args), Objects.requireNonNullElse(result, CacheEnum.NULL));

            return (T) result;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException("Error invoking method: " + methodName, e);
        }
    }

}
