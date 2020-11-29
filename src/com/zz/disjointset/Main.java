package com.zz.disjointset;

import com.zz.classes.Student;
import com.zz.tools.Asserts;
import com.zz.tools.TimeTools;

/**
 * @ClassName Main
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 00:03
 * @Version 1.0
 **/
public class Main {
    static final int count = 1000000;

    public static void main(String[] args) {
//        testTime(new UnionFind_QuickFind(count));
//        testTime(new UnionFind_QuickU(count));
//        testTime(new UnionFind_QuickU_SizeOp(count));
//        testTime(new UnionFind_QuickU_RankOp(count));
//        testTime(new UnionFind_QuickU_RankPC(count));
//        testTime(new UnionFind_QuickU_RankPS(count));
//        testTime(new UnionFind_QuickU_SizePC(count));
        testTime(new GenericUnionFind<>());
    }

    static void testTime(GenericUnionFind<Integer> uf) {
        for (int i = 0; i < count; i++) {
            uf.makeSet(i);
        }

        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);

        Asserts.test(!uf.isSame(2, 7));

        uf.union(4, 6);

        Asserts.test(uf.isSame(2, 7));

        TimeTools.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }
        });
    }

    static void testTime(UnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);

        Asserts.test(!uf.isSame(2, 7));

        uf.union(4, 6);

        Asserts.test(uf.isSame(2, 7));

        TimeTools.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }

            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random() * count),
                        (int)(Math.random() * count));
            }
        });
    }
}
