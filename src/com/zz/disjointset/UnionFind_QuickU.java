package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 01:37
 * @Version 1.0
 **/
public class UnionFind_QuickU extends UnionFind {
    public UnionFind_QuickU(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        parents[p1] = p2;
    }
}
