package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU_SizeOp
 * @Description 对快速并集进行优化, 维护每一个集合的 size
 * @Author carl
 * @Date 2020/11/18 01:41
 * @Version 1.0
 **/
public class UnionFind_QuickU_SizeOp extends UnionFind_QuickU {
    private int[] sizes;

    public UnionFind_QuickU_SizeOp(int capacity) {
        super(capacity);

        sizes = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            sizes[i] = 1;
        }
    }

    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if (sizes[p1] < sizes[p2]) {
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        } else {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        }
    }
}
