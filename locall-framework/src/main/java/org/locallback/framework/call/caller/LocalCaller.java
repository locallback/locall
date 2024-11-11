package org.locallback.framework.call.caller;

import org.locallback.common.config.LocallConfig;
import org.locallback.common.enums.CacheEnum;
import org.locallback.common.exception.LocallException;
import org.locallback.common.exception.MethodNotFindException;
import org.locallback.framework.call.CallCache;
import org.locallback.framework.call.TypeReference;
import org.locallback.framework.context.LocallContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

import static org.locallback.framework.call.CallCache.getCacheKey;

public class LocalCaller<T> implements Caller<T> {

    private final LocallContext locallContext = LocallContext.getContext();
    private final CallCache callCache = CallCache.getInstance();
    private final Type returnType;

    public LocalCaller() {
        this.returnType = null;
    }

    public LocalCaller(TypeReference<T> typeReference) {
        this.returnType = typeReference.getType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T call(String methodName, Object... args) {
        Method method = locallContext.getMethod(methodName);
        if (method == null) {
            throw new MethodNotFindException("Method not found: " + methodName);
        }
        // 可能存在性能问题，待优化
        boolean isCacheMethod = callCache.isLocallCachedMethod(methodName);
        if (LocallConfig.isEnableCallCache() && isCacheMethod) {
            Object cacheValue = callCache.get(getCacheKey(method, args));
            if (cacheValue != null) {
                if (cacheValue == CacheEnum.NULL) return null;
                return (T) cacheValue;
            }
        }

        try {
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            Object result = method.invoke(instance, args);
            if (LocallConfig.isEnableCallCache() && isCacheMethod) {
                callCache.put(getCacheKey(method, args), Objects.requireNonNullElse(result, CacheEnum.NULL));
            }
            if (result != null && returnType != null && !((Class<?>) returnType).isInstance(result)) {
                throw new ClassCastException("Return type mismatch, expected: " + returnType + ", now: " + result.getClass());
            }
            return (T) result;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new LocallException("Error invoking method: " + methodName, e);
        }
    }

}

