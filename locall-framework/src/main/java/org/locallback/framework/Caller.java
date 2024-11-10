package org.locallback.framework;

import org.locallback.common.config.LocallConfig;
import org.locallback.common.enums.CacheEnum;
import org.locallback.common.exception.MethodNotFindException;
import org.locallback.natives.pipe.NativeCallBridge;
import org.locallback.natives.pipe.NativeCallBridgeFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

import static org.locallback.framework.CallCache.getCacheKey;

public class Caller<T> {

    private final LocallContext locallContext = LocallContext.getContext();
    private final CallCache callCache = CallCache.getInstance();
    private final Type returnType;
    private String ip = LocallConfig.ip;
    private int port = LocallConfig.port;

    public Caller(TypeReference<T> typeReference) {
        this.returnType = typeReference.getType();
    }

    public Caller() {
        returnType = null;
    }

    public Caller(String ip, int port) {
        this.ip = ip;
        this.port = port;
        returnType = null;
    }

    public T callMethod(boolean isNative, String methodName, Object... args) {
        return isNative ? callNativeMethod(methodName, args) : callMethod(methodName, args);
    }

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
            if (result != null && returnType != null && !((Class<?>) returnType).isInstance(result)) {
                throw new ClassCastException("Return type mismatch, expected: " + returnType + ", now: " + result.getClass());
            }
            return (T) result;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException("Error invoking method: " + methodName, e);
        }
    }

    @SuppressWarnings("unchecked")
    public T callNativeMethod(String functionName, Object... args) {
        NativeCallBridge nativeCallBridge = NativeCallBridgeFactory.create(ip, port);
        String[] stringArgs = Arrays.stream(args)
                .map(Object::toString)
                .toArray(String[]::new);

        return (T) nativeCallBridge.call(functionName, stringArgs);
    }

}