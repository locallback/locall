package org.locallback.framework.call.caller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locallback.common.config.LocallConfig;
import org.locallback.common.exception.LocallException;
import org.locallback.framework.call.TypeReference;
import org.locallback.natives.pipe.CallBridgeFactory;
import org.locallback.natives.pipe.RemoteCallBridge;

import java.lang.reflect.Type;
import java.util.Arrays;

public class RemoteCaller<T> implements Caller<T> {

    private static final Logger log = LogManager.getLogger("[RemoteCaller]");

    private final String ip;
    private final int port;
    private final Type returnType;

    public RemoteCaller() {
        this.ip = LocallConfig.getIp();
        this.port = LocallConfig.getPort();
        this.returnType = null;
    }

    public RemoteCaller(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.returnType = null;
    }

    public RemoteCaller(TypeReference<T> typeReference) {
        this.ip = LocallConfig.getIp();
        this.port = LocallConfig.getPort();
        this.returnType = typeReference.getType();
    }

    public RemoteCaller(String ip, int port, TypeReference<T> typeReference) {
        this.ip = ip;
        this.port = port;
        this.returnType = typeReference.getType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T call(String functionName, Object... args) {
        if (!LocallConfig.isEnableConnexus()) {
            log.error("Connexus is not enabled.");
            return null;
        }
        RemoteCallBridge callBridge = CallBridgeFactory.create(ip, port);
        String[] stringArgs = Arrays.stream(args)
                .map(Object::toString)
                .toArray(String[]::new);

        return (T) callBridge.call(functionName, stringArgs);
    }

}

