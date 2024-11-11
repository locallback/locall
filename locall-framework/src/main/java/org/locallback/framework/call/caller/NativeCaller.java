package org.locallback.framework.call.caller;

import org.locallback.common.exception.LocallException;

public class NativeCaller<T> implements Caller<T> {

    @Override
    public T call(String methodName, Object... args) {
        throw new LocallException("NativeCaller is not implemented");
    }

}

