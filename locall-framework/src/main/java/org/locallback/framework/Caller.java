package org.locallback.framework;

import org.locallback.common.config.LocallConfig;
import org.locallback.common.enums.CacheEnum;
import org.locallback.common.exception.MethodNotFindException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import static org.locallback.framework.CallCache.getCacheKey;

public class Caller<T> {

    private final LocallContext locallContext = LocallContext.getContext();
    private final CallCache callCache = CallCache.getInstance();

    @SuppressWarnings("unchecked")
    public T callMethod(String methodName, Object... args) {
        Method method = locallContext.getMethod(methodName);
        if (method == null) {
            throw new MethodNotFindException("Method not found: " + methodName);
        }
        // 存在性能问题，待优化
        boolean isCacheMethod = callCache.isLocallCachedMethod(methodName);
        if (LocallConfig.enableCallCache && isCacheMethod) {
            Object cacheValue = callCache.get(getCacheKey(method, args));
            if (cacheValue != null) {
                if (cacheValue == CacheEnum.NULL) return null;
                return (T) cacheValue;
            }
        }

        try {
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            Object result = method.invoke(instance, args);
            if (LocallConfig.enableCallCache && isCacheMethod) {
                callCache.put(getCacheKey(method, args), Objects.requireNonNullElse(result, CacheEnum.NULL));
            }

            return (T) result;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException("Error invoking method: " + methodName, e);
        }
    }

}