package org.locallback.framework.call.caller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locallback.framework.context.LocallContext;

public class AutoCaller<T> implements Caller<T> {

    private static final Logger log = LogManager.getLogger("[AutoCaller]");

    private final LocallContext locallContext = LocallContext.getContext();

    Caller<T> caller = null;

    @Override
    public T call(String methodName, Object... args) {
        if ((locallContext.getMethod(methodName) != null)) {
            caller = new LocalCaller<>();
        } else {
            caller = new RemoteCaller<>();
        }
        return caller.call(methodName, args);
    }

}

