package org.locallback.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LocallContext {

    private static volatile LocallContext INSTANCE = null;

    /**
     * 可用方法列表
     */
    private List<Method> availableMethodList = new ArrayList<>();

    public List<Method> getAvailableMethodList() {
        return availableMethodList;
    }

    public void setAvailableMethodList(List<Method> availableMethodList) {
        this.availableMethodList = availableMethodList;
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
