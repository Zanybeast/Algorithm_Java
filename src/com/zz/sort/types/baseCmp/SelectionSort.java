package com.zz.sort.types.baseCmp;

import com.zz.sort.types.Sort;

/**
 * @ClassName SelectionSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/14 11:43
 * @Version 1.0
 **/
public class SelectionSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(maxIndex, begin) < 0) {
                    maxIndex = begin;
                }
            }
            swap(maxIndex, end);
        }
    }
}
