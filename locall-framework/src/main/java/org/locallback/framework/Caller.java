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

    private static final String CACHE_SEPARATOR = "#";
    private static final String CACHE_KEY = "%s" + CACHE_SEPARATOR + "%s";

    @SuppressWarnings("unchecked")
    public T callMethod(String methodName, Object... args) {
        Method method = locallContext.getMethod(methodName);
        Object cacheValue = callCache.get(cacheKey(method, args));
        if (cacheValue != null) {
            if (cacheValue == CacheEnum.NULL) return null;
            return (T) cacheValue;
        }

        try {
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            Object result = method.invoke(instance, args);
            callCache.put(cacheKey(method, args), Objects.requireNonNullElse(result, CacheEnum.NULL));

            return (T) result;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException("Error invoking method: " + methodName, e);
        }
    }

    private String cacheKey(Method method, Object... args) {
        return String.format(CACHE_KEY, method.getName(), Arrays.toString(args));
    }

}
