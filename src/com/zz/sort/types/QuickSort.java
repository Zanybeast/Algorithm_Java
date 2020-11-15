package com.zz.sort.types;

/**
 * @ClassName QuickSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 22:36
 * @Version 1.0
 **/
public class QuickSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        sort(0, array.length);
    }

    private void sort(int begin , int end) {
        if ((end - begin) < 2) return;

        int mid = pivotIndex(begin, end);

        sort(begin, mid);
        sort(mid + 1, end);
    }

    private int pivotIndex(int begin, int end) {
        //随机选择一个元素与begin 交换
        swap(begin, begin + (int)Math.random() * (end - begin));

        E pivot = array[begin];
        end--;

        while (begin < end) {
            //将尾元素与轴点元素比较,如果比轴点元素大则直接 end--,否则将元素移到头部 begin
            //并且头元素自增,并退出循环,从首部开始执行
            while (begin < end) {
                if (cmp(pivot, array[end]) < 0) {
                    end--;
                } else {
                    array[begin++] = array[end];
                    break;
                }
            }

            //将首元素与轴点元素进行比较,如果比轴点元素小则 begin++,否则将元素移到 end
            //并且尾元素自减,并退出循环,从尾部开始执行
            while (begin < end) {
                if (cmp(pivot, array[begin]) > 0) {
                    begin++;
                } else {
                    array[end--] = array[begin];
                    break;
                }
            }
        }

        array[begin] = pivot;

        return begin;

    }
}
