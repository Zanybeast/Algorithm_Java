package com.zz.backtracking;

/**
 * @ClassName NQueens2
 * @Description TODO
 * @Author carl
 * @Date 2020/12/5 01:05
 * @Version 1.0
 **/
public class NQueens2 {
    public static void main(String[] args) {
        new NQueens2().placeQueens(14);
    }

    int[] queens;

    //用布尔数组存放摆放结果
    /**********************
     *  标记某一列是否有皇后
     ***********************/
    boolean[] cols;
    //标记某一对角线是否有皇后
    //因为有两对角线, 所以用两个数组
    boolean[] leftDiagonal;
    boolean[] rightDiagonal;

    //记录有多少种摆法
    int ways;
    void placeQueens(int n) {
        if (n < 1) return;
        queens = new int[n];
        cols = new boolean[n];
        leftDiagonal = new boolean[(n << 1) - 1];
        rightDiagonal = new boolean[(n << 1) - 1];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法");
    }

    void place(int row) {
        if (row == cols.length) {
            ways++;
//            show();
            return;
        }

        for (int col = 0; col < cols.length; col++) {
            if (cols[col]) continue;
            int leftIndex = row - col + cols.length - 1;
            if (leftDiagonal[leftIndex]) continue;
            int rightIndex = row + col;
            if (rightDiagonal[rightIndex]) continue;

            queens[row] = col;
            cols[col] = true;
            leftDiagonal[leftIndex] = true;
            rightDiagonal[rightIndex] = true;
            place(row + 1);

            //还原现场, 回溯失败回来之后要重置上一次回溯的情况
            cols[col] = false;
            leftDiagonal[leftIndex] = false;
            rightDiagonal[rightIndex] = false;
        }
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (queens[row] == col) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }
}
