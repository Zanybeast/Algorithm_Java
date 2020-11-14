package com.zz.sort.types;

import com.zz.sort.Sort;

/**
 * @ClassName BubbleSort2
 * @Description TODO
 * @Author carl
 * @Date 2020/11/14 11:31
 * @Version 1.0
 **/
public class BubbleSort2 extends Sort {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 1; end--) {
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin - 1, begin) > 0) {
                    swap(begin - 1, begin);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }
}
