package com.zz.sort.types;

/**
 * @ClassName InsertionSort3
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 09:53
 * @Version 1.0
 **/
public class InsertionSort3<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {

            insert(begin, search(begin));

//            int current = begin;
//            E curElement = array[current];
//            int b = 0;
//            int end = begin;
//            while (b < end) {
//                int mid = (b + end) >> 1;
//                if (cmp(curElement, array[mid]) < 0) {
//                    end = mid;
//                } else {
//                    b = mid + 1;
//                }
//            }
//            for (int i = begin; i > b; i--) {
//                array[i] = array[i - 1];
//            }
//            array[b] = curElement;
        }
    }

    /*
     * @Author carl
     * @Description 将 source 位置插入到要求位置
     * @Date 10:06 2020/11/15
     * @Param [source, dest]
     * @Return void
     */
    private void insert(int source, int dest) {
        E e = array[source];
        for (int i = source; i > dest; i--) {
            array[i] = array[i - 1];
        }
        array[dest] = e;
    }

    /*
     * @Author carl
     * @Description 利用二分查找找出待插入位置
     * @Date 10:08 2020/11/15
     * @Param [index]
     * @Return int
     */
    private int search(int index) {
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmp(array[index], array[mid]) < 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }
}
