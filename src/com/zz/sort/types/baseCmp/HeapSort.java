package com.zz.sort.types.baseCmp;

import com.zz.sort.types.Sort;

/**
 * @ClassName HeapSort
 * @Description TODO
 * @Author carl
 * @Date 2020/11/14 11:52
 * @Version 1.0
 **/
public class HeapSort<E extends Comparable<E>> extends Sort<E> {
    private int heapSize;
    @Override
    protected void sort() {
        //原地建堆
        heapSize = array.length;
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }

        //堆顶元素与尾部元素交换
        //然后对 0 位置元素下滤
        //堆大小-1
        //直到堆元素只剩一个
        while (heapSize > 1) {
            swap(0, --heapSize);

            siftDown(0);
        }
    }

    private void siftDown(int index) {
        E element = array[index];

        int half = heapSize >> 1;
        while (index < half) { // index必须是非叶子节点
            // 默认是左边跟父节点比
            int childIndex = (index << 1) + 1;
            E child = array[childIndex];

            int rightIndex = childIndex + 1;
            // 右子节点比左子节点大
            if (rightIndex < heapSize &&
                    cmp(array[rightIndex], child) > 0) {
                child = array[childIndex = rightIndex];
            }

            // 大于等于子节点
            if (cmp(element, child) >= 0) break;

            array[index] = child;
            index = childIndex;
        }
        array[index] = element;
    }
}
