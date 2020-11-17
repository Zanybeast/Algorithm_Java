package com.zz.sort.types.baseCmp;

import com.zz.sort.types.Sort;

/**
 * @ClassName InsertionSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 09:35
 * @Version 1.0
 **/
public class InsertionSort1<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int current = begin;
            while (current > 0 && cmp(current, current - 1) < 0) {
                swap(current, current - 1);
                current--;
            }
        }
    }
}
