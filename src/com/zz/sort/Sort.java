package com.zz.sort;

import java.text.DecimalFormat;

/**
 * @ClassName Sort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/14 11:02
 * @Version 1.0
 **/
public abstract class Sort implements Comparable<Sort> {
    protected Integer[] array;
    private int cmpCount;
    private int swapCount;
    private long time;
    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(Integer[] array) {
        if (array == null || array.length < 2) return;

        this.array = array;

        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    /**********************
     *  子类实现排序方法
     ***********************/
    protected abstract void sort();

    @Override
    public int compareTo(Sort o) {
        int result = (int)(time - o.time);
        if (result != 0) return result;

        result = cmpCount - o.cmpCount;
        if (result != 0) return result;

        return swapCount - o.swapCount;
    }

    /**********************
     *  比较方法
     ***********************/
    protected int cmp(int i1, int i2) {
        cmpCount++;
        return array[i1] - array[i2];
    }

    protected int cmpElement(Integer v1, Integer v2) {
        cmpCount++;
        return v1 - v2;
    }

    /**********************
     *  交换方法
     ***********************/
    protected void swap(int i1, int i2) {
        swapCount++;
        Integer tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
//        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
//                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }
}
