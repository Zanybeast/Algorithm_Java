package com.zz.sort.types;

/**
 * @ClassName BubbleSort3
 * @Description TODO
 * @Author carl
 * @Date 2020/11/14 11:35
 * @Version 1.0
 **/
public class BubbleSort3<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin - 1, begin) > 0) {
                    swap(begin - 1, begin);
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }
}
