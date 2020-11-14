package com.zz.sort;

import com.zz.sort.types.*;
import com.zz.tools.Asserts;
import com.zz.tools.Integers;

import java.util.Arrays;

/**
 * @ClassName main
 * @Description TODO
 * @Author carl
 * @Date 2020/11/14 10:53
 * @Version 1.0
 **/
public class Main {
    public static void main(String[] args) {
        Integer[] array = Integers.random(10000, 1, 20000);
//        Integer[] array = Integers.tailAscOrder(1,20000, 4000);
//        Integer[] array = Integers.ascOrder(1, 20000);

        testSort(array,
                new BubbleSort1(),
                new BubbleSort2(),
                new BubbleSort3(),
                new SelectionSort(),
                new HeapSort());
    }

    public static void testSort(Integer[] array, Sort... sorts) {
        for (Sort sort: sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }
        Arrays.sort(sorts);

        for (Sort sort: sorts) {
            System.out.println(sort);
        }
    }
}
