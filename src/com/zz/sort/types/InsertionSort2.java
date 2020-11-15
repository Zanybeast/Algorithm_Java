package com.zz.sort.types;

/**
 * @ClassName InsertionSort2
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 09:49
 * @Version 1.0
 **/
public class InsertionSort2<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int current = begin;
            E currentElement = array[current];
            while (current > 0 && cmp(currentElement, array[current - 1]) < 0) {
                array[current] = array[current - 1];
                current--;
            }
            array[current] = currentElement;
        }
    }
}
