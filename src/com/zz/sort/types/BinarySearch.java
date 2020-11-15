package com.zz.sort.types;

/**
 * @ClassName BinarySearch
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 09:21
 * @Version 1.0
 **/
public class BinarySearch {

    /*
     * @Author carl
     * @Description 一般情况的二分查找
     * @Date 09:38 2020/11/15
     * @Param [array, v]
     * @Return int
     */
    public static int indexOf(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else if (v > array[mid]) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
    
    /*
     * @Author carl
     * @Description 给插入排序用的二分查找,找到比 v 大的第一个元素位置
     * @Date 09:40 2020/11/15
     * @Param [array, v]
     * @Return int
     */
    public static int search(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }
}
