package org.locallback.framework;

import org.locallback.common.config.LocallConfig;
import org.locallback.framework.call.caller.Caller;
import org.locallback.framework.call.caller.LocalCaller;
import org.locallback.framework.call.caller.RemoteCaller;

import java.util.List;

public class TestFramework {
    public static void main(String[] args) {
        InitLocall.init("org.locallback");
        LocallConfig.enableCallCache();
        Caller<List<String>> caller = new LocalCaller<>();
        List<String> result = caller.call("findPrimes", 10000);
        System.out.println(result);
        LocallConfig.enableConnexus();
        Caller<String> callerNative = new RemoteCaller<>("127.0.0.1", 18233);
        String added = callerNative.call("add", 2314325, 234523334);
        System.out.println(added);
    }
}
