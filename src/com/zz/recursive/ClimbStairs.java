package com.zz.recursive;

import com.zz.tools.TimeTools;

/**
 * @ClassName ClimbStairs
 * @Description TODO
 * @Author carl
 * @Date 2020/12/3 23:45
 * @Version 1.0
 **/
public class ClimbStairs {

    public static void main(String[] args) {
        int n = 30;
        TimeTools.test("recursive", () -> {
            System.out.println(climbStairs(n));
        });
        TimeTools.test("optimized", () -> {
            System.out.println(optimizedClimbStairs(n));
        });
    }

    static int climbStairs(int n) {
        if (n <= 2) return n;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    static int optimizedClimbStairs(int n) {
        if (n <= 2) return n;
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }
}
