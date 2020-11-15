package com.zz.sort.types;

/**
 * @ClassName MergeSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 11:10
 * @Version 1.0
 **/
public class MergeSort<E extends Comparable<E>> extends Sort<E> {
    //临时左边数组
    private E[] leftArray;

    @Override
    protected void sort() {
        leftArray = (E[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }

    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;

        //备份左边数组
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin + i];
        }

        //如果左边还没结束,将左右序列合并
        while (li < le) {
            if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = leftArray[li++];
            }
        }
    }
}
