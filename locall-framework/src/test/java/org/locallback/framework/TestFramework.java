package org.locallback.framework;

import org.locallback.common.config.LocallConfig;
import org.locallback.framework.call.caller.AutoCaller;
import org.locallback.framework.call.caller.Caller;
import org.locallback.framework.call.caller.LocalCaller;
import org.locallback.framework.call.caller.RemoteCaller;
import org.locallback.framework.entity.TestEntity;
import org.locallback.framework.executer.CodeExecuter;
import org.locallback.framework.executer.Executer;
import org.locallback.framework.executer.ShellExecuter;

import java.util.List;

public class TestFramework {
    public static void main(String[] args) {
        InitLocall.init("org.locallback");
        // 开启调用缓存
        LocallConfig.enableCallCache();

        Caller<Void> callerVoid = new LocalCaller<>();
        Caller<String> callerString = new LocalCaller<>();
        Caller<List<Integer>> callerIntegerList = new LocalCaller<>();
        Caller<TestEntity> callerEntity = new LocalCaller<>();

        callerVoid.call("testVoidReturn");
        callerString.call("testParam", "Hello, World!");
        callerVoid.call("testParams", "example", 42, List.of("item1", "item2"));

        List<Integer> primeResult = callerIntegerList.call("testGenericsReturn", 100);
        System.out.println("Prime numbers: " + primeResult);

        TestEntity entityResult = callerEntity.call("testObjectReturn");
        System.out.println("TestEntity result: " + entityResult.toString());

        LocallConfig.enableConnexus();
//        Caller<String> callerNative = new RemoteCaller<>("127.0.0.1", 18233);
//        String added = callerNative.call("add", 2314325, 234523334);
//        System.out.println(added);

        Caller<String> autoCaller = new AutoCaller<>();
        String autoResult = autoCaller.call("add", 2314325, 234523334);
        System.out.println("=============================================");
        System.out.println(autoResult);

        Executer executer = new ShellExecuter();
        String result = executer.exec("pwd");
        System.out.println(result);

        Executer codeExecuter = new CodeExecuter();

        String code = """
                public class HelloWorld {
                    public static void main(String[] args) {
                        System.out.println("Hello from in-line Java code!");
                    }
                }
                """;
        String codeResult = codeExecuter.exec(code);
        System.out.println(codeResult);
    }
}
