package com.zz.recursive;

import com.zz.tools.TimeTools;

/**
 * @ClassName Fib
 * @Description TODO
 * @Author carl
 * @Date 2020/11/29 22:32
 * @Version 1.0
 **/
public class Fib {
    public static void main(String[] args) {
        Fib fib = new Fib();
        int n = 35;
        TimeTools.test("fib0", () -> {
            System.out.println(fib.fib0(n));
        });
        TimeTools.test("fib1", () -> {
            System.out.println(fib.fib1(n));
        });
        TimeTools.test("fib2", () -> {
            System.out.println(fib.fib2(n));
        });
        TimeTools.test("fib3", () -> {
            System.out.println(fib.fib3(n));
        });
        TimeTools.test("fib4", () -> {
            System.out.println(fib.fib4(n));
        });
    }

    int fib0(int n) {
        if (n <= 2) return 1;
        return fib0(n - 1) + fib0(n - 2);
    }

    int fib1(int n) {
        if (n <= 2) return 1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return fib1(n, array);
    }
    int fib1(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fib1(n - 1, array) + fib1(n - 2, array);
        }
        return array[n];
    }

    int fib2(int n) {
        if (n <= 2) return 1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        for (int i = 3; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n];
    }

    int fib3(int n) {
        if (n <= 2) return 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
        }
        return array[n % 2];
    }

    int fib4(int n) {
        if (n <= 2) return 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
        }
        return array[n & 1];
    }
}
