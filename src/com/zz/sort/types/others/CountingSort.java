package com.zz.sort.types.others;

import com.zz.sort.types.Sort;

/**
 * @ClassName CountingSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/16 23:51
 * @Version 1.0
 **/
public class CountingSort extends Sort<Integer> {


    @Override
    protected void sort() {
//        sortNoOptimized();
        optimizedSort();
    }

    private void optimizedSort() {
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }

            if (array[i] < min) {
                min = array[i];
            }
        }

        int[] counts = new int[max - min + 1];
        //记录元素出现多少次
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }
        //将前面元素出现次数累加起来放到计数数组中
        for (int i = 1; i < counts.length; i++) {
            //新计数 = 原来的出现次数 + 前一个元素保存的计数
            counts[i] = counts[i] + counts[i - 1];
        }

        //原数组从后向前遍历, 开辟新数组, 将元素按位置逐一放好
        int[] result = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            //先找到所需放到的位置
            int index = --counts[array[i] - min];
            result[index] = array[i];
        }

        for (int i = 0; i < result.length; i++) {
            array[i] = result[i];
        }
    }

    private void sortNoOptimized() {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        int[] counts = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            counts[array[i]]++;
        }

        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i] > 0) {
                array[index++] = i;
                counts[i]--;
            }
        }
    }
}

