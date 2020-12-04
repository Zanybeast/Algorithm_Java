package com.zz.recursive;

/**
 * @ClassName Hanoi
 * @Description TODO
 * @Author carl
 * @Date 2020/12/3 23:59
 * @Version 1.0
 **/
public class Hanoi {
    private int moveCount = 0;

    public static void main(String[] args) {
        Hanoi h = new Hanoi();
        h.hanoi(10, "A", "B", "C");
        h.printMoveCount();
    }

    void hanoi(int n, String pr1, String pr2, String pr3) {
        if (n == 1) {
            move(n, pr1, pr3);
            return;
        }
        hanoi(n - 1, pr1, pr3, pr2);

        move(n, pr1, pr3);

        hanoi(n - 1, pr2, pr1, pr3);

    }

    void printMoveCount() {
        System.out.println("Move Count: " + moveCount);
    }

    void move(int n, String from, String to) {
        System.out.println(n + ": " + from + " -> " + to);
        moveCount++;
    }
}
