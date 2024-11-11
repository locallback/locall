package org.locallback.framework;

import org.locallback.common.config.LocallConfig;
import org.locallback.framework.call.Caller;

import java.util.List;

public class TestFramework {
    public static void main(String[] args) {
        InitLocall.init("org.locallback");
        LocallConfig.enableCallCache();
        Caller<List<String>> caller = new Caller<>();
        List<String> result = caller.callMethod("findPrimes", 10000);
        System.out.println(result);
//        Caller<String> callerNative = new Caller<>("127.0.0.1", 18233);
//        String added = callerNative.callMethod(true, "add", 2, 3);
//        System.out.println(added);
    }
}
