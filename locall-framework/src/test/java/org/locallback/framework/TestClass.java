package org.locallback.framework;

import org.locallback.annotation.Exclude;
import org.locallback.annotation.LocallCache;
import org.locallback.annotation.LocallFunction;

import java.util.ArrayList;
import java.util.List;

@LocallCache
@LocallFunction
public class TestClass {

    public String test() {
        return "test111111111111111111";
    }

    public void test1(String a) {
        System.out.println(a);
    }

    public void test2() {
        System.out.println("222222222");
    }

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
