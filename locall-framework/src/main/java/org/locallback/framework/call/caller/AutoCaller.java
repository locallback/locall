package org.locallback.framework.call.caller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locallback.common.exception.MethodNotFindException;

public class AutoCaller<T> implements Caller<T> {

    private static final Logger log = LogManager.getLogger("[AutoCaller]");

    @Override
    public T call(String methodName, Object... args) {
        try {
            // 存在性能问题, 待优化
            Caller<T> localCaller = new LocalCaller<>();
            return localCaller.call(methodName, args);
        } catch (MethodNotFindException ignored) {
            Caller<T> remoteCaller = new RemoteCaller<>();
            return remoteCaller.call(methodName, args);
        }
    }

}

