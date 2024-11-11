package org.locallback.framework.call.caller;

public interface Caller<T> {

    T call(String methodName, Object... args);

}
