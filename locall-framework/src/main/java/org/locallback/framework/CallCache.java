package org.locallback.framework;

import java.lang.reflect.Method;
import java.util.*;

public class CallCache {

    private static volatile CallCache INSTANCE = null;

    private static final Map<String, Object> callCacheMap = new HashMap<>();
    private static final List<String> locallCachedMethodList = new ArrayList<>();
    private static final String CACHE_SEPARATOR = "#";
    private static final String CACHE_KEY = "%s" + CACHE_SEPARATOR + "%s";

    private CallCache() {
    }

    public static CallCache getInstance() {
        if (INSTANCE == null) {
            synchronized (LocallContext.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CallCache();
                }
            }
        }
        return INSTANCE;
    }

    public static String getCacheKey(Method method, Object... args) {
        return String.format(CACHE_KEY, method.getName(), Arrays.toString(args));
    }

    public Object get(String key) {
        return callCacheMap.get(key);
    }

    public void put(String key, Object value) {
        callCacheMap.put(key, value);
    }

    public void addLocallCachedMethod(String methodName) {
        locallCachedMethodList.add(methodName);
    }

    public boolean isLocallCachedMethod(String methodName) {
        return locallCachedMethodList.contains(methodName);
    }

}
