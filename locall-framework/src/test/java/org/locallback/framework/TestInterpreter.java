package org.locallback.framework;

import org.locallback.common.config.LocallConfig;
import org.locallback.framework.call.caller.AutoCaller;
import org.locallback.framework.call.caller.Caller;
import org.locallback.interpreter.FragileParser;

import java.util.Arrays;

public class TestInterpreter {
    public static void main(String[] args) {
        FragileParser fragileParser = new FragileParser();
        Caller<String> callerString = new AutoCaller<>();

        String input = "INVOKE add(Int 22, Int 44): Int";
        try {
            String[] parsed = fragileParser.parse(input);
            LocallConfig.enableConnexus();
            Object[] objects = Arrays.copyOfRange(parsed, 1, parsed.length);
            String called = callerString.call(parsed[0], objects);
            System.out.println(called);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
