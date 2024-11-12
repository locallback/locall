package org.locallback.framework;

import org.locallback.annotation.Exclude;
import org.locallback.annotation.LocallCache;
import org.locallback.annotation.LocallFunction;
import org.locallback.framework.entity.TestEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@LocallCache
@LocallFunction
public class TestClass {

    public void testVoidReturn() {
        System.out.println("void return");
    }

    public void testParam(String params) {
        System.out.println(params);
    }

    public void testParams(String string, int number, List<String> list) {
        System.out.println("string: " + string + ", number: " + number + ", list: " + list.toString());
    }

    @Exclude(annotation = LocallCache.class)
    public List<Integer> testGenericsReturn(int number) {
        return findPrimes(number);
    }

    public TestEntity testObjectReturn() {
        return new TestEntity(1233, "test", List.of("elem0", "elem1"), Map.of("locallback.org", 123));
    }

    @Exclude(annotation = {LocallFunction.class})
    public static List<Integer> findPrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    @Exclude
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

}
