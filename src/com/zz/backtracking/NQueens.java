package com.zz.backtracking;

/**
 * @ClassName NQueens
 * @Description N皇后问题
 * @Author carl
 * @Date 2020/12/5 00:43
 * @Version 1.0
 **/
public class NQueens {
    /**********************
     *  测试
     ***********************/
    public static void main(String[] args) {
        new NQueens().placeQueens(14);
    }

    /*
     * @Description 数组索引是行号, 数组元素是列号
     * @Date 00:47 2020/12/5
     */
    int[] cols;
    /*
     * @Description 一共有多少种摆法
     * @Date 00:48 2020/12/5
     */
    int ways;

    /*
     * @Author carl
     * @Description N 皇后摆法, 回溯
     * @Date 00:49 2020/12/5
     * @Param [n]元素 n 为要计算的 N 皇后的皇后数量
     * @Return void
     */
    void placeQueens(int n) {
        if (n < 1) return;
        cols = new int[n];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法");
    }

    void place(int row) {
        //回溯递归基
        if (row == cols.length) {
            ways++;
//            show();
            return;
        }

        for (int col = 0; col < cols.length; col++) {
            if (isValid(row, col)) {
                cols[row] = col;
                place(row + 1);
            }
        }
    }

    boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            //第 col 列已经有皇后了
            if (cols[i] == col) {
                printTrueOrFalse(row, col, false);
                return false;
            }
            //第 i 行的皇后跟第 row 行第 col 列格子处在同一斜线上
            if (row - i == Math.abs(col - cols[i])) {
                printTrueOrFalse(row, col, false);
                return false;
            }
        }
        printTrueOrFalse(row, col, true);
        return true;
    }

    /**********************
     *  打印信息, 将代码注释掉即不打印
     ***********************/
    void printTrueOrFalse(int row, int col, boolean flag) {
//        System.out.println("[" + row + "][" + col + "]=" + flag);
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
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
