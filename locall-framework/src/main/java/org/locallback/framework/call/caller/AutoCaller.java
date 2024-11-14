package org.locallback.framework.call.caller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locallback.framework.context.LocallContext;

public class AutoCaller<T> implements Caller<T> {

    private static final Logger log = LogManager.getLogger("[AutoCaller]");

    private final LocallContext locallContext = LocallContext.getContext();

    @Override
    public T call(String methodName, Object... args) {
        if (locallContext.getMethod(methodName) != null) {
            Caller<T> localCaller = new LocalCaller<>();
            return localCaller.call(methodName, args);
        } else {
            Caller<T> remoteCaller = new RemoteCaller<>();
            return remoteCaller.call(methodName, args);
        }
    }

}

