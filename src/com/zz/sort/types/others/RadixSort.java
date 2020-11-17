package com.zz.sort.types.others;

import com.zz.sort.types.Sort;

/**
 * @ClassName RadixSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/17 21:43
 * @Version 1.0
 **/
public class RadixSort extends Sort<Integer> {
    @Override
    protected void sort() {
        originVersion();
    }

    private void originVersion() {
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        for (int divisor = 1; divisor <= max; divisor *= 10) {
            countingSort(divisor);
        }
    }

    private void countingSort(int divisor) {
        int[] counts = new int[10];
        //统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] / divisor % 10]++;
        }
        //累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        int[] result = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            int index = --counts[array[i] / divisor % 10];
            result[index] = array[i];
        }

        for (int i = 0; i < result.length; i++) {
            array[i] = result[i];
        }

    }
}
